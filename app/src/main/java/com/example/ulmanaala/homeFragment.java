package com.example.ulmanaala;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulmanaala.api.ApiService;
import com.example.ulmanaala.client.RetrofitClient;
import com.example.ulmanaala.response.DailyFactItem;
import com.example.ulmanaala.response.DailyFactResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class homeFragment extends Fragment {

    private static final String TAG = "HOME_FRAGMENT";
    private DailyFactAdapter dailyFactAdapter;

    public homeFragment() {}

    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // ✅ 1. RecyclerView 설정
        RecyclerView recyclerView = view.findViewById(R.id.dailyFactRecyclerView);
        dailyFactAdapter = new DailyFactAdapter();
        recyclerView.setAdapter(dailyFactAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // ✅ 2. SharedPreferences에서 이메일 꺼내기
        SharedPreferences prefs = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userEmail = prefs.getString("userEmail", null);

        // ✅ 3. 데일리 상식 API 호출
        if (userEmail != null) {
            ApiService apiService = RetrofitClient.getApiService();

            apiService.getDailyFacts(userEmail).enqueue(new Callback<DailyFactResponse>() {
                @Override
                public void onResponse(Call<DailyFactResponse> call, Response<DailyFactResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<DailyFactItem> factList = response.body().getDailyFacts();
                        Log.d(TAG, "✅ 받아온 상식 수: " + factList.size());
                        dailyFactAdapter.setFacts(factList);
                    } else {
                        Log.e(TAG, "❌ 데일리 상식 실패 - 응답 코드: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<DailyFactResponse> call, Throwable t) {
                    Log.e(TAG, "❌ 데일리 상식 에러: " + t.getMessage());
                }
            });
        } else {
            Log.e(TAG, "⚠️ SharedPreferences에 이메일 없음");
        }

        return view;
    }
}
