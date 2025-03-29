package com.example.ulmanaala;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class GenreActivity extends AppCompatActivity {

    private Button btn_selectgenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_genre);


    btn_selectgenre = findViewById(R.id.btn_selectgenre);
        btn_selectgenre.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(GenreActivity.this, MainActivity.class);
            startActivity(intent);
        }
    });
}
}