package com.example.ulmanaala.response;

import com.google.gson.annotations.SerializedName;

public class WrongNoteSubmitResponse {

    @SerializedName("summary")
    private Summary summary;

    public Summary getSummary() {
        return summary;
    }

    public static class Summary {
        @SerializedName("정답 수")
        private int correctCount;

        @SerializedName("오답 수")
        private int wrongCount;

        @SerializedName("총 점수")
        private double totalScore;

        public int getCorrectCount() { return correctCount; }
        public int getWrongCount() { return wrongCount; }
        public double getTotalScore() { return totalScore; }
    }
}