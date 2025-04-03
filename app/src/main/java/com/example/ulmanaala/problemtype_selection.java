package com.example.ulmanaala;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class problemtype_selection extends AppCompatActivity {

    private String genre; // 액티비티에서 전달받은 분야 저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_problemtype_selection);

        // 액티비티에서 전달받은 분야 데이터 추출
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            genre = receivedIntent.getStringExtra("genre");
        }

        Button btntest30 = findViewById(R.id.btn_test30);

        // 시험30문제 버튼 클릭
        btntest30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "test30";


                Intent intent = new Intent(problemtype_selection.this, problemsolve.class);
                intent.putExtra("genre", genre); //  분야
                intent.putExtra("type", type); //  방식
                startActivity(intent);
            }
        });
    }
}
