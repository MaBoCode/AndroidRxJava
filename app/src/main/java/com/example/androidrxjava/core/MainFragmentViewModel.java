package com.example.androidrxjava.core;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.androidrxjava.core.user.User;
import com.example.androidrxjava.core.user.webservices.UserService;
import com.example.androidrxjava.utils.Logs;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainFragmentViewModel extends ViewModel {

    public enum LoadingStatus {
        LOADING,
        NOT_LOADING
    }

    protected UserService userService;

    protected SavedStateHandle savedStateHandle;

    protected MutableLiveData<List<User>> _usersLiveData = new MutableLiveData<>();
    public LiveData<List<User>> usersLiveData = _usersLiveData;

    protected MutableLiveData<User> _userLiveData = new MutableLiveData<>();
    public LiveData<User> userLiveData = _userLiveData;

    protected MutableLiveData<LoadingStatus> _loadingLiveData = new MutableLiveData<>();
    public LiveData<LoadingStatus> loadingLiveData = _loadingLiveData;

    @ViewModelInject
    public MainFragmentViewModel(UserService userService, @Assisted SavedStateHandle savedStateHandle) {
        this.userService = userService;
        this.savedStateHandle = savedStateHandle;
    }

    public void getUsers() {
        userService.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .delay(1, TimeUnit.SECONDS)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        _loadingLiveData.postValue(LoadingStatus.LOADING);
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Throwable {
                        _loadingLiveData.postValue(LoadingStatus.NOT_LOADING);
                    }
                })
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Throwable {
                        _usersLiveData.postValue(users);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Logs.error(this, throwable.getMessage());
                    }
                });
    }

    public void getUser(Long userId) {
        userService.getUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .delay(1, TimeUnit.SECONDS)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        _loadingLiveData.postValue(LoadingStatus.LOADING);
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Throwable {
                        _loadingLiveData.postValue(LoadingStatus.NOT_LOADING);
                    }
                })
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Throwable {
                        _userLiveData.postValue(user);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Logs.error(this, throwable.getMessage());
                    }
                });
    }

}
