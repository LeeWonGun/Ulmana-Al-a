package com.example.ulmanaala.request;

public class ResetPasswordRequest {
    private String email;
    private String new_password;

    // 생성자
    public ResetPasswordRequest(String email, String new_password) {
        this.email = email;
        this.new_password = new_password;
    }

    // Getter and Setter
    public String getEmail() {
        return email;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }
}