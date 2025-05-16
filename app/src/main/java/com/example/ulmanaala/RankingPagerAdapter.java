package com.example.ulmanaala;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class RankingPagerAdapter extends FragmentStateAdapter {

    public RankingPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return Speed1MinRankingFragment.newInstance();   // ⏱️ 1분
            case 1: return Speed3MinRankingFragment.newInstance();   // ⏱️ 3분
            case 2: return SolveRankingFragment.newInstance();       // 🧠 문제풀이
            case 3: return TotalSolveRankingFragment.newInstance();  // 📊 누적 총합
            default: return new Fragment();  // fallback
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}