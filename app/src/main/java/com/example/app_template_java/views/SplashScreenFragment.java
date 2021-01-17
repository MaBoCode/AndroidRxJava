package com.example.app_template_java.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.example.app_template_java.R;
import com.example.app_template_java.databinding.FrgSplashscreenBinding;
import com.example.app_template_java.injects.base.BaseFragment;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenFragment extends BaseFragment {

    protected FrgSplashscreenBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FrgSplashscreenBinding.inflate(inflater, container, false);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.to_main_fragment);
            }
        }, 400);

        return binding.getRoot();
    }
}
