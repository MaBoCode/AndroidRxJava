package com.example.androidrxjava.views;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidrxjava.core.MainFragmentViewModel;
import com.example.androidrxjava.core.MainFragmentViewModel.LoadingStatus;
import com.example.androidrxjava.core.user.User;
import com.example.androidrxjava.databinding.FrgMainBinding;
import com.example.androidrxjava.injects.base.BaseFragment;

import org.androidannotations.annotations.EFragment;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@EFragment
public class MainFragment extends BaseFragment {

    protected FrgMainBinding binding;

    protected MainFragmentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FrgMainBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(MainFragmentViewModel.class);

        initObservers();

        Long userId = new Long(1);
        viewModel.getUser(userId);

        viewModel.getUsers();

        return binding.getRoot();
    }

    public void initObservers() {

        viewModel.userLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.usernameTxtView.setText(user.username);
                startAnimations();
            }
        });

        viewModel.usersLiveData.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                String usersCount = String.format("%s users", users.size());
                binding.usersCountTxtView.setText(usersCount);
            }
        });

        viewModel.loadingLiveData.observe(this, new Observer<LoadingStatus>() {
            @Override
            public void onChanged(LoadingStatus status) {
                int visiblity = status == LoadingStatus.LOADING ? View.VISIBLE : View.GONE;
                binding.loader.setVisibility(visiblity);
            }
        });
    }

    public void startAnimations() {
        ObjectAnimator usernameTxtViewAnimator = ObjectAnimator.ofFloat(binding.usernameTxtView, "alpha", 0f, 1f);
        ObjectAnimator userCountTxtViewAnimator = ObjectAnimator.ofFloat(binding.usersCountTxtView, "alpha", 0f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(usernameTxtViewAnimator, userCountTxtViewAnimator);
        animatorSet.setDuration(300L);
        animatorSet.setStartDelay(100L);

        animatorSet.start();
    }

}
