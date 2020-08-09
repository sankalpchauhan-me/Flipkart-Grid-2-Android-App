package com.sankalpchauhan.flipkartgridchallenge.ui.fragment.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sankalpchauhan.flipkartgridchallenge.config.DefaultPrefSettings;
import com.sankalpchauhan.flipkartgridchallenge.databinding.FragmentOnboarding3Binding;
import com.sankalpchauhan.flipkartgridchallenge.databinding.FragmentOnboarding4Binding;
import com.sankalpchauhan.flipkartgridchallenge.ui.view.OnBoardingActivity;

import static com.sankalpchauhan.flipkartgridchallenge.util.DarkModeHandler.themeHelper;

public class OnBoardingFragment4 extends Fragment {
    private FragmentOnboarding4Binding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOnboarding4Binding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        if(!DefaultPrefSettings.getInstance().isDarkMode()) {
            binding.darkMode.setOnClickListener(view -> {
                DefaultPrefSettings.getInstance().enableDarkMode(true);
                themeHelper(true);
                if (getActivity() instanceof OnBoardingActivity) {
                    ((OnBoardingActivity) getActivity()).binding.button.performClick();
                }
            });
        } else {
            binding.darkMode.setText("Enable Light Mode");
            binding.darkMode.setOnClickListener(view -> {
                DefaultPrefSettings.getInstance().enableDarkMode(false);
                themeHelper(false);
                if (getActivity() instanceof OnBoardingActivity) {
                    ((OnBoardingActivity) getActivity()).binding.button.performClick();
                }
            });
        }
        return rootView;
    }
}
