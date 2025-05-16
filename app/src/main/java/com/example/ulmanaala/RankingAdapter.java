package com.example.ulmanaala;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ulmanaala.model.RankingItem;

import java.util.ArrayList;
import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private List<RankingItem> items = new ArrayList<>();

    public void setItems(List<RankingItem> list) {
        items = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RankingItem item = items.get(position);

        holder.rank.setText("#" + item.rank);
        holder.name.setText(item.nickname);
        holder.score.setText((int)item.score + "점");

        // Glide로 이미지 로딩
        if (item.profileImage != null && !item.profileImage.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(item.profileImage)
                    .placeholder(android.R.drawable.sym_def_app_icon) // 기본 이미지
                    .error(android.R.drawable.ic_menu_report_image)   // 오류 시 이미지
                    .circleCrop() // 원형 처리 (선택)
                    .into(holder.image);
        } else {
            holder.image.setImageResource(android.R.drawable.sym_def_app_icon); // 기본 이미지
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rank, name, score;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.item_rank);
            name = itemView.findViewById(R.id.item_nickname);
            score = itemView.findViewById(R.id.item_score);
            image = itemView.findViewById(R.id.item_profile);
        }
    }
}
