package com.example.ulmanaala.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DailyFactResponse {

    @SerializedName("daily_facts")
    private List<DailyFactItem> dailyFacts;

    public List<DailyFactItem> getDailyFacts() {
        return dailyFacts;
    }
}
