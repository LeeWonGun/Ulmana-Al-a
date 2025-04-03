package com.example.ulmanaala.request;

public class FindIdRequest {
    private String username;

    public FindIdRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void getUsername(String username) {
        this.username = username;
    }
}
