package com.example.ulmanaala;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ProblemType_SelectionActivity extends AppCompatActivity {

    private String genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problemtype_selection);

        // 장르 데이터 수신
        genre = getIntent().getStringExtra("genre");

        // 문제 유형 버튼 초기화
        Button btnTest50 = findViewById(R.id.btn_test50);
        Button btnTest25 = findViewById(R.id.btn_test25);
        Button btnSpeedQuiz = findViewById(R.id.btn_speedquiz);

        // 시험50문제 버튼 클릭 시
        btnTest50.setOnClickListener(v -> startProblemSolve("test50"));

        // 시험25문제 버튼 클릭 시
        btnTest25.setOnClickListener(v -> startProblemSolve("test25"));

        // 스피드 퀴즈 버튼 클릭 시
        btnSpeedQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(this, SpeedQuizTimeSelectionActivity.class);
            intent.putExtra("genre", genre);
            startActivity(intent);
        });

    }

    private void startProblemSolve(String type) {
        // 문제 유형과 장르를 문제 풀이 화면으로 전달
        Intent intent = new Intent(this, ProblemSolveActivity.class);
        intent.putExtra("genre", genre);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}