package com.example.ulmanaala.response;

import com.google.gson.annotations.SerializedName;

public class QuestionDetailResponse {

    @SerializedName("question_id")
    private int questionId;

    @SerializedName("question_text")
    private String questionText;

    @SerializedName("option1")
    private String option1;

    @SerializedName("option2")
    private String option2;

    @SerializedName("option3")
    private String option3;

    @SerializedName("option4")
    private String option4;

    @SerializedName(("user_answer"))
    private String userAnswer;

    @SerializedName("answer")
    private String answer;

    @SerializedName("explanation")
    private String explanation;

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getAnswer() {
        return answer;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getUserAnswer() {
        return userAnswer;
    }



    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}