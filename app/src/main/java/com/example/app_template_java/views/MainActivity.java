package com.example.app_template_java.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.app_template_java.R;
import com.example.app_template_java.databinding.ActMainBinding;
import com.example.app_template_java.injects.base.BaseActivity;

public class MainActivity extends BaseActivity {

    protected ActMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.act_main);
    }
}