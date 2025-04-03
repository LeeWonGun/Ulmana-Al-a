package com.example.ulmanaala.request;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("email")  // 서버와 필드명이 같아야 함
    private String email;

    @SerializedName("password")
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
}