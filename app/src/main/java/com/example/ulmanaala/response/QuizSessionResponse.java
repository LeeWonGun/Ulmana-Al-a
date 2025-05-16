package com.example.ulmanaala.response;

public class QuizSessionResponse {
    private int id;
    private String created_at;
    private String genre_name;
    private int total_questions;
    private int correct_count;
    private int wrong_count;
    private int total_score;

    public int getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public int getTotal_questions() {
        return total_questions;
    }

    public int getCorrect_count() {
        return correct_count;
    }

    public int getWrong_count() {
        return wrong_count;
    }

    public int getTotal_score() {
        return total_score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }

    public void setTotal_questions(int total_questions) {
        this.total_questions = total_questions;
    }

    public void setCorrect_count(int correct_count) {
        this.correct_count = correct_count;
    }

    public void setWrong_count(int wrong_count) {
        this.wrong_count = wrong_count;
    }

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }
}
