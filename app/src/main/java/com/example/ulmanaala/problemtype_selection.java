package com.example.ulmanaala;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class problemtype_selection extends AppCompatActivity {

    private String genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problemtype_selection);

        // 장르 데이터 수신
        genre = getIntent().getStringExtra("genre");

        // 문제 유형 버튼 초기화
        Button btnQuiz50 = findViewById(R.id.btn_quiz50);
        Button btnQuiz25 = findViewById(R.id.btn_quiz25);
        Button btnSpeedQuiz = findViewById(R.id.btn_speedquiz);

        // 시험50문제 버튼
        btnQuiz50.setOnClickListener(v -> startProblemSolve("퀴즈50"));

        // 시험25문제 버튼
        btnQuiz25.setOnClickListener(v -> startProblemSolve("퀴즈25"));

        // 스피드퀴즈 버튼
        btnSpeedQuiz.setOnClickListener(v -> startProblemSolve("speedquiz"));
    }

    private void startProblemSolve(String type) {
        Intent intent = new Intent(this, problemsolve.class);
        intent.putExtra("genre", genre);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}