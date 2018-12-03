package com.github.masaliev.passport.controllers;

import com.github.masaliev.passport.domain.User;
import com.github.masaliev.passport.repository.UserRepository;
import com.github.masaliev.passport.service.UserService;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @RequestMapping("/me")
    public User me(@AuthenticationPrincipal User user) {
        return user;
    }

    @PostMapping("/sign-up")
    @ResponseBody
    public ResponseEntity<Map<String, String>> signUp(User user) {
        String errorString = userService.addUser(user);
        if (errorString != null) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("error", errorString));
        } else {
            return ResponseEntity.ok(Collections.singletonMap("user", "test2"));
        }
    }
}
