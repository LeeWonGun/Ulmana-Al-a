package com.example.ulmanaala;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ulmanaala.R;
import com.example.ulmanaala.model.QuizResult;

import java.util.List;

public class QuizResultAdapter extends RecyclerView.Adapter<QuizResultAdapter.ViewHolder> {

    private List<QuizResult> quizResults;

    public QuizResultAdapter(List<QuizResult> quizResults) {
        this.quizResults = quizResults;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuizType, tvQuizInfo, tvQuizScore;

        public ViewHolder(View view) {
            super(view);
            tvQuizType = view.findViewById(R.id.tv_quiz_type);
            tvQuizInfo = view.findViewById(R.id.tv_quiz_info);
            tvQuizScore = view.findViewById(R.id.tv_quiz_score);
        }
    }

    @NonNull
    @Override
    public QuizResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuizResult result = quizResults.get(position);
        holder.tvQuizType.setText("장르: " + result.getGenreName());
        holder.tvQuizInfo.setText("문제: " + result.getQuestionText());
        holder.tvQuizScore.setText("정답: " + (result.isCorrect() ? "O" : "X"));
    }

    @Override
    public int getItemCount() {
        return quizResults.size();
    }
}