package com.joint.turman.app.api;

import com.joint.turman.app.api.service.UserService;

/**
 * Created by dqf on 2016/1/8.
 */
public class ApiFactory {

    private static final Object monitor = new Object();

    private static UserService userService;

    public static UserService getUserService() {
        synchronized (monitor) {
            if (userService == null) {
                userService = new ApiRetrofit().getUserService();
            }
            return userService;
        }
    }


}
