package com.example.ulmanaala;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class problemsolve extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_problemsolve);


        Intent intent = getIntent();
        String genre = intent.getStringExtra("genre");
        String type = intent.getStringExtra("type");


        TextView textselectedgenre = findViewById(R.id.text_selectedgenre);
        TextView textselectedtype = findViewById(R.id.text_selectedtype);
        textselectedgenre.setText(genre);
        textselectedtype.setText(type);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        problem problemFragment = problem.newInstance("", "");
        fragmentTransaction.replace(R.id.fragment_container, problemFragment);
        fragmentTransaction.commit();
    }
}
