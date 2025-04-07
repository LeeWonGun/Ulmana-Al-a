package com.example.ulmanaala;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class problemsolve extends AppCompatActivity {

    private int currentProblemNumber = 1;
    private int maxProblemNumber = 10;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problemsolve);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        String genre = intent.getStringExtra("genre");

        TextView textSelectedGenre = findViewById(R.id.text_selectedgenre);
        TextView textSelectedType = findViewById(R.id.text_selectedtype);

        textSelectedGenre.setText(genre);
        textSelectedType.setText(type+"문제");

        setMaxProblemNumber(type);


        // 문제 번호 표시 TextView 초기화
        TextView textProblemNum = findViewById(R.id.text_problemnum);
        textProblemNum.setText(String.valueOf(currentProblemNumber));

        // 버튼 참조 초기화
        Button btnBefore = findViewById(R.id.btn_beforeproblem);
        btnNext = findViewById(R.id.btn_nextproblem);

        // 이전 버튼 클릭 리스너
        btnBefore.setOnClickListener(v -> {
            if (currentProblemNumber > 1) {
                currentProblemNumber--;
                updateProblemDisplay();
                updateButtonState();
            }
        });

        // 다음/제출 버튼 클릭 리스너
        btnNext.setOnClickListener(v -> {
            if (currentProblemNumber == maxProblemNumber) {
                submitTest();
            } else {
                currentProblemNumber++;
                updateProblemDisplay();
                updateButtonState();
            }
        });

        // 초기 버튼 상태 설정
        updateButtonState();
        updateProblemFragment();
    }

    // 문제 유형에 따른 최대 문제 수 설정 메서드
    private void setMaxProblemNumber(String type) {
        if (type != null) {
            switch (type) {
                case "퀴즈50":
                    maxProblemNumber = 50;
                    break;
                case "퀴즈25":
                    maxProblemNumber = 25;
                    break;
                default:

            }
        }
        Log.d("ProblemSolve", "Max Problems: " + maxProblemNumber);
    }

    private void updateButtonState() {
        btnNext.setText(
                currentProblemNumber == maxProblemNumber ? "제출" : "다음"
        );

        Button btnBefore = findViewById(R.id.btn_beforeproblem);
        btnBefore.setEnabled(currentProblemNumber > 1);
    }

    private void submitTest() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void updateProblemDisplay() {
        TextView textProblemNum = findViewById(R.id.text_problemnum);
        textProblemNum.setText(String.valueOf(currentProblemNumber));
        updateProblemFragment();
    }

    private void updateProblemFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        problem newProblemFragment = problem.newInstance(
                String.valueOf(currentProblemNumber),
                ""
        );

        transaction.replace(R.id.fragment_container, newProblemFragment);
        transaction.commit();
    }
}
