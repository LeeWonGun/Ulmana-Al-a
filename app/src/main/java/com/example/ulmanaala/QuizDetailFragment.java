package com.example.ulmanaala;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.ulmanaala.api.ApiService;
import com.example.ulmanaala.client.RetrofitClient;
import com.example.ulmanaala.model.QuizResult;
import com.example.ulmanaala.model.QuizSet;
import com.example.ulmanaala.response.QuestionDetailResponse;
import com.example.ulmanaala.response.WrongNoteSubmitResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizDetailFragment extends Fragment {

    private QuizSet quizSet;
    private List<QuizResult> wrongQuestions = new ArrayList<>();
    private Map<Integer, QuizResult> wrongQuestionMap = new HashMap<>();
    private Map<Integer, String> selectedAnswers = new HashMap<>();
    private int currentIndex = 0;
    private boolean hideAnswers = false;

    private TextView tvQuestionNumber, tvQuestionText, tvUserAnswer, tvCorrectAnswer;
    private RadioGroup rgOptions;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private Button btnPrev, btnNext, btnBack, btnSubmit, btnExplanation;

    private ApiService apiService;
    private String token;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_detail, container, false);

        apiService = RetrofitClient.getApiService();
        token = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token", "");

        if (getArguments() != null) {
            quizSet = (QuizSet) getArguments().getSerializable("quizSet");
            hideAnswers = getArguments().getBoolean("hideAnswers", false);
        }

        tvQuestionNumber = view.findViewById(R.id.tv_question_number);
        tvQuestionText = view.findViewById(R.id.tv_question_text);
        tvUserAnswer = view.findViewById(R.id.tv_user_answer);
        tvCorrectAnswer = view.findViewById(R.id.tv_correct_answer);
        rgOptions = view.findViewById(R.id.rg_options);
        rbOption1 = view.findViewById(R.id.rb_option1);
        rbOption2 = view.findViewById(R.id.rb_option2);
        rbOption3 = view.findViewById(R.id.rb_option3);
        rbOption4 = view.findViewById(R.id.rb_option4);
        btnPrev = view.findViewById(R.id.btn_prev);
        btnNext = view.findViewById(R.id.btn_next);
        btnBack = view.findViewById(R.id.btn_back);
        btnSubmit = view.findViewById(R.id.btn_submit);
        btnExplanation = view.findViewById(R.id.btn_explanation);

        if (hideAnswers) {
            tvUserAnswer.setVisibility(View.GONE);
            tvCorrectAnswer.setVisibility(View.GONE);
            btnExplanation.setVisibility(View.GONE);
        }

        btnPrev.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                fetchQuestionDetails();
            } else {
                startActivity(new Intent(getActivity(), QuizSetsActivity.class));
                requireActivity().finish();
            }
        });

        btnNext.setOnClickListener(v -> {
            if (currentIndex < wrongQuestions.size() - 1) {
                currentIndex++;
                fetchQuestionDetails();
            }
        });

        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), QuizSetsActivity.class));
            requireActivity().finish();
        });

        btnSubmit.setOnClickListener(v -> {
            List<Map<String, Object>> resultList = new ArrayList<>();

            for (QuizResult result : wrongQuestions) {
                int qId = result.getQuestionId();
                String selectedIdx = selectedAnswers.getOrDefault(qId, "-1");
                String selectedText = "";

                switch (selectedIdx) {
                    case "1": selectedText = result.getOption1(); break;
                    case "2": selectedText = result.getOption2(); break;
                    case "3": selectedText = result.getOption3(); break;
                    case "4": selectedText = result.getOption4(); break;
                }

                Log.d("submitAnswer", "QID: " + qId + ", 선택 번호: " + selectedIdx + ", 보기 텍스트: " + selectedText);

                Map<String, Object> item = new HashMap<>();
                item.put("question_id", qId);
                item.put("user_answer", selectedIdx);  // 번호가 아닌 보기 텍스트를 전송
                resultList.add(item);
            }

            Map<String, Object> body = new HashMap<>();
            body.put("quiz_results", resultList);
            body.put("quiz_type", "wrong_note");
            body.put("total_questions", resultList.size()); // 🔹 문제 수도 함께 전송 (선택적이지만 추천)

            // 🔹 기존 오답노트 세션의 ID를 함께 전송
            if (quizSet != null) {
                body.put("origin_session_id", quizSet.getId());
            }

            apiService.submitWrongNote("Bearer " + token, body)
                    .enqueue(new Callback<WrongNoteSubmitResponse>() {
                        @Override
                        public void onResponse(Call<WrongNoteSubmitResponse> call, Response<WrongNoteSubmitResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                WrongNoteSubmitResponse.Summary summary = response.body().getSummary();

                                Intent intent = new Intent(getActivity(), QuizResultActivity.class);
                                intent.putExtra("correctCount", summary.getCorrectCount());
                                intent.putExtra("wrongCount", summary.getWrongCount());
                                intent.putExtra("score", summary.getTotalScore());
                                intent.putExtra("subject", quizSet.getQuizType());
                                intent.putExtra("genre", quizSet.getGenre());
                                startActivity(intent);
                                requireActivity().finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<WrongNoteSubmitResponse> call, Throwable t) {
                            Log.e("WrongNoteSubmit", "API 호출 실패: " + t.getMessage());
                        }
                    });
        });

        btnExplanation.setOnClickListener(v -> {
            QuizResult result = wrongQuestions.get(currentIndex);
            new android.app.AlertDialog.Builder(getContext())
                    .setTitle("해설")
                    .setMessage(result.getExplanation() != null ? result.getExplanation() : "해설이 없습니다.")
                    .setPositiveButton("확인", null)
                    .show();
        });

        fetchAllQuestionDetails();
        return view;
    }

    private void fetchAllQuestionDetails() {
        if (quizSet == null || quizSet.getQuizResults() == null) return;
        List<QuizResult> allResults = quizSet.getQuizResults();



        for (QuizResult result : allResults) {
            int questionId = result.getQuestionId();
            apiService.getQuestionDetailsWithAuth(questionId, "Bearer " + token)
                    .enqueue(new Callback<QuestionDetailResponse>() {
                        @Override
                        public void onResponse(Call<QuestionDetailResponse> call, Response<QuestionDetailResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                QuestionDetailResponse res = response.body();

                                boolean isCorrect = Objects.equals(result.getUserAnswer(), res.getAnswer());

                                if (!isCorrect) {
                                    QuizResult quizResult = new QuizResult(
                                            res.getQuestionId(), res.getQuestionText(),
                                            result.getUserAnswer(), res.getAnswer(),
                                            false, 0, res.getExplanation(),
                                            "장르", 0,
                                            res.getOption1(), res.getOption2(),
                                            res.getOption3(), res.getOption4()
                                    );
                                    wrongQuestionMap.put(res.getQuestionId(), quizResult);
                                }

                                if (wrongQuestionMap.size() + getCorrectCount() == allResults.size()) {
                                    for (QuizResult r : allResults) {
                                        QuizResult q = wrongQuestionMap.get(r.getQuestionId());
                                        if (q != null) wrongQuestions.add(q);
                                    }
                                    if (!wrongQuestions.isEmpty()) fetchQuestionDetails();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<QuestionDetailResponse> call, Throwable t) {
                            Log.e("QuizDetailFragment", "문제 불러오기 실패: " + t.getMessage());
                        }
                    });
        }
    }

    private int getCorrectCount() {
        int count = 0;
        for (QuizResult result : quizSet.getQuizResults()) {
            if (Objects.equals(result.getUserAnswer(), result.getCorrectAnswer())) {
                count++;
            }
        }
        return count;
    }

    private void fetchQuestionDetails() {
        if (wrongQuestions.isEmpty()) return;
        QuizResult result = wrongQuestions.get(currentIndex);
        apiService.getQuestionDetailsWithAuth(result.getQuestionId(), "Bearer " + token)
                .enqueue(new Callback<QuestionDetailResponse>() {
                    @Override
                    public void onResponse(Call<QuestionDetailResponse> call, Response<QuestionDetailResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            updateQuestionDetails(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<QuestionDetailResponse> call, Throwable t) {
                        Log.e("QuizDetailFragment", "API 실패: " + t.getMessage());
                    }
                });
    }

    private void updateQuestionDetails(QuestionDetailResponse detail) {
        tvQuestionNumber.setText("문제 " + (currentIndex + 1));
        tvQuestionText.setText(detail.getQuestionText());

        // 보기 텍스트 설정
        rbOption1.setText(detail.getOption1());
        rbOption2.setText(detail.getOption2());
        rbOption3.setText(detail.getOption3());
        rbOption4.setText(detail.getOption4());

        // 버튼 활성/비활성 설정 (풀이 모드에서만 선택 가능)
        boolean enabled = hideAnswers;
        rbOption1.setEnabled(enabled);
        rbOption2.setEnabled(enabled);
        rbOption3.setEnabled(enabled);
        rbOption4.setEnabled(enabled);

        // 리스너 초기화 (중복 방지)
        rgOptions.setOnCheckedChangeListener(null);

        // 색상 초기화
        RadioButton[] buttons = {rbOption1, rbOption2, rbOption3, rbOption4};
        for (RadioButton btn : buttons) {
            btn.setButtonTintList(null);  // 기본 색상
        }

        // 선택 복원 (풀이 모드)
        String selected = selectedAnswers.getOrDefault(detail.getQuestionId(), "-1");
        switch (selected) {
            case "1": rbOption1.setChecked(true); break;
            case "2": rbOption2.setChecked(true); break;
            case "3": rbOption3.setChecked(true); break;
            case "4": rbOption4.setChecked(true); break;
            default: rgOptions.clearCheck(); break;
        }

        // 해설 모드일 경우 정답/오답 색상 표시
        if (!hideAnswers) {
            String sel = detail.getUserAnswer();
            String correct = detail.getAnswer();

            int red = ContextCompat.getColor(requireContext(), R.color.red);
            int blue = ContextCompat.getColor(requireContext(), R.color.blue);
            int gray = ContextCompat.getColor(requireContext(), R.color.gray);

            for (int i = 0; i < 4; i++) {
                RadioButton btn = buttons[i];
                String num = String.valueOf(i + 1);

                if (num.equals(sel) && !num.equals(correct)) {
                    btn.setButtonTintList(ContextCompat.getColorStateList(requireContext(), R.color.red));
                    btn.setChecked(true);
                } else if (num.equals(correct)) {
                    btn.setButtonTintList(ContextCompat.getColorStateList(requireContext(), R.color.blue));
                    btn.setChecked(true);
                } else {
                    btn.setButtonTintList(ContextCompat.getColorStateList(requireContext(), R.color.gray));
                }
            }

            // 내가 선택한 답, 정답 텍스트 표시
            if (sel == null || sel.equals("-1") || sel.trim().isEmpty()) {
                tvUserAnswer.setText("내가 선택한 답: 없음");
            } else {
                String text = "";
                switch (sel) {
                    case "1": text = detail.getOption1(); break;
                    case "2": text = detail.getOption2(); break;
                    case "3": text = detail.getOption3(); break;
                    case "4": text = detail.getOption4(); break;
                }
                tvUserAnswer.setText("내가 선택한 답: " + text);
            }
            tvCorrectAnswer.setText("정답: " + correct);
        }

        // 리스너 재등록 (선택값 저장)
        rgOptions.setOnCheckedChangeListener((group, checkedId) -> {
            String selectedVal = "-1";
            if (checkedId == R.id.rb_option1) selectedVal = "1";
            else if (checkedId == R.id.rb_option2) selectedVal = "2";
            else if (checkedId == R.id.rb_option3) selectedVal = "3";
            else if (checkedId == R.id.rb_option4) selectedVal = "4";

            selectedAnswers.put(detail.getQuestionId(), selectedVal);
            Log.d("선택 저장", "문제 ID: " + detail.getQuestionId() + ", 선택 번호: " + selectedVal);
        });

        // 버튼 표시 제어
        btnPrev.setVisibility(View.VISIBLE);
        btnNext.setVisibility(currentIndex == wrongQuestions.size() - 1 ? View.GONE : View.VISIBLE);
        btnBack.setVisibility(!hideAnswers && currentIndex == wrongQuestions.size() - 1 ? View.VISIBLE : View.GONE);
        btnSubmit.setVisibility(hideAnswers && currentIndex == wrongQuestions.size() - 1 ? View.VISIBLE : View.GONE);
    }
}
