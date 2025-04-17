package com.example.ulmanaala;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    Fragment homeFragment;
    Fragment studyFragment;
    Fragment chatbotFragment;
    Fragment myinfoFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        homeFragment= new homeFragment();
        studyFragment=new studyFragment();
        chatbotFragment=new chatbotFragment();
        myinfoFragment=new myinfoFragment();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId=item.getItemId();
                Fragment fragment = null;

                if(itemId == R.id.homeFrag){

                    fragment = homeFragment;

                } else if (itemId == R.id.studyFrag) {

                    fragment = studyFragment;

                } else if (itemId == R.id.chatbotFrag) {

                    fragment = chatbotFragment;

                }else if (itemId == R.id.myinfoFrag) {

                    fragment = myinfoFragment;

                }
                return loadFragment(fragment);
            }
        });

    }
    boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment,fragment)
                    .commit();
            return true;
        }else {
            return false;
        }
    }
}