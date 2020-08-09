package com.sankalpchauhan.flipkartgridchallenge.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {
    private ArrayList<Fragment> onboardingList = new ArrayList<>();

    public ViewPagerFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return onboardingList.get(position);
    }

    public void addFragment(Fragment fragment) {
        onboardingList.add(fragment);
    }

    @Override
    public int getItemCount() {
        return onboardingList.size();
    }
}
