package com.example.ulmanaala;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class problemgenre_selection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_problemgenre_selection);

        // 과학 장르 선택시
        Button btnScience = findViewById(R.id.btn_science);
        btnScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genre = "과학";

                Intent intent = new Intent(problemgenre_selection.this, problemtype_selection.class);
                intent.putExtra("장르", genre);
                startActivity(intent);
            }
        });

        //역사 장르 선택시
        Button btnHistory = findViewById(R.id.btn_history);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genre = "역사";

                Intent intent = new Intent(problemgenre_selection.this, problemtype_selection.class);
                intent.putExtra("genre", genre);
                startActivity(intent);
            }
        });

        //사자성어 장르 선택시
        Button btnFouridioms = findViewById(R.id.btn_four_idioms);
        btnFouridioms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genre = "사자성어";

                Intent intent = new Intent(problemgenre_selection.this, problemtype_selection.class);
                intent.putExtra("genre", genre);
                startActivity(intent);
            }
        });

        //맞춤법 장르 선택시
        Button btnSpelling = findViewById(R.id.btn_spelling);
        btnSpelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genre = "맞춤법";

                Intent intent = new Intent(problemgenre_selection.this, problemtype_selection.class);
                intent.putExtra("genre", genre);
                startActivity(intent);
            }
        });

        //수도 장르 선택시
        Button btnCapital = findViewById(R.id.btn_capital);
        btnCapital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genre = "수도";

                Intent intent = new Intent(problemgenre_selection.this, problemtype_selection.class);
                intent.putExtra("genre", genre);
                startActivity(intent);
            }
        });



    }
}
