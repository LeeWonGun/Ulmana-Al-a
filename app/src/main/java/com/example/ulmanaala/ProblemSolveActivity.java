package com.example.ulmanaala;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.ulmanaala.api.ApiService;
import com.example.ulmanaala.client.RetrofitClient;
import com.example.ulmanaala.response.QuestionResponse;
import com.example.ulmanaala.response.SpeedQuizResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProblemSolveActivity extends AppCompatActivity {

    private int currentProblemNumber = 1;
    private int maxProblemNumber = 10;
    private Button btnNext;
    private List<QuestionResponse> questionList = new ArrayList<>();
    private List<Integer> userAnswers = new ArrayList<>();

    private CountDownTimer countDownTimer;
    private long remainingTimeMillis;
    private TextView textTimer;

    private String quizType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problemsolve);

        Intent intent = getIntent();
        String genre = intent.getStringExtra("genre");
        quizType = intent.getStringExtra("type");
        int genreId = getGenreId(genre);

        textTimer = findViewById(R.id.text_timer);
        setMaxProblemNumber(quizType);

        if ("speed".equals(quizType)) {
            int selectedTimeInMinutes = intent.getIntExtra("selectedTime", 1);
            remainingTimeMillis = selectedTimeInMinutes * 60 * 1000;
            textTimer.setVisibility(TextView.VISIBLE);
            startCountdownTimer();
        } else {
            textTimer.setVisibility(TextView.GONE);
        }

        fetchQuestions(quizType, genreId);

        TextView textProblemNum = findViewById(R.id.text_problemnum);
        textProblemNum.setText(String.valueOf(currentProblemNumber));

        Button btnBefore = findViewById(R.id.btn_beforeproblem);
        btnNext = findViewById(R.id.btn_nextproblem);

        btnBefore.setOnClickListener(v -> {
            saveCurrentAnswer();
            if (currentProblemNumber > 1) {
                currentProblemNumber--;
                updateProblemDisplay();
                updateButtonState();
            }
        });

        btnNext.setOnClickListener(v -> {
            saveCurrentAnswer();
            if (currentProblemNumber == maxProblemNumber) {
                submitTest();
            } else {
                currentProblemNumber++;
                updateProblemDisplay();
                updateButtonState();
            }
        });

        updateButtonState();
    }

    private int getGenreId(String genre) {
        switch (genre) {
            case "science": return 1;
            case "history": return 2;
            case "spelling": return 3;
            case "idiom": return 4;
            case "capital": return 5;
            case "art": return 6;
            default: return 1;
        }
    }

    private void setMaxProblemNumber(String type) {
        if (type != null) {
            switch (type) {
                case "test50": maxProblemNumber = 50; break;
                case "test25": maxProblemNumber = 25; break;
                case "speed": maxProblemNumber = 100; break;
                default: maxProblemNumber = 15;
            }
        }
    }

    private void updateButtonState() {
        btnNext.setText(currentProblemNumber == maxProblemNumber ? "제출" : "다음");
        Button btnBefore = findViewById(R.id.btn_beforeproblem);
        btnBefore.setEnabled(currentProblemNumber > 1);
    }

    private void submitTest() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        saveCurrentAnswer();

        List<Integer> questionIds = new ArrayList<>();
        for (QuestionResponse q : questionList) {
            questionIds.add(q.getQuestionId());
        }

        // 로그인 정보 주석 처리
        /*
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);
        String token = prefs.getString("token", null);

        if (userId == -1 || token == null) {
            Toast.makeText(this, "로그인 정보가 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        QuizResultRequest request = new QuizResultRequest(questionIds, userAnswers, userId);
        ApiService apiService = RetrofitClient.getApiService();

        apiService.submitQuizResult("Bearer " + token, request).enqueue(new Callback<QuizResultResponse>() {
            @Override
            public void onResponse(Call<QuizResultResponse> call, Response<QuizResultResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 원래는 결과 화면으로 이동
                    // 지금은 MainActivity로 이동
                    Intent intent = new Intent(ProblemSolveActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ProblemSolveActivity.this, "채점 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QuizResultResponse> call, Throwable t) {
                Toast.makeText(ProblemSolveActivity.this, "서버 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        */

        // 서버 연동 없이 MainActivity로 바로 이동
        Intent intent = new Intent(ProblemSolveActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void updateProblemDisplay() {
        TextView textProblemNum = findViewById(R.id.text_problemnum);
        textProblemNum.setText(String.valueOf(currentProblemNumber));
        updateProblemFragment();
    }

    private void updateProblemFragment() {
        if (questionList == null || questionList.size() == 0) return;

        QuestionResponse currentQuestion = questionList.get(currentProblemNumber - 1);
        int selectedOption = userAnswers.get(currentProblemNumber - 1);

        problemFragment newProblemFragment = problemFragment.newInstance(
                currentQuestion.getQuestionText(),
                currentQuestion.getOption1(),
                currentQuestion.getOption2(),
                currentQuestion.getOption3(),
                currentQuestion.getOption4(),
                selectedOption
        );

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newProblemFragment);
        transaction.commit();
    }

    private void saveCurrentAnswer() {
        problemFragment currentFragment = (problemFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment != null) {
            int selectedAnswer = currentFragment.getSelectedAnswer();
            userAnswers.set(currentProblemNumber - 1, selectedAnswer);
        }
    }

    private void fetchQuestions(String type, int genreId) {
        ApiService apiService = RetrofitClient.getApiService();

        if ("speed".equals(type)) {
            apiService.getSpeedQuiz(genreId).enqueue(new Callback<SpeedQuizResponse>() {
                @Override
                public void onResponse(Call<SpeedQuizResponse> call, Response<SpeedQuizResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        questionList = response.body().getQuestions();
                        userAnswers = new ArrayList<>(Collections.nCopies(questionList.size(), -1));
                        updateProblemFragment();
                    } else {
                        Toast.makeText(ProblemSolveActivity.this, "문제 로딩 실패", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SpeedQuizResponse> call, Throwable t) {
                    Toast.makeText(ProblemSolveActivity.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Call<List<QuestionResponse>> call = type.equals("test25") ?
                    apiService.get25Questions(genreId) :
                    apiService.get50Questions(genreId);

            call.enqueue(new Callback<List<QuestionResponse>>() {
                @Override
                public void onResponse(Call<List<QuestionResponse>> call, Response<List<QuestionResponse>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        questionList = response.body();
                        userAnswers = new ArrayList<>(Collections.nCopies(questionList.size(), -1));
                        updateProblemFragment();
                    } else {
                        Toast.makeText(ProblemSolveActivity.this, "문제 로딩 실패", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<QuestionResponse>> call, Throwable t) {
                    Toast.makeText(ProblemSolveActivity.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(remainingTimeMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTimeMillis = millisUntilFinished;
                updateTimerUI();
            }

            @Override
            public void onFinish() {
                Toast.makeText(ProblemSolveActivity.this, "시간 종료! 자동 제출합니다.", Toast.LENGTH_SHORT).show();
                submitTest();
            }
        }.start();
    }

    private void updateTimerUI() {
        int seconds = (int) (remainingTimeMillis / 1000) % 60;
        int minutes = (int) ((remainingTimeMillis / 1000) / 60);
        textTimer.setText(String.format("%02d:%02d", minutes, seconds));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}