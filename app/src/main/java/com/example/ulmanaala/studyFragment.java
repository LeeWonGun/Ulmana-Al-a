package com.example.ulmanaala;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link studyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class studyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public studyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment studyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static studyFragment newInstance(String param1, String param2) {
        studyFragment fragment = new studyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 레이아웃을 inflate해서 view 객체 생성
        View view = inflater.inflate(R.layout.fragment_study, container, false);

        // 버튼 찾아서 클릭 이벤트 설정
        Button btnRanking = view.findViewById(R.id.ranking);
        btnRanking.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RankingActivity.class);
            startActivity(intent);
        });

        // view 반환 (여기 하나만 있어야 함)
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button problemSolvingButton = view.findViewById(R.id.btn_problemsolving);/** 과학 선택시 화면이동*/
        problemSolvingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), problemgenre_selection.class);
                startActivity(intent);
            }
        });

    }




}