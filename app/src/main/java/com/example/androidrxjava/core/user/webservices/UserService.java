package com.example.androidrxjava.core.user.webservices;

import com.example.androidrxjava.core.user.User;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("users")
    Observable<List<User>> getUsers();

    @GET("users/{userId}")
    Single<User> getUser(
            @Path("userId") Long userId
    );

}
