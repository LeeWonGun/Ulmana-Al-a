package com.example.ulmanaala;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulmanaala.api.ApiService;
import com.example.ulmanaala.client.RetrofitClient;
import com.example.ulmanaala.request.ResetPasswordRequest;
import com.example.ulmanaala.response.ResetPasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText edtEmail, edtNewPassword;
    private Button btnResetPassword;
    private TextView tvResetMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        edtEmail = findViewById(R.id.edtEmail);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        tvResetMessage = findViewById(R.id.tvResetMessage);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = edtEmail.getText().toString().trim();
        String newPassword = edtNewPassword.getText().toString().trim();

        if (email.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(this, "이메일과 새 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 이메일 형식 검증 (간단한 예시)
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "유효한 이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 비밀번호 길이 체크 (8자 이상)
        if (newPassword.length() < 8) {
            Toast.makeText(this, "비밀번호는 최소 8자리 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getApiService();
        ResetPasswordRequest request = new ResetPasswordRequest(email, newPassword);
        Call<ResetPasswordResponse> call = apiService.resetPassword(request);

        call.enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvResetMessage.setText(response.body().getMessage());
                    tvResetMessage.setVisibility(View.VISIBLE);
                } else {
                    try {
                        // 서버에서 반환한 오류 메시지를 읽음
                        String errorMessage = response.errorBody().string();

                        if (errorMessage.contains("새로운 비밀번호는 기존 비밀번호와 다르게 설정해야 합니다.")) {
                            Toast.makeText(ResetPasswordActivity.this, "새 비밀번호가 기존 비밀번호와 동일합니다. 다른 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, "비밀번호 재설정 실패: 이메일 또는 비밀번호 확인", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(ResetPasswordActivity.this, "오류 발생: 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                Toast.makeText(ResetPasswordActivity.this, "네트워크 오류 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }
}