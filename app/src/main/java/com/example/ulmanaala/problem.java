package com.example.ulmanaala;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class problem extends Fragment {

    private static final String ARG_PROBLEM_NUM = "problemNum";

    public static problem newInstance(String problemNum, String param2) {
        problem fragment = new problem();
        Bundle args = new Bundle();
        args.putString(ARG_PROBLEM_NUM, problemNum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_problem, container, false);

        // 문제 번호 가져오기
        if (getArguments() != null) {
            String problemNum = getArguments().getString(ARG_PROBLEM_NUM);

            // 문제 번호에 따른 내용 표시 (예: 텍스트뷰에 출력)
            TextView textView = view.findViewById(R.id.text_problemcontent);
            textView.setText("문제 번호: " + problemNum); // 예시로 문제 번호 출력
        }

        return view;
    }
}
