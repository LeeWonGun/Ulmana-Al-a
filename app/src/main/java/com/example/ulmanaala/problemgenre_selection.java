package com.example.ulmanaala;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class problemgenre_selection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_problemgenre_selection);

        // 과학 버튼
        Button btnScience = findViewById(R.id.btn_science);
        btnScience.setOnClickListener(v -> goToProblemType("science"));

        // 역사 버튼
        Button btnHistory = findViewById(R.id.btn_history);
        btnHistory.setOnClickListener(v -> goToProblemType("history"));

        // 맞춤법 버튼
        Button btnSpelling = findViewById(R.id.btn_spelling);
        btnSpelling.setOnClickListener(v -> goToProblemType("spelling"));

        // 사자성어 버튼
        Button btnIdiom = findViewById(R.id.btn_four_idioms);
        btnIdiom.setOnClickListener(v -> goToProblemType("idiom"));

        // 수도 버튼
        Button btnCapital = findViewById(R.id.btn_capital);
        btnCapital.setOnClickListener(v -> goToProblemType("capital"));

        // 미술 버튼
        Button btnArt = findViewById(R.id.btn_art);
        btnArt.setOnClickListener(v -> goToProblemType("art"));
    }

    private void goToProblemType(String genre) {
        Intent intent = new Intent(problemgenre_selection.this, ProblemType_SelectionActivity.class);
        intent.putExtra("genre", genre);
        startActivity(intent);
    }
}