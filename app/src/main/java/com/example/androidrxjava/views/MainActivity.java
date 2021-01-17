package com.example.androidrxjava.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.androidrxjava.R;
import com.example.androidrxjava.databinding.ActMainBinding;
import com.example.androidrxjava.injects.base.BaseActivity;

import org.androidannotations.annotations.EActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@EActivity
public class MainActivity extends BaseActivity {

    protected ActMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.act_main);
    }
}