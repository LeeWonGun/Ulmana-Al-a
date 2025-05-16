package com.example.ulmanaala;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RankingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private RankingPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        tabLayout = findViewById(R.id.ranking_tab);
        viewPager = findViewById(R.id.ranking_viewpager);

        pagerAdapter = new RankingPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0: tab.setText("스피드 1분"); break;
                        case 1: tab.setText("스피드 3분"); break;
                        case 2: tab.setText("문제풀이"); break;
                        case 3: tab.setText("총 문제풀이"); break;
                    }
                }).attach();
    }
}