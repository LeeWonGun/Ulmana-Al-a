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


        // 과학 분야 선택시
        Button btnScience = findViewById(R.id.btn_science);
        btnScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genre="sciencce";

                Intent intent = new Intent(problemgenre_selection.this, problemtype_selection.class);
                intent.putExtra("genre",genre);
                startActivity(intent);
            }
        });


    }
}