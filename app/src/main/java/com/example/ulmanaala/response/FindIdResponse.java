package com.example.ulmanaala.response;

import com.google.gson.annotations.SerializedName;

public class FindIdResponse {

    @SerializedName("message")  // JSON 필드와 매칭
    private String message;

    public FindIdResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}