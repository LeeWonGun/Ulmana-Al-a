package com.example.ulmanaala.response;

import com.google.gson.annotations.SerializedName;

public class UploadImageResponse {
    private String message;
    @SerializedName("profile_image_url")
    private String profileImageUrl;

    public String getProfileImageUrl() { return profileImageUrl; }
}
