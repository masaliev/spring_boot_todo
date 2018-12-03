package com.github.masaliev.passport.domain.dto;

import java.io.Serializable;

public class SignInResult implements Serializable {

    private String username;

    private String token;

    public SignInResult(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
