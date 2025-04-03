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
import com.example.ulmanaala.request.FindIdRequest;
import com.example.ulmanaala.response.FindIdResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindIdActivity extends AppCompatActivity {

    private EditText edtUsername;
    private Button btnFindId;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        edtUsername = findViewById(R.id.edtUsername);
        btnFindId = findViewById(R.id.btnFindId);
        tvResult = findViewById(R.id.tvResult);

        btnFindId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findId();
            }
        });
    }

    private void findId() {
        String username = edtUsername.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(this, "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getApiService();
        FindIdRequest request = new FindIdRequest(username);
        Call<FindIdResponse> call = apiService.findId(request);

        call.enqueue(new Callback<FindIdResponse>() {
            @Override
            public void onResponse(Call<FindIdResponse> call, Response<FindIdResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvResult.setText(response.body().getMessage());
                    tvResult.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(FindIdActivity.this, "아이디를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FindIdResponse> call, Throwable t) {
                Toast.makeText(FindIdActivity.this, "네트워크 오류 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }
}