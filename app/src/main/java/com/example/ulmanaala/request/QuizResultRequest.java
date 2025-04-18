package com.example.ulmanaala.request;

import java.util.List;

public class QuizResultRequest {
    private List<Integer> questionIds;
    private List<Integer> userAnswers;
    private int userId;  // 사용자 ID가 필요하면 추가

    public QuizResultRequest(List<Integer> questionIds, List<Integer> userAnswers, int userId) {
        this.questionIds = questionIds;
        this.userAnswers = userAnswers;
        this.userId = userId;
    }

    public List<Integer> getQuestionIds() {
        return questionIds;
    }

    public List<Integer> getUserAnswers() {
        return userAnswers;
    }

    public int getUserId() {
        return userId;
    }

    public void setQuestionIds(List<Integer> questionIds) {
        this.questionIds = questionIds;
    }

    public void setUserAnswers(List<Integer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}