package com.example.ulmanaala.request;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class QuizResultRequest {

    @SerializedName("genre_id")
    private int genreId;

    @SerializedName("quiz_results")
    private List<QuizAnswer> quizResults;

    @SerializedName("quiz_type")
    private String quizType;

    @SerializedName("selected_time")
    private int selectedTime;  // 추가: 1 또는 3

    // 수정된 생성자
    public QuizResultRequest(int genreId, List<QuizAnswer> quizResults, String quizType, int selectedTime, String dummy) {
        this.genreId = genreId;
        this.quizResults = quizResults;
        this.quizType = quizType;
        this.selectedTime = selectedTime;
    }

    public int getGenreId() {
        return genreId;
    }

    public List<QuizAnswer> getQuizResults() {
        return quizResults;
    }

    public String getQuizType() {
        return quizType;
    }

    public int getSelectedTime() {
        return selectedTime;
    }

    // 내부 클래스는 그대로 유지
    public static class QuizAnswer {
        @SerializedName("question_id")
        private int questionId;

        @SerializedName("user_answer")
        private int userAnswer;

        public QuizAnswer(int questionId, int userAnswer) {
            this.questionId = questionId;
            this.userAnswer = userAnswer;
        }

        public int getQuestionId() {
            return questionId;
        }

        public int getUserAnswer() {
            return userAnswer;
        }
    }
}