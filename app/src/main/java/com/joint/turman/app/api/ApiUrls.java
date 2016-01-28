package com.joint.turman.app.api;

import java.io.Serializable;

/**
 * Created by dqf on 2016/1/8.
 */
public class ApiUrls implements Serializable {
    private static final long serialVersionUID = -1453773382865080478L;

    public final static String HTTP = "http://";
    public final static String HTTPS = "https://";

    private final static String URL_SPLITTER = "/";
    private final static String URL_UNDERLINE = "_";

    private final static String PROJECT = "ec-web";

    public final static String HOST = "cst.9joint-eco.com";

    private final static String NAMESPACE_APP = "app";
    private final static String NAMESPACE_COM = "com";

    //http://cst.9joint-eco.com/
    public final static String URL_API_HOST = HTTP + HOST + URL_SPLITTER;
    //http://cst.9joint-eco.com/ec-web/app/
    public final static String URL_APP_API_HOST = HTTP + HOST + URL_SPLITTER + PROJECT + URL_SPLITTER + NAMESPACE_APP + URL_SPLITTER;
    //    public final static String URL_APP_API_WEBHOST = HTTP + WEB_HOST + URL_SPLITTER + PROJECTNAME + URL_SPLITTER + NAMESPACE_APP + URL_SPLITTER;
    //http://cst.9joint-eco.com/ec-web/com/
    public final static String URL_COM_API_HOST = HTTP + HOST + URL_SPLITTER + PROJECT + URL_SPLITTER + NAMESPACE_COM + URL_SPLITTER;
    //http://cst.9joint-eco.com/ec-web/app/%s
    public final static String DEV_API_URL = URL_APP_API_HOST + "%s";

    //用户
    public final static String LOGIN_VALIDATE_HTTP = "/login.action";//登陆
    public final static String LOGIN_AUTHC_HTTP = "/account!authc.action";//自动登陆
    public final static String USER_REGISTER = "account!register.action";//注册
    public final static String USER_ACTION = "users!getUserAction.action";//用户权限
    //用户信息
    public final static String MY_INFO = "users!getUserInfo.action";
    //用户列表
    public final static String USER_LIST = "users!getList.action";
}
