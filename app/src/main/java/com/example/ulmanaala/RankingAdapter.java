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

        String imageUrl = item.profileImage;
        if (imageUrl == null || imageUrl.isEmpty()) {
            holder.image.setImageResource(R.drawable.ic_person);
        } else {
            // 절대 URL이면 그대로, 상대경로면 서버 주소 붙이기
            if (!imageUrl.startsWith("http://") && !imageUrl.startsWith("https://")) {
                imageUrl = "http://10.0.2.2:8000" + imageUrl;
            }

            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .circleCrop()
                    .into(holder.image);
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
