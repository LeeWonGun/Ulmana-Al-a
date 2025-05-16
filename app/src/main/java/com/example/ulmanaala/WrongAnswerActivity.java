package com.example.ulmanaala;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WrongAnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_answer);

        // 문제 및 해설 보기 버튼
        Button btnProblemAndSolution = findViewById(R.id.btn_problem_and_solution);
        btnProblemAndSolution.setOnClickListener(v -> {
            // QuizSetsActivity로 이동 (정답 표시 O, 해설 보기 O)
            Intent intent = new Intent(WrongAnswerActivity.this, QuizSetsActivity.class);
            intent.putExtra("hideAnswers", false); // 정답, 해설 보임
            intent.putExtra("isSolvingWrong", false); // 일반 모드
            startActivity(intent);
        });

        // 오답 문제 풀기 버튼
        Button btnWrongProblems = findViewById(R.id.btn_wrong_problems);
        btnWrongProblems.setOnClickListener(v -> {
            // QuizSetsActivity로 이동 (정답 표시 X, 해설 보기 X, 제출 버튼 활성)
            Intent intent = new Intent(WrongAnswerActivity.this, QuizSetsActivity.class);
            intent.putExtra("hideAnswers", true);  // 핵심 플래그
            intent.putExtra("isSolvingWrong", true);  // 오답 풀기 모드
            startActivity(intent);
        });
    }
}