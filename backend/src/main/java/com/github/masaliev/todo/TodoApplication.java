package com.github.masaliev.todo;

import com.github.masaliev.shared.AuthUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }
    
    @GetMapping
    public AuthUser secret(@AuthenticationPrincipal AuthUser user){
        return user;
    }
}
