package com.joint.turman.app.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joint.turman.app.AppContext;
import com.joint.turman.app.api.service.UserService;
import com.squareup.okhttp.OkHttpClient;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by dqf on 2016/1/8.
 */
public class ApiRetrofit {
    private final static String TAG = ApiRetrofit.class.getSimpleName();

    private final RestAdapter restAdapter;

    private final static Gson gson = new GsonBuilder()
            .serializeNulls()
            .create();


    ApiRetrofit() {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(15, TimeUnit.SECONDS);

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept-Language", Locale.getDefault().toString());
                request.addHeader("Host", ApiUrls.HOST);
                request.addHeader("Connection", "Keep-Alive");
                request.addHeader("User-Agent", ApiHelper.getUserAgent(AppContext.instance()));
            }
        };

        restAdapter = new RestAdapter.Builder()
                .setClient(new OkClient(client))
                .setEndpoint(ApiUrls.URL_APP_API_HOST)
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog(TAG))
                .build();

    }

    public UserService getUserService() {
        return restAdapter.create(UserService.class);
    }
}
