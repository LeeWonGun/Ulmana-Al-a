package com.example.ulmanaala.response;

import java.util.List;

public class LoginResponse {
    private int userId;  // 추가된 필드
    private String token;  // 추가된 필드
    private String email;
    private String password;
    private String username;
    private List<String> interests;

    public LoginResponse(int userId, String token, String email, String password, String username, List<String> interests) {
        this.userId = userId;  // userId 초기화
        this.token = token;  // token 초기화
        this.email = email;
        this.password = password;
        this.username = username;
        this.interests = interests;
    }

    // Getter and Setter for userId
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter and Setter for token
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter for interests
    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

}