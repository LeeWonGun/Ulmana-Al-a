package com.example.ulmanaala;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulmanaala.model.QuizSet;

public class QuizDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_detail);

        if (savedInstanceState == null) {
            QuizDetailFragment fragment = new QuizDetailFragment();
            Bundle bundle = new Bundle();

            QuizSet quizSet = (QuizSet) getIntent().getSerializableExtra("quizSet");
            bundle.putSerializable("quizSet", quizSet);
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}