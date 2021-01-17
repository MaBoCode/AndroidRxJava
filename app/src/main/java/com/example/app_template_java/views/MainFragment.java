package com.example.app_template_java.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_template_java.databinding.FrgMainBinding;
import com.example.app_template_java.injects.base.BaseFragment;

public class MainFragment extends BaseFragment {

    protected FrgMainBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FrgMainBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
