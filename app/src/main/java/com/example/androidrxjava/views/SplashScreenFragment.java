package com.example.androidrxjava.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.example.androidrxjava.R;
import com.example.androidrxjava.databinding.FrgSplashscreenBinding;
import com.example.androidrxjava.injects.base.BaseFragment;

import java.util.Timer;
import java.util.TimerTask;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
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
