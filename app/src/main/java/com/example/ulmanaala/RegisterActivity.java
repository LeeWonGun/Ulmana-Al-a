package com.example.ulmanaala;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulmanaala.api.ApiService;
import com.example.ulmanaala.client.RetrofitClient;
import com.example.ulmanaala.request.RegisterRequest;
import com.example.ulmanaala.response.RegisterResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword;
    private CheckBox chkScience, chkHistory, chkIdiom, chkGrammar, chkCapital, chkArt;
    private Button btnRegister;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.et_register_username);
        etEmail = findViewById(R.id.et_register_email);
        etPassword = findViewById(R.id.et_register_password);

        chkScience = findViewById(R.id.chk_science);
        chkHistory = findViewById(R.id.chk_history);
        chkIdiom = findViewById(R.id.chk_idiom);
        chkGrammar = findViewById(R.id.chk_grammar);
        chkCapital = findViewById(R.id.chk_capital);
        chkArt = findViewById(R.id.chk_art);

        btnRegister = findViewById(R.id.btn_register);
        apiService = RetrofitClient.getApiService();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "모든 필드를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 선택된 체크박스 수집
                List<String> selectedInterests = new ArrayList<>();
                if (chkScience.isChecked()) selectedInterests.add("과학");
                if (chkHistory.isChecked()) selectedInterests.add("역사");
                if (chkIdiom.isChecked()) selectedInterests.add("사자성어");
                if (chkGrammar.isChecked()) selectedInterests.add("맞춤법");
                if (chkCapital.isChecked()) selectedInterests.add("수도");
                if (chkArt.isChecked()) selectedInterests.add("미술");

                if (selectedInterests.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "최소 하나의 관심사를 선택하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedInterests.size() > 3) {
                    Toast.makeText(RegisterActivity.this, "최대 3개의 관심사만 선택할 수 있습니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 부족한 항목은 빈 문자열로 채움
                while (selectedInterests.size() < 3) {
                    selectedInterests.add("");
                }

                RegisterRequest request = new RegisterRequest(
                        username,
                        email,
                        password,
                        selectedInterests.get(0),
                        selectedInterests.get(1),
                        selectedInterests.get(2)
                );

                apiService.registerUser(request).enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(RegisterActivity.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "서버 연결 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}