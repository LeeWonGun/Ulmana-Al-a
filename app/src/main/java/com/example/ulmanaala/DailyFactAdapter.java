package com.example.ulmanaala;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulmanaala.response.DailyFactItem;

import java.util.ArrayList;
import java.util.List;

public class DailyFactAdapter extends RecyclerView.Adapter<DailyFactAdapter.FactViewHolder> {

    private List<DailyFactItem> factList = new ArrayList<>();

    // ✅ 데이터 업데이트 메서드 추가
    public void setFacts(List<DailyFactItem> facts) {
        this.factList = facts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_fact, parent, false);
        return new FactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FactViewHolder holder, int position) {
        DailyFactItem item = factList.get(position);
        holder.genreTextView.setText("분야: " + item.getGenreName());
        holder.explanationTextView.setText(item.getExplanation());
    }

    @Override
    public int getItemCount() {
        return factList != null ? factList.size() : 0;
    }

    static class FactViewHolder extends RecyclerView.ViewHolder {
        TextView genreTextView;
        TextView explanationTextView;

        public FactViewHolder(@NonNull View itemView) {
            super(itemView);
            genreTextView = itemView.findViewById(R.id.genreTextView);
            explanationTextView = itemView.findViewById(R.id.explanationTextView);
        }
    }
}
