package com.example.ulmanaala;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulmanaala.api.ApiService;
import com.example.ulmanaala.client.RetrofitClient;
import com.example.ulmanaala.model.QuizSet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizSetsFragment extends Fragment {

    private RecyclerView recyclerView;
    private QuizSetAdapter adapter;
    private List<QuizSet> quizSets;
    private String token;

    private boolean hideAnswers = false; // 🔹 정답 숨김 플래그

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_sets, container, false);

        // 🔹 전달된 플래그 확인
        if (getArguments() != null) {
            hideAnswers = getArguments().getBoolean("hideAnswers", false);
        }

        recyclerView = view.findViewById(R.id.recyclerViewQuizSets);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // 🔹 어댑터 생성 (클릭 리스너 포함)
        adapter = new QuizSetAdapter(new ArrayList<>(), selectedQuizSet -> {
            QuizDetailFragment fragment = new QuizDetailFragment();

            Bundle args = new Bundle();
            args.putSerializable("quizSet", selectedQuizSet);
            args.putBoolean("hideAnswers", hideAnswers); // 🔸 hideAnswers 플래그 전달
            fragment.setArguments(args);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(adapter);

        token = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                .getString("token", "");

        if (token.isEmpty()) {
            Toast.makeText(requireContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
        } else {
            fetchQuizSets();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchQuizSets();  // 🔁 다시 API 호출
    }

    private void fetchQuizSets() {
        Log.d("QuizSetsFragment", "fetchQuizSets() 호출됨");

        ApiService apiService = RetrofitClient.getApiService();
        apiService.getQuizSessions("Bearer " + token).enqueue(new Callback<List<QuizSet>>() {
            @Override
            public void onResponse(Call<List<QuizSet>> call, Response<List<QuizSet>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    quizSets = response.body();
                    Log.d("QuizSetsFragment", "받은 세트 수: " + quizSets.size());
                    adapter.updateQuizSets(quizSets);
                } else {
                    Log.e("QuizSetsFragment", "서버 응답 실패: " + response.message());
                    if (isAdded()) {
                        Toast.makeText(requireContext(), "서버 응답 실패: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<QuizSet>> call, Throwable t) {
                Log.e("QuizSetsFragment", "서버 오류: " + t.getMessage());
                if (isAdded()) {
                    Toast.makeText(requireContext(), "서버 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}