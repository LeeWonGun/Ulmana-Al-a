package com.example.ulmanaala;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SpeedQuizTimeSelectionActivity extends AppCompatActivity {

    private String genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedquiz_time_selection);

        genre = getIntent().getStringExtra("genre");

        Button btn1Min = findViewById(R.id.btn_time_1min);
        Button btn3Min = findViewById(R.id.btn_time_3min);

        btn1Min.setOnClickListener(v -> startQuizWithTime(1));
        btn3Min.setOnClickListener(v -> startQuizWithTime(3));
    }

    private void startQuizWithTime(int minutes) {
        Intent intent = new Intent(this, ProblemSolveActivity.class);
        intent.putExtra("genre", genre);
        intent.putExtra("type", "speed");
        intent.putExtra("selectedTime", minutes);
        startActivity(intent);
    }
}