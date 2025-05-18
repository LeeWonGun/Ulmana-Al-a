package com.example.ulmanaala;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecommendPagerAdapter extends RecyclerView.Adapter<RecommendPagerAdapter.ViewHolder> {

    private List<RecommendItem> items;
    private Context context;

    public RecommendPagerAdapter(List<RecommendItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recommend_slide, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecommendItem item = items.get(position);
        holder.textView.setText(item.getText());
        holder.button.setOnClickListener(v -> {
            Intent intent = new Intent(context, item.getTargetActivity());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button button;
        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_recommend_text);
            button = itemView.findViewById(R.id.btn_recommend_goto);
        }
    }
}
