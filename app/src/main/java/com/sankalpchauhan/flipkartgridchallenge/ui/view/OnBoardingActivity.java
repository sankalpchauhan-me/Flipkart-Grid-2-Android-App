package com.sankalpchauhan.flipkartgridchallenge.ui.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayoutMediator;
import com.sankalpchauhan.flipkartgridchallenge.MainActivity;
import com.sankalpchauhan.flipkartgridchallenge.R;
import com.sankalpchauhan.flipkartgridchallenge.config.DefaultPrefSettings;
import com.sankalpchauhan.flipkartgridchallenge.databinding.ActivityOnBoardingBinding;
import com.sankalpchauhan.flipkartgridchallenge.ui.adapter.ViewPagerFragmentAdapter;
import com.sankalpchauhan.flipkartgridchallenge.ui.fragment.onboarding.OnBoardingFragment1;
import com.sankalpchauhan.flipkartgridchallenge.ui.fragment.onboarding.OnBoardingFragment2;
import com.sankalpchauhan.flipkartgridchallenge.ui.fragment.onboarding.OnBoardingFragment3;
import com.sankalpchauhan.flipkartgridchallenge.ui.fragment.onboarding.OnBoardingFragment4;

public class OnBoardingActivity extends AppCompatActivity implements ViewPager2.PageTransformer {
    public ActivityOnBoardingBinding binding;
    private ViewPagerFragmentAdapter viewPagerFragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPagerFragmentAdapter.addFragment(new OnBoardingFragment1());
        viewPagerFragmentAdapter.addFragment(new OnBoardingFragment2());
        viewPagerFragmentAdapter.addFragment(new OnBoardingFragment3());
        viewPagerFragmentAdapter.addFragment(new OnBoardingFragment4());
        binding.viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.viewPager.setAdapter(viewPagerFragmentAdapter);
        binding.viewPager.setPageTransformer(this);
        binding.viewPager.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
//        binding.viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, ((tab, position) -> {})).attach();
        binding.button.setOnClickListener(view -> {
            DefaultPrefSettings.getInstance().setUserFirstTime(false);
            Intent i = new Intent(OnBoardingActivity.this, LoginActivity.class);
            startActivity(i);
            this.finish();
        });

    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        page.findViewById(R.id.onboardImage).setTranslationX(page.getWidth() * -position / 2);
        page.findViewById(R.id.onboardTitle).setTranslationX(-position * page.getWidth() / 4);
//        page.findViewById(R.id.onboardDescription).setTranslationX(-position * page.getWidth() / 4);
//        page.findViewById(R.id.share_fab).setRotation(position * 180.0f);
        if(position <= -1.0F || position >= 1.0F) {
            page.findViewById(R.id.onboardImage).setTranslationX(page.getWidth() * position / 2);
        } else if( position == 0.0F ) {
            page.findViewById(R.id.onboardImage).setTranslationX(page.getWidth() * position / 2);
        } else {
            page.findViewById(R.id.onboardImage).setTranslationX(page.getWidth() * -position / 2);
        }
    }
}
