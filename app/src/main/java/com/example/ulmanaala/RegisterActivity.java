package com.example.ulmanaala;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulmanaala.api.ApiService;
import com.example.ulmanaala.client.RetrofitClient;
import com.example.ulmanaala.request.RegisterRequest;
import com.example.ulmanaala.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword;
    private RadioGroup radioGroupInterest1, radioGroupInterest2, radioGroupInterest3;
    private Button btnRegister;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.et_register_username);
        etEmail = findViewById(R.id.et_register_email);
        etPassword = findViewById(R.id.et_register_password);
        radioGroupInterest1 = findViewById(R.id.radioGroup_interest_1);
        radioGroupInterest2 = findViewById(R.id.radioGroup_interest_2);
        radioGroupInterest3 = findViewById(R.id.radioGroup_interest_3);
        btnRegister = findViewById(R.id.btn_register);

        apiService = RetrofitClient.getApiService();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String interest1 = getSelectedRadioText(radioGroupInterest1);
                String interest2 = getSelectedRadioText(radioGroupInterest2);
                String interest3 = getSelectedRadioText(radioGroupInterest3);

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "모든 필드를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                RegisterRequest request = new RegisterRequest(username, email, password, interest1, interest2, interest3);
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

    private String getSelectedRadioText(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            return selectedRadioButton.getText().toString();
        }
        return "";  // 선택되지 않은 경우 빈 문자열 반환
    }
}