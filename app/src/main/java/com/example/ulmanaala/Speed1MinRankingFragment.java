package com.example.ulmanaala;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulmanaala.api.ApiService;
import com.example.ulmanaala.client.RetrofitClient;
import com.example.ulmanaala.model.RankingItem;
import com.example.ulmanaala.response.RankingResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Speed1MinRankingFragment extends Fragment {

    private RecyclerView recyclerView;
    private RankingAdapter adapter;
    private TextView myRankingView;


    public static Speed1MinRankingFragment newInstance() {
        return new Speed1MinRankingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        recyclerView = view.findViewById(R.id.ranking_recycler);
        myRankingView = view.findViewById(R.id.my_ranking);

        adapter = new RankingAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        loadRankings("speed");
        return view;
    }

    private void loadRankings(String mode) {
        String token = "Bearer " + getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token", "");

        mode = "speed_1min";

        ApiService apiService = RetrofitClient.getApiService();
        apiService.getRanking(token, mode).enqueue(new Callback<RankingResponse>() {
            @Override
            public void onResponse(Call<RankingResponse> call, Response<RankingResponse> response) {
                if (response.isSuccessful()) {
                    adapter.setItems(response.body().topRankings);
                    RankingItem me = response.body().myRanking;
                    myRankingView.setText("#" + me.rank + " " + me.nickname + " - " + (int) me.score + "점");
                }
            }

            @Override
            public void onFailure(Call<RankingResponse> call, Throwable t) {
                Log.e("Speed1MinRanking", "불러오기 실패", t);
            }
        });
    }
}
