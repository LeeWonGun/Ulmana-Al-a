package com.example.ulmanaala;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulmanaala.response.QuestionResponse;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<QuestionResponse> questionList = new ArrayList<>();

    private boolean showAllAnswers = false;
    private boolean showAllExplanations = false;

    public void setShowAllAnswers(boolean show) {
        showAllAnswers = show;
        notifyDataSetChanged();
    }

    public void setShowAllExplanations(boolean show) {
        showAllExplanations = show;
        notifyDataSetChanged();
    }

    public void setItems(List<QuestionResponse> list) {
        questionList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        QuestionResponse item = questionList.get(position);

        holder.tvQuestion.setText(item.getQuestion_text());

        holder.tvAccuracy.setText(item.getAccuracy() != null
                ? "정답률: " + item.getAccuracy() + "%"
                : "정답률: -");

        holder.tvOption1.setText("① " + item.getOption1());
        holder.tvOption2.setText("② " + item.getOption2());
        holder.tvOption3.setText("③ " + item.getOption3());
        holder.tvOption4.setText("④ " + item.getOption4());

        holder.tvCorrectAnswer.setText("정답: " + item.getCorrect_answer());
        holder.tvCorrectAnswer.setVisibility(showAllAnswers ? View.VISIBLE : View.GONE);

        holder.tvExplanation.setText("해설: " + item.getExplanation());
        holder.tvExplanation.setVisibility(showAllExplanations ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion, tvAccuracy;
        TextView tvOption1, tvOption2, tvOption3, tvOption4;
        TextView tvCorrectAnswer, tvExplanation;
        Button btnToggleAllAnswer, btnToggleAllExplanation;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tv_question_text);
            tvAccuracy = itemView.findViewById(R.id.tv_accuracy);
            tvOption1 = itemView.findViewById(R.id.tv_option1);
            tvOption2 = itemView.findViewById(R.id.tv_option2);
            tvOption3 = itemView.findViewById(R.id.tv_option3);
            tvOption4 = itemView.findViewById(R.id.tv_option4);
            tvCorrectAnswer = itemView.findViewById(R.id.tv_correct_answer);
            tvExplanation = itemView.findViewById(R.id.tv_explanation);
            btnToggleAllAnswer = itemView.findViewById(R.id.btn_toggle_all_answers);
            btnToggleAllExplanation = itemView.findViewById(R.id.btn_toggle_all_explanations);
        }
    }
}
