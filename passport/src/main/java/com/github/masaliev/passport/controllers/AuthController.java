package com.github.masaliev.passport.controllers;

import com.github.masaliev.passport.domain.User;
import com.github.masaliev.passport.domain.dto.SignInResult;
import com.github.masaliev.passport.domain.dto.SignUpRequest;
import com.github.masaliev.passport.exceptions.ValidationException;
import com.github.masaliev.passport.service.UserService;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public SignInResult signIn(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        return handleSignIn(username, password, request);
    }

    @PostMapping("/sign-up")
    public SignInResult signUp(@Valid SignUpRequest signUpRequest,
            BindingResult bindingResult,
            HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        User user = userService.addUser(signUpRequest);

        return handleSignIn(user.getUsername(), signUpRequest.getPassword(), request);
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
}
