package com.example.ulmanaala;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ulmanaala.model.QuizSet;

import java.util.ArrayList;
import java.util.List;

public class QuizSetAdapter extends RecyclerView.Adapter<QuizSetAdapter.ViewHolder> {

    private List<QuizSet> quizSets;
    private OnQuizSetClickListener listener;

    public interface OnQuizSetClickListener {
        void onQuizSetClick(QuizSet selectedQuizSet);
    }

    public QuizSetAdapter(List<QuizSet> quizSets, OnQuizSetClickListener listener) {
        this.quizSets = quizSets != null ? quizSets : new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quiz_set, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QuizSet quizSet = quizSets.get(position);

        // 날짜
        holder.date.setText("푼 날짜: " + quizSet.getDate());
        // 장르 (genre 필드 사용)
        holder.genre.setText("장르: " + quizSet.getGenre());
        // 총 문제 수
        holder.totalQuestions.setText("총 문제 수: " + quizSet.getTotalQuestions());
        // 오답 수
        holder.wrongAnswers.setText("오답 수: " + quizSet.getWrongAnswers());

        // 종목(quiz type) 보기 좋게 변환
        String readableQuizType = getReadableQuizType(quizSet.getQuizType());
        holder.quizType.setText("종목: " + readableQuizType);

        // 재풀이 버튼 클릭 리스너
        holder.quizSetButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onQuizSetClick(quizSet);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizSets != null ? quizSets.size() : 0;
    }

    public void updateQuizSets(List<QuizSet> newQuizSets) {
        this.quizSets = newQuizSets != null ? newQuizSets : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, genre, totalQuestions, wrongAnswers, quizType;
        Button quizSetButton;

        ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.quizSetDate);
            genre = itemView.findViewById(R.id.quizSetGenre);
            totalQuestions = itemView.findViewById(R.id.quizSetTotalQuestions);
            wrongAnswers = itemView.findViewById(R.id.quizSetWrongAnswers);
            quizType = itemView.findViewById(R.id.quizSetQuizType);
            quizSetButton = itemView.findViewById(R.id.quizSetButton);
        }
    }

    // quizType(String) → 보기 좋은 한글 문자열로 변환
    private String getReadableQuizType(String quizType) {
        if (quizType == null) return "알 수 없음";
        switch (quizType) {
            case "test25":
                return "25문제 시험";
            case "test50":
                return "50문제 시험";
            case "speed":
                return "스피드 퀴즈";
            case "wrong_note":
                return "오답노트";
            default:
                return "기타";
        }
    }
}