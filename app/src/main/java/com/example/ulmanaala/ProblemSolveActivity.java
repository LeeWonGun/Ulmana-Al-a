package com.example.ulmanaala;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.ulmanaala.api.ApiService;
import com.example.ulmanaala.client.RetrofitClient;
import com.example.ulmanaala.request.QuizResultRequest;
import com.example.ulmanaala.response.QuestionResponse;
import com.example.ulmanaala.response.QuizResultResponse;
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
    private Button btnNext, btnBefore;
    private TextView textTimer;
    private List<QuestionResponse> questionList = new ArrayList<>();
    private List<Integer> userAnswers = new ArrayList<>();
    private CountDownTimer countDownTimer;
    private long remainingTimeMillis;
    private long startTimeMillis;
    private String quizType;
    private int selectedTimeInMinutes = 1; // 클래스 변수로 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problemsolve);

        textTimer = findViewById(R.id.text_timer);
        btnNext   = findViewById(R.id.btn_nextproblem);
        btnBefore = findViewById(R.id.btn_beforeproblem);

        Intent intent = getIntent();
        String genre = intent.getStringExtra("genre");
        quizType     = intent.getStringExtra("type");
        selectedTimeInMinutes = intent.getIntExtra("selectedTime", 1);
        int genreId  = getGenreId(genre);

        setMaxProblemNumber(quizType);

        if ("speed".equals(quizType)) {
            remainingTimeMillis = selectedTimeInMinutes * 60L * 1000L;
            textTimer.setVisibility(TextView.VISIBLE);
            startTimeMillis = System.currentTimeMillis();
            startCountdownTimer();
        } else {
            textTimer.setVisibility(TextView.GONE);
        }

        fetchQuestions(quizType, genreId);

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
        if (type == null) return;
        switch (type) {
            case "test50": maxProblemNumber = 50; break;
            case "test25": maxProblemNumber = 25; break;
            case "speed":  maxProblemNumber = 100; break;
            default: maxProblemNumber = 15;
        }
    }

    private void updateButtonState() {
        btnNext.setText(currentProblemNumber == maxProblemNumber ? "제출" : "다음");
        btnBefore.setEnabled(currentProblemNumber > 1);
    }

    private void submitTest() {
        saveCurrentAnswer();

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null) {
            Toast.makeText(this, "로그인 정보가 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<QuizResultRequest.QuizAnswer> answerList = new ArrayList<>();
        for (int i = 0; i < questionList.size(); i++) {
            int qId = questionList.get(i).getQuestionId();
            int selected = userAnswers.size() > i ? userAnswers.get(i) : -1;
            answerList.add(new QuizResultRequest.QuizAnswer(qId, selected));
        }

        String genre = getIntent().getStringExtra("genre");
        int genreId = getGenreId(genre);
        QuizResultRequest request = new QuizResultRequest(genreId, answerList, quizType, selectedTimeInMinutes, "");

        ApiService apiService = RetrofitClient.getApiService();
        apiService.submitQuizResult("Bearer " + token, request)
                .enqueue(new Callback<QuizResultResponse>() {
                    @Override
                    public void onResponse(Call<QuizResultResponse> call, Response<QuizResultResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            QuizResultResponse.Summary summary = response.body().getSummary();

                            int timeTakenSeconds = 0;
                            if ("speed".equals(quizType)) {
                                long now = System.currentTimeMillis();
                                timeTakenSeconds = (int) ((now - startTimeMillis) / 1000);
                            }

                            Intent intent = new Intent(ProblemSolveActivity.this, ResultActivity.class);
                            intent.putExtra("genre", genre);
                            intent.putExtra("quizType", quizType);
                            intent.putExtra("score", summary.getScore());
                            intent.putExtra("totalQuestions", summary.getTotal());
                            intent.putExtra("correct", summary.getCorrect());
                            intent.putExtra("wrong", summary.getWrong());
                            intent.putExtra("timeTaken", timeTakenSeconds);

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ProblemSolveActivity.this, "퀴즈 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<QuizResultResponse> call, Throwable t) {
                        Toast.makeText(ProblemSolveActivity.this, "서버 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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
                        currentProblemNumber = 1;
                        updateProblemDisplay();
                        updateButtonState();
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
                        currentProblemNumber = 1;
                        updateProblemDisplay();
                        updateButtonState();
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

    private void updateProblemDisplay() {
        TextView textProblemNum = findViewById(R.id.text_problemnum);
        textProblemNum.setText(String.valueOf(currentProblemNumber));
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        QuestionResponse q = questionList.get(currentProblemNumber - 1);
        problemFragment frag = problemFragment.newInstance(
                q.getQuestionText(), q.getOption1(), q.getOption2(),
                q.getOption3(), q.getOption4(), userAnswers.get(currentProblemNumber - 1));
        tx.replace(R.id.fragment_container, frag).commit();
    }

    private void saveCurrentAnswer() {
        problemFragment frag = (problemFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (frag != null) {
            userAnswers.set(currentProblemNumber - 1, frag.getSelectedAnswer());
        }
    }

    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(remainingTimeMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTimeMillis = millisUntilFinished;
                long m = millisUntilFinished / 1000 / 60;
                long s = (millisUntilFinished / 1000) % 60;
                textTimer.setText(String.format("%02d:%02d", m, s));
            }

            @Override
            public void onFinish() {
                textTimer.setText("00:00");
                submitTest();
            }
        }.start();
    }
}