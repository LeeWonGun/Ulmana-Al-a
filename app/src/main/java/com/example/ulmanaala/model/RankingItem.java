package com.example.ulmanaala.model;

import com.google.gson.annotations.SerializedName;

public class RankingItem {
    public int rank;
    public String nickname;

    @SerializedName("profile_image")
    public String profileImage;

    public float score; // 또는 int로 변경 가능
}