package com.example.ulmanaala.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuizSet implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("date")
    private String date;

    @SerializedName("genre")
    private String genre;

    @SerializedName("quiz_type")
    private String quizType;

    @SerializedName("totalQuestions")
    private int totalQuestions;

    @SerializedName("wrongAnswers")
    private int wrongAnswers;

    @SerializedName("quizResults")
    private List<QuizResult> quizResults;

    public QuizSet(String date, String genre, String quizType, int totalQuestions, int wrongAnswers, List<QuizResult> quizResults) {
        this.date = date;
        this.genre = genre;
        this.quizType = quizType;
        this.totalQuestions = totalQuestions;
        this.wrongAnswers = wrongAnswers;
        this.quizResults = quizResults;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public List<QuizResult> getQuizResults() {
        return quizResults;
    }

    public void setQuizResults(List<QuizResult> quizResults) {
        this.quizResults = quizResults;
    }

    public List<Integer> getWrongQuestionIds() {
        List<Integer> wrongIds = new ArrayList<>();
        if (quizResults != null) {
            for (QuizResult result : quizResults) {
                if (!result.isCorrect()) {
                    wrongIds.add(result.getQuestionId());
                }
            }
        }
        return wrongIds;
    }
}