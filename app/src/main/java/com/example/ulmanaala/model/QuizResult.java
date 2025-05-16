package com.example.ulmanaala.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QuizResult implements Serializable {
    @SerializedName("question")
    private int questionId;
    @SerializedName("question_text")
    private String questionText;
    @SerializedName("user_answer")
    private String userAnswer;
    @SerializedName("correct_answer")
    private String correctAnswer;
    @SerializedName("is_correct")
    private boolean isCorrect;
    @SerializedName("score")
    private int score;
    @SerializedName("explanation")
    private String explanation;
    @SerializedName("genre_name")
    private String genreName;
    @SerializedName("correct_count")
    private int correctCount;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    public QuizResult(int questionId, String questionText, String userAnswer, String correctAnswer, boolean isCorrect,
                      int score, String explanation, String genreName, int correctCount,
                      String option1, String option2, String option3, String option4) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.isCorrect = isCorrect;
        this.score = score;
        this.explanation = explanation;
        this.genreName = genreName;
        this.correctCount = correctCount;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;

        this.isCorrect = userAnswer != null && userAnswer.equals(correctAnswer);
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}