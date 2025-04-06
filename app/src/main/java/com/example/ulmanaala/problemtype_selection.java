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
        Button btnTest30 = findViewById(R.id.btn_test30);
       // Button btnTest25 = findViewById(R.id.btn_test25);

        // 시험30문제 버튼
        btnTest30.setOnClickListener(v -> startProblemSolve("test30"));

        // 시험25문제 버튼
       // btnTest50.setOnClickListener(v -> startProblemSolve("test25"));
    }

    private void startProblemSolve(String type) {
        Intent intent = new Intent(this, problemsolve.class);
        intent.putExtra("genre", genre);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}