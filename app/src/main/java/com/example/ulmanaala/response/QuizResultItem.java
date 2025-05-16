package com.example.ulmanaala.response;

public class QuizResultItem {
    private int question_id;
    private String user_answer;
    private String correct_answer;
    private boolean is_correct;

    public int getQuestion_id() { return question_id; }
    public String getUser_answer() { return user_answer; }
    public String getCorrect_answer() { return correct_answer; }
    public boolean isIs_correct() { return is_correct; }
}
