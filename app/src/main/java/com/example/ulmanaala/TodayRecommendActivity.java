package com.example.ulmanaala;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulmanaala.api.ApiService;
import com.example.ulmanaala.client.RetrofitClient;
import com.example.ulmanaala.response.QuestionResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayRecommendActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuestionAdapter adapter;
    private List<QuestionResponse> allQuestions = new ArrayList<>();
    private List<QuestionResponse> filteredQuestions = new ArrayList<>();
    private String accessToken;
    private boolean showAllAnswers = false;
    private boolean showAllExplanations = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_recommend);

        recyclerView = findViewById(R.id.recycler_today_questions);
        adapter = new QuestionAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // SharedPreferences에서 JWT 토큰 불러오기
        SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        accessToken = prefs.getString("token", null);

        if (accessToken == null) {
            Toast.makeText(this, "로그인이 필요합니다", Toast.LENGTH_SHORT).show();
            return;
        }

        int genreId = 1; // 예시: 과학
        fetchLowAccuracyQuestions(genreId);

        Button btnToggleAllAnswers = findViewById(R.id.btn_toggle_all_answers);
        Button btnToggleAllExplanations = findViewById(R.id.btn_toggle_all_explanations);

        btnToggleAllAnswers.setOnClickListener(v -> {
            showAllAnswers = !showAllAnswers;
            adapter.setShowAllAnswers(showAllAnswers);
            btnToggleAllAnswers.setText(showAllAnswers ? "정답 숨기기" : "정답 보기");
        });

        btnToggleAllExplanations.setOnClickListener(v -> {
            showAllExplanations = !showAllExplanations;
            adapter.setShowAllExplanations(showAllExplanations);
            btnToggleAllExplanations.setText(showAllExplanations ? "해설 숨기기" : "해설 보기");
        });
    }

    private void fetchLowAccuracyQuestions(int genreId) {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getTodayRecommendedQuestions("Bearer " + accessToken, genreId)
                .enqueue(new Callback<List<QuestionResponse>>() {
                    @Override
                    public void onResponse(Call<List<QuestionResponse>> call, Response<List<QuestionResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            allQuestions = response.body();
                            filterLowAccuracyAndSortByAccuracy();
                        } else {
                            Toast.makeText(TodayRecommendActivity.this, "문제 불러오기 실패", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<QuestionResponse>> call, Throwable t) {
                        Toast.makeText(TodayRecommendActivity.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void filterLowAccuracyAndSortByAccuracy() {
        filteredQuestions.clear();
        for (QuestionResponse q : allQuestions) {
            if (q.getAccuracy() != null && q.getAccuracy() <= 50.0) {
                filteredQuestions.add(q);
            }
        }

        // 정답률 오름차순 정렬 (낮은 정답률이 먼저)
        Collections.sort(filteredQuestions, (q1, q2) -> {
            double acc1 = q1.getAccuracy() != null ? q1.getAccuracy() : 100.0;
            double acc2 = q2.getAccuracy() != null ? q2.getAccuracy() : 100.0;
            return Double.compare(acc1, acc2);
        });

        adapter.setItems(filteredQuestions);
    }
}
