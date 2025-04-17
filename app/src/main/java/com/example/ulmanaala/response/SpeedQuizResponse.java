package com.example.ulmanaala.response;

import java.util.List;

public class SpeedQuizResponse {
    private List<Integer> time_options;
    private List<QuestionResponse> questions;

    public List<Integer> getTime_options() {
        return time_options;
    }

    public List<QuestionResponse> getQuestions() {
        return questions;
    }
}
