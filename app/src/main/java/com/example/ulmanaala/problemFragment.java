package com.example.ulmanaala;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class problemFragment extends Fragment {

    private static final String ARG_QUESTION_TEXT = "questionText";
    private static final String ARG_OPTION1 = "option1";
    private static final String ARG_OPTION2 = "option2";
    private static final String ARG_OPTION3 = "option3";
    private static final String ARG_OPTION4 = "option4";
    private static final String ARG_SELECTED_OPTION = "selectedOption";

    private int selectedAnswer = -1; // 사용자가 선택한 답을 저장하는 변수
    private RadioGroup radioGroup;

    public static problemFragment newInstance(String questionText, String option1, String option2, String option3, String option4, int selectedOption) {
        problemFragment fragment = new problemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION_TEXT, questionText);
        args.putString(ARG_OPTION1, option1);
        args.putString(ARG_OPTION2, option2);
        args.putString(ARG_OPTION3, option3);
        args.putString(ARG_OPTION4, option4);
        args.putInt(ARG_SELECTED_OPTION, selectedOption);  // 선택한 답안 전달
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_problem, container, false);

        if (getArguments() != null) {
            String questionText = getArguments().getString(ARG_QUESTION_TEXT);
            String option1 = getArguments().getString(ARG_OPTION1);
            String option2 = getArguments().getString(ARG_OPTION2);
            String option3 = getArguments().getString(ARG_OPTION3);
            String option4 = getArguments().getString(ARG_OPTION4);
            selectedAnswer = getArguments().getInt(ARG_SELECTED_OPTION, -1); // 기존 선택된 답을 가져옴

            // 문제 텍스트
            TextView textViewQuestion = view.findViewById(R.id.text_problemcontent);
            textViewQuestion.setText(questionText);

            // 선택지
            radioGroup = view.findViewById(R.id.radio_group_options);
            RadioButton radioButton1 = view.findViewById(R.id.radio_option1);
            RadioButton radioButton2 = view.findViewById(R.id.radio_option2);
            RadioButton radioButton3 = view.findViewById(R.id.radio_option3);
            RadioButton radioButton4 = view.findViewById(R.id.radio_option4);

            radioButton1.setText(option1);
            radioButton2.setText(option2);
            radioButton3.setText(option3);
            radioButton4.setText(option4);

            // 이전 선택 반영
            switch (selectedAnswer) {
                case 1: radioButton1.setChecked(true); break;
                case 2: radioButton2.setChecked(true); break;
                case 3: radioButton3.setChecked(true); break;
                case 4: radioButton4.setChecked(true); break;
            }

            // 라디오버튼 체크 리스너 추가 - 사용자가 선택한 답을 저장
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (checkedId == R.id.radio_option1) {
                    selectedAnswer = 1;
                } else if (checkedId == R.id.radio_option2) {
                    selectedAnswer = 2;
                } else if (checkedId == R.id.radio_option3) {
                    selectedAnswer = 3;
                } else if (checkedId == R.id.radio_option4) {
                    selectedAnswer = 4;
                }
            });
        }

        return view;
    }

    // 현재 선택된 답안을 반환하는 메서드
    public int getSelectedAnswer() {
        return selectedAnswer; // 사용자가 선택한 답안 반환
    }
}