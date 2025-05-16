package com.example.ulmanaala;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView textQuizType;
    private TextView textGenre;
    private TextView textScore;
    private TextView textCorrect;
    private TextView textWrong;
    private TextView textTimeTaken;
    private Button btnRetry;
    private Button btnGoHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 뷰 바인딩
        textQuizType = findViewById(R.id.text_quiz_type);
        textGenre     = findViewById(R.id.text_genre);
        textScore     = findViewById(R.id.text_score);
        textCorrect   = findViewById(R.id.text_correct);
        textWrong     = findViewById(R.id.text_wrong);
        textTimeTaken = findViewById(R.id.text_time_taken);
        btnRetry      = findViewById(R.id.btn_retry);
        btnGoHome     = findViewById(R.id.btn_go_home);

        // 인텐트에서 값 꺼내기
        Intent intent      = getIntent();
        String quizType    = intent.getStringExtra("quizType");
        String genre       = intent.getStringExtra("genre");
        int score       = intent.getIntExtra("score", 0);
        int correctCount   = intent.getIntExtra("correct", 0);
        int wrongCount     = intent.getIntExtra("wrong", 0);
        int timeTakenSec   = intent.getIntExtra("timeTaken", 0);

        // 퀴즈 유형 한글화
        String typeLabel;
        if ("test25".equals(quizType))        typeLabel = "25문제 시험";
        else if ("test50".equals(quizType))   typeLabel = "50문제 시험";
        else if ("speed".equals(quizType))    typeLabel = "스피드 퀴즈";
        else if ("wrong_note".equals(quizType)) typeLabel = "오답노트";
        else                                  typeLabel = "기타";

        // 과목 한글화
        String genreLabel;
        if ("science".equals(genre))      genreLabel = "과학";
        else if ("history".equals(genre)) genreLabel = "역사";
        else if ("spelling".equals(genre))genreLabel = "맞춤법";
        else if ("idiom".equals(genre))   genreLabel = "사자성어";
        else if ("capital".equals(genre)) genreLabel = "수도";
        else if ("art".equals(genre))     genreLabel = "미술";
        else                               genreLabel = "기타";

        // 화면에 값 세팅
        textQuizType.setText("종목: " + typeLabel);
        textGenre   .setText("과목: " + genreLabel);
        textScore   .setText("점수: " + score + "점");
        textCorrect .setText("정답 수: " + correctCount + "개");
        textWrong   .setText("오답 수: " + wrongCount + "개");

        // 스피드 퀴즈일 때만 소요 시간 표시
        if ("speed".equals(quizType) && timeTakenSec > 0) {
            int minutes = timeTakenSec / 60;
            int seconds = timeTakenSec % 60;
            textTimeTaken.setText(
                    String.format("소요 시간: %d분 %02d초", minutes, seconds)
            );
            textTimeTaken.setVisibility(View.VISIBLE);
        } else {
            textTimeTaken.setVisibility(View.GONE);
        }

        // 다시 풀기
        btnRetry.setOnClickListener(v -> finish());

        // 홈으로 돌아가기
        btnGoHome.setOnClickListener(v -> {
            Intent home = new Intent(ResultActivity.this, MainActivity.class);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(home);
            finish();
        });
    }
}