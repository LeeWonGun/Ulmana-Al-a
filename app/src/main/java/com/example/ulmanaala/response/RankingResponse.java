package com.example.ulmanaala.response;

import com.example.ulmanaala.model.RankingItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RankingResponse {
    @SerializedName("top_rankings")
    public List<RankingItem> topRankings;

    @SerializedName("my_ranking")
    public RankingItem myRanking;
}