package com.github.masaliev.passport.controllers;

import com.github.masaliev.passport.domain.User;
import com.github.masaliev.passport.domain.dto.SignInRequest;
import com.github.masaliev.passport.domain.dto.SignInResult;
import com.github.masaliev.passport.domain.dto.SignUpRequest;
import com.github.masaliev.passport.exceptions.ValidationException;
import com.github.masaliev.passport.service.UserService;
import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping("/me")
    public Principal me(@AuthenticationPrincipal Principal user) {
        return user; //@TODO remove pasword, etc fields from serialization or add new dto class
    }

    @PostMapping("/sign-in")
    public SignInResult signIn(@RequestBody SignInRequest signInRequest, HttpServletRequest request) {
        return handleSignIn(signInRequest.getUsername(), signInRequest.getPassword(), request);
    }

    @PostMapping("/sign-up")
    public Map<String, String> signUp(@RequestBody @Valid SignUpRequest signUpRequest,
            BindingResult bindingResult) {

        if (signUpRequest.getPassword() != null && !signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            bindingResult.addError(new FieldError("signUpRequest", "confirm_password", "Those passwords didn't match"));
        }

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        User user = userService.addUser(signUpRequest);

        return Collections.singletonMap("status", "success");
    }

    private SignInResult handleSignIn(String username, String password, HttpServletRequest request) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        return new SignInResult(authentication.getName(), session.getId());

    }

    @PostMapping("/signout")
    public ResponseEntity signout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok(null);
    }

    @GetMapping("/activate/{code}")
    public void activate(HttpServletResponse responce, @PathVariable String code) throws IOException {

        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            responce.sendRedirect("http://localhost:8082/#/activate/success");
        } else {
            responce.sendRedirect("http://localhost:8082/#/activate/error");
        }
    }
}
