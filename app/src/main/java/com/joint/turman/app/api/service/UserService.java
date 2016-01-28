package com.joint.turman.app.api.service;

import com.joint.turman.app.api.ApiUrls;
import com.joint.turman.app.model.base.Response;
import com.joint.turman.app.model.entity.User;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by dqf on 2016/1/8.
 */
public interface UserService {

    @GET(ApiUrls.LOGIN_VALIDATE_HTTP)
    Observable<Response<User>> login(
            @Query("username") String username,
            @Query("password") String password,
            @Query("deviceId") String deviceId
    );
}
