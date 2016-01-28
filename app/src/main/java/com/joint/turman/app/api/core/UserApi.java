package com.joint.turman.app.api.core;

import com.joint.turman.app.api.ApiHttpClient;
import com.joint.turman.app.model.URLs;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by dqf on 2016/1/7.
 */
public class UserApi extends BaseApi {

    /**
     * 注册
     *
     * @param params 参数
     * @param handler 返回处理
     */
    public static void register(RequestParams params, AsyncHttpResponseHandler handler) {

        String url = URLs.USER_REGISTER;
        ApiHttpClient.post(url, params, handler);
    }

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     * @param handler 返回处理
     */
    public static void login(String username, String password, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();

        params.put("username", username);
        params.put("password", password);

        String url = URLs.LOGIN_VALIDATE_HTTP;

        ApiHttpClient.post(url, params, handler);
    }

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     * @param deviceId 手机的设备号
     * @param handler 返回处理
     */
    public static void loginWithDeviceId(String username, String password, String deviceId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();

        params.put("username", username);
        params.put("password", password);
        params.put("deviceId", deviceId);

        String url = URLs.LOGIN_VALIDATE_HTTP;

        ApiHttpClient.post(url, params, handler);
    }

    /**
     * 自动登陆
     *
     * @param username
     * @param digest
     * @param handler 返回处理
     */
    public static void authc(String username, String digest, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();

        String url = URLs.LOGIN_AUTHC_HTTP;
        url += "?u="+username+"&digest="+digest;

        ApiHttpClient.post(url, params, handler);
    }

    /**
     * 获取我的个人信息
     *
     * @param handler
     */
    public static void getMyInfo(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();

        String url = URLs.MY_INFO;

        getResult(url, params, handler);
    }

    /**
     * 获取用户权限
     *
     * @param handler
     */
    public static void getUserAction(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();

        String url = URLs.USER_ACTION;

        getResult(url, params, handler);
    }

    /**
     * 获取用户部门
     *
     * @param handler
     */
    public static void getUserDepartmentList(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();

        String url = URLs.USER_DEPARTMENT_LIST;

        getResult(url, params, handler);
    }

    /**
     * 获取部门用户
     *
     * @param departmentId
     * @param handler
     */
    public static void getDepartmentUserList(String departmentId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();

        params.put("keyId", departmentId);

        String url = URLs.DEPARTMENT_USER_LIST;

        getResult(url, params, handler);
    }

    /**
     * 获取用户列表
     *
     * @param catalog
     * @param pageIndex
     * @param property
     * @param keyword
     * @param handler
     */
    public static void getUserList(int catalog, int pageIndex, String keyId, String property, String keyword, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();

        params.put("catalog", catalog);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", DEF_PAGE_SIZE);
        params.put("keyId", keyId);
        params.put("pageProperty", property);
        params.put("pageKeyword", keyword);

        String url = URLs.USER_LIST;

        getResult(url, params, handler);
    }

    /**
     * 修改用户头像
     *
     * @param filePath
     * @param handler
     */
    public static void updateUserHeader(String filePath, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();

        try {
            params.put("portrait", new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String url = URLs.MY_INFO_HEADER_UPDATE;

        getResult(url, params, handler);
    }

    /**
     * 修改用户名称
     *
     * @param name
     * @param handler
     */
    public static void updateUserName(String name, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();

        params.put("name", name);

        String url = URLs.MY_INFO_NAME_UPDATE;

        getResult(url, params, handler);
    }

    /**
     * 修改用户性别
     *
     * @param sex
     * @param handler
     */
    public static void updateUserSex(String sex, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();

        params.put("sex", sex);

        String url = URLs.MY_INFO_SEX_UPDATE;

        getResult(url, params, handler);
    }

    /**
     * 修改绑定手机
     *
     * @param params 参数
     * @param handler 返回处理
     */
    public static void updateUserPhone(RequestParams params, AsyncHttpResponseHandler handler) {

        String url = URLs.MY_INFO_MOBILE_BINDING;

        postResult(url, params, handler);
    }

    /**
     * 删除体验账号
     *
     * @param handler
     */
    public static void deleteAccount(String password, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();

        params.put("PassWord", password);

        String url = URLs.MY_INFO_ACCOUNT_DELETE;

        postResult(url, params, handler);
    }

    /**
     * 核对企业编号
     *
     * @param params 参数
     * @param handler 返回处理
     */
    public static void accountInCompanyNo(RequestParams params, AsyncHttpResponseHandler handler) {

        String url = URLs.MY_INFO_ACCOUNT_IN_CHECK;

        postResult(url, params, handler);
    }

    /**
     * 用户账号转移
     *
     * @param params 参数
     * @param handler 返回处理
     */
    public static void accountIn(RequestParams params, AsyncHttpResponseHandler handler) {

        String url = URLs.MY_INFO_ACCOUNT_IN;

        postResult(url, params, handler);
    }

    /**
     * 修改用户密码
     *
     * @param pwd_old
     * @param pwd_new
     */
    public static void updateUserPassword(String pwd_old, String pwd_new, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();

        params.put("OldPassWord", pwd_old);
        params.put("PassWord", pwd_new);

        String url = URLs.MY_INFO_PASSWORD_UPDATE;

        postResult(url, params, handler);
    }

}
