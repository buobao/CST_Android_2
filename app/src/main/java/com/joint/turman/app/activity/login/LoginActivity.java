package com.joint.turman.app.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.joint.turman.app.AppContext;
import com.joint.turman.app.api.ApiFactory;
import com.joint.turman.app.api.ApiHttpClient;
import com.joint.turman.app.model.base.Response;
import com.joint.turman.app.model.base.Result;
import com.joint.turman.app.model.entity.User;
import com.joint.turman.app.utils.StringUtils;
import com.joint.turman.app.utils.TDevice;
import com.joint.turman.app.utils.TLog;
import com.joint.turman.app.utils.UIHelper;
import com.joint.turman.app.widget.view.InputMethodRelativeLayout;
import com.joint.turman.cst_android_2.R;
import com.loopj.android.http.AsyncHttpClient;

import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.protocol.HttpContext;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dqf on 2016/1/7.
 */
public class LoginActivity extends Activity implements View.OnClickListener,InputMethodRelativeLayout.OnSizeChangedListenner {

    protected static final String TAG = "LoginActivity";

    @InjectView(R.id.inputAreaLayout)
    protected InputMethodRelativeLayout inputMethodRelativeLayout;
    @InjectView(R.id.scrollAreaLayout)
    protected LinearLayout scrollAreaLayout;
    @InjectView(R.id.bottomLayout)
    protected RelativeLayout bottomLayout;
    @InjectView(R.id.face)
    protected ImageView mIvUserFace;
    @InjectView(R.id.cell_username)
    protected EditText mEtUserName;
    @InjectView(R.id.cell_password)
    protected EditText mEtPassword;
    @InjectView(R.id.btn_login)
    protected Button mBtnLogin;

    @InjectView(R.id.btn_register_quick)
    protected Button mBtnQuick;

    private String mUserName, mPassword;
    private boolean isRememberMe;
    private boolean hasFace = false;

    private SharedPreferences mGlobalSettings;

    private boolean prepareForLogin() {
        if (!TDevice.hasInternet()) {
            AppContext.showToastShort(R.string.tip_no_internet);
            return false;
        }
        String uName = mEtUserName.getText().toString();
        if (TextUtils.isEmpty(uName)) {
            AppContext.showToastShort(R.string.tip_please_input_username);
            mEtUserName.requestFocus();
            return false;
        }
        String pwd = mEtPassword.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            AppContext.showToastShort(R.string.tip_please_input_password);
            mEtPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void handleLogin(final boolean isRememberMe) {
        if (!prepareForLogin()){
            return;
        }
        mUserName = mEtUserName.getText().toString();
        mPassword = mEtPassword.getText().toString();
        this.isRememberMe = isRememberMe;
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();

        if(StringUtils.isNotEmpty(deviceId)){
            ApiFactory.getUserService().login(mUserName,mPassword,deviceId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<Response<User>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            //请求错误，用户名或密码错误，user对象为空，抛出异常，这里统一处理为“用户名密码”，修改服务端代码
                            AppContext.instance().cleanLoginInfo();
                            System.out.println("Login Error");
                            AppContext.showToast("用户名或密码错误!");
                        }

                        @Override
                        public void onNext(Response<User> jsonElementResponse) {
                            AsyncHttpClient client = ApiHttpClient.getHttpClient();
                            HttpContext httpContext = client.getHttpContext();
                            CookieStore cookies = (CookieStore) httpContext
                                    .getAttribute(ClientContext.COOKIE_STORE);
                            if (cookies != null) {
                                String tmpcookies = "";
                                for (Cookie c : cookies.getCookies()) {
                                    TLog.log(TAG, "cookie:" + c.getName() + " " + c.getValue());
                                    tmpcookies += (c.getName() + "=" + c.getValue()) + ";";
                                }
                                TLog.log(TAG, "cookies:" + tmpcookies);
                                AppContext.instance().setProperty("cookie", tmpcookies);
                                ApiHttpClient.setCookie(ApiHttpClient.getCookie(AppContext.instance()));
                            }

                            User user  = jsonElementResponse.data;
                            Result res = jsonElementResponse.result;
                            if (res.OK()) {
                                // 保存登录信息
                                user.setUsername(mUserName);
                                user.setRememberMe(isRememberMe);
                                AppContext.instance().saveLoginInfo(user);

                                //记录是否曾经登录
                                mGlobalSettings = getSharedPreferences("setting", 0);
                                SharedPreferences.Editor editor = mGlobalSettings.edit();
                                editor.putString("hasLogged", "yes");
                                editor.commit();

                                handleLoginSuccess();
                            } else {
                                AppContext.instance().cleanLoginInfo();
                                System.out.println("Error Code:"+res.getErrorMessage());
                                AppContext.showToast(res.getErrorMessage());
                            }
                        }
                    });

            //UserApi.loginWithDeviceId(mUserName, mPassword, deviceId, mHandler);
        }else{
            Toast.makeText(this, R.string.no_device_id, Toast.LENGTH_LONG).show();
        }
    }

    //登录、验证成功显示主页面
    private void handleLoginSuccess(){
        AppContext.showToastShort(R.string.login_success_message);
        UIHelper.showMainActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        mBtnLogin.setOnClickListener(this);
        mBtnQuick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                handleLogin(true);
                break;
            case R.id.btn_register_quick:
                loginQuick();
                break;
        }
    }

    @Override
    public void onSizeChange(boolean paramBoolean, int w, int h) {
        if(paramBoolean){
            bottomLayout.setVisibility(View.GONE);
            scrollAreaLayout.setPadding(0, -130, 0, 0);
        } else {
            bottomLayout.setVisibility(View.VISIBLE);
            scrollAreaLayout.setPadding(0, 0, 0, 0);
        }
    }


    private void loginQuick(){
        //体验账号
        mUserName = "13800001111";
        mPassword = "pass";
        this.isRememberMe = false;

        AppContext.showToastShort(R.string.login_success_message);
        UIHelper.showMainActivity(this);
    }


}

































