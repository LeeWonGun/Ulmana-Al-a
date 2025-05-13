package com.example.ulmanaala.response;

public class QuizResultResponse {
    private int score;
    private int correctCount;
    private int wrongCount;
    private String quizType;   // 🔵 퀴즈 종류 추가
    private String genre;      // 🔵 퀴즈 장르 추가
    private String timestamp;  // 🔵 제출 시간 추가

    // Getters
    public int getScore() {
        return score;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public int getWrongCount() {
        return wrongCount;
    }

    public String getQuizType() {
        return quizType;
    }

    public String getGenre() {
        return genre;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
