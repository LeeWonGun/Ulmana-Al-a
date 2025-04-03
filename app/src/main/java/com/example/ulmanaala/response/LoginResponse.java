package com.example.ulmanaala.response;

import java.util.List;

public class LoginResponse {
    private String email;
    private String password;
    private String username;
    private List<String> interests;

    public LoginResponse(String email, String password, String username, List<String> interests) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.interests = interests;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setNickname(String username) {
        this.username = username;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}