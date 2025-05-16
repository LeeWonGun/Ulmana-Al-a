package com.example.ulmanaala.response;

import com.google.gson.annotations.SerializedName;

public class QuizResultResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("summary")
    private Summary summary;

    public String getMessage() {
        return message;
    }

    public Summary getSummary() {
        return summary;
    }

    public static class Summary {
        @SerializedName("총 문항 수")
        private int total;

        @SerializedName("정답 수")
        private int correct;

        @SerializedName("오답 수")
        private int wrong;

        @SerializedName("획득 점수")
        private int score;

        public int getTotal() {
            return total;
        }

        public int getCorrect() {
            return correct;
        }

        public int getWrong() {
            return wrong;
        }

        public int getScore() {
            return score;
        }
    }
}