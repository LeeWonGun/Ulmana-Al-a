package com.example.ulmanaala;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class QuizResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        TextView tvType = findViewById(R.id.tv_result_type);
        TextView tvGenre = findViewById(R.id.tv_result_genre);
        TextView tvCorrect = findViewById(R.id.tv_result_correct);
        TextView tvWrong = findViewById(R.id.tv_result_wrong);
        TextView tvScore = findViewById(R.id.tv_result_score);

        String type = getIntent().getStringExtra("subject");
        String genre = getIntent().getStringExtra("genre");
        int correctCount = getIntent().getIntExtra("correctCount", 0);
        int wrongCount = getIntent().getIntExtra("wrongCount", 0);
        double score = getIntent().getDoubleExtra("score", 0.0);

        tvType.setText("유형: " + getReadableSubject(type));
        tvGenre.setText("장르: " + genre);
        tvCorrect.setText("맞힌 개수: " + correctCount);
        tvWrong.setText("틀린 개수: " + wrongCount);
        tvScore.setText("이번 점수: " + score);
    }

    private String getReadableSubject(String subject) {
        switch (subject) {
            case "test25": return "25문제 시험";
            case "test50": return "50문제 시험";
            case "speed": return "스피드퀴즈";
            case "wrong_note": return "오답노트";
            default: return subject;
        }
    }
}