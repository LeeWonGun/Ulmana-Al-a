package com.example.ulmanaala;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ulmanaala.model.QuizSet;

public class QuizSetsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_sets);  // 해당 Activity의 레이아웃 파일

        Intent intent = getIntent();

        if (savedInstanceState == null) {
            QuizSetsFragment fragment = new QuizSetsFragment();

            boolean hideAnswers = false;
            if (intent != null) {
                hideAnswers = intent.getBooleanExtra("hideAnswers", false);
            }

            Bundle args = new Bundle();
            args.putBoolean("hideAnswers", hideAnswers);
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }

        if (intent != null && intent.hasExtra("quizSet") && intent.getBooleanExtra("hideAnswers", false)) {
            QuizSet quizSet = (QuizSet) intent.getSerializableExtra("quizSet");

            QuizDetailFragment fragment = new QuizDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("quizSet", quizSet);
            bundle.putBoolean("hideAnswers", true);  // 정답 숨기기 설정
            fragment.setArguments(bundle);

            switchToFragment(fragment);
        }
    }

    // Fragment 전환 메서드
    private void switchToFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);  // 뒤로가기 지원
        transaction.commit();
    }
}