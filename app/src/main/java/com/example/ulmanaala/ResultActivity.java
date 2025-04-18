package com.example.ulmanaala;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulmanaala.api.ApiService;
import com.example.ulmanaala.client.RetrofitClient;
import com.example.ulmanaala.request.QuizResultRequest;
import com.example.ulmanaala.response.QuizResultResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {

    private TextView textScore, textCorrectCount, textWrongCount;
    private Button btnGoHome, btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 결과값 받아오기
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        int correctCount = intent.getIntExtra("correctCount", 0);
        int wrongCount = intent.getIntExtra("wrongCount", 0);
        ArrayList<Integer> questionIds = intent.getIntegerArrayListExtra("questionIds");
        ArrayList<Integer> userAnswers = intent.getIntegerArrayListExtra("userAnswers");
        int userId = intent.getIntExtra("userId", -1);
        String token = intent.getStringExtra("token");

        // 뷰 초기화
        textScore = findViewById(R.id.text_score);
        textCorrectCount = findViewById(R.id.text_correct);
        textWrongCount = findViewById(R.id.text_wrong);
        btnGoHome = findViewById(R.id.btn_go_home);
        btnRetry = findViewById(R.id.btn_retry);

        // 결과값 설정
        textScore.setText("점수: " + score + "점");
        textCorrectCount.setText("정답 수: " + correctCount + "개");
        textWrongCount.setText("오답 수: " + wrongCount + "개");

        // ✅ 여기서 결과 서버로 전송
        if (token != null && userId != -1 && questionIds != null && userAnswers != null) {
            String jwtToken = "Bearer " + token;
            submitQuizResult(jwtToken, userId, questionIds, userAnswers);
        }

        // 홈으로 이동
        btnGoHome.setOnClickListener(v -> {
            Intent homeIntent = new Intent(ResultActivity.this, MainActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
            finish();
        });

        // 다시 풀기 (현재 액티비티 재시작 or 이전 액티비티 재호출)
        btnRetry.setOnClickListener(v -> {
            finish(); // 현재 ResultActivity만 종료 → ProblemSolveActivity로 복귀
        });
    }

    private void submitQuizResult(String token, int userId, ArrayList<Integer> questionIds, ArrayList<Integer> userAnswers) {
        ApiService apiService = RetrofitClient.getApiService();

        QuizResultRequest request = new QuizResultRequest(questionIds, userAnswers, userId);

        apiService.submitQuizResult(token, request).enqueue(new Callback<QuizResultResponse>() {
            @Override
            public void onResponse(Call<QuizResultResponse> call, Response<QuizResultResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ResultActivity.this, "퀴즈 결과가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ResultActivity.this, "결과 저장 실패: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("QuizSubmit", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<QuizResultResponse> call, Throwable t) {
                Toast.makeText(ResultActivity.this, "서버 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("QuizSubmit", "Failure", t);
            }
        });
    }
}