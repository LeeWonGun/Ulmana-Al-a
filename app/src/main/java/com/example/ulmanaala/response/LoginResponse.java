package com.example.ulmanaala.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LoginResponse {

    @SerializedName("refresh")
    private String refresh;

    @SerializedName("access")
    private String access;

    @SerializedName("email")
    private String email;

    @SerializedName("username")
    private String username;

    @SerializedName("interests")
    private List<String> interests;

    // 기본 생성자
    public LoginResponse() {
    }

    // Getter 메서드들
    public String getRefresh() {
        return refresh;
    }

    public String getAccess() {
        return access;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getInterests() {
        return interests;
    }
}
