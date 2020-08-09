package com.sankalpchauhan.flipkartgridchallenge.ui.fragment.onboarding;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sankalpchauhan.flipkartgridchallenge.databinding.FragmentOnboarding2Binding;


public class OnBoardingFragment2 extends Fragment {
    private FragmentOnboarding2Binding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOnboarding2Binding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        return rootView;
    }
}

