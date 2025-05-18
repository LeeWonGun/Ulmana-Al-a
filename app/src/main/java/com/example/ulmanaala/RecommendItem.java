package com.example.ulmanaala;

public class RecommendItem {
    private String text;
    private Class<?> targetActivity;

    public RecommendItem(String text, Class<?> targetActivity) {
        this.text = text;
        this.targetActivity = targetActivity;
    }

    public String getText() {
        return text;
    }

    public Class<?> getTargetActivity() {
        return targetActivity;
    }
}
