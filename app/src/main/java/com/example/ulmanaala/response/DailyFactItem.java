package com.example.ulmanaala.response;

import com.google.gson.annotations.SerializedName;

public class DailyFactItem {

    @SerializedName("genre_name")
    private String genreName;

    @SerializedName("explanation")
    private String explanation;

    public String getGenreName() {
        return genreName;
    }

    public String getExplanation() {
        return explanation;
    }
}
