package com.joint.turman.app.api.core;

import com.joint.turman.app.AppContext;
import com.joint.turman.app.api.ApiHttpClient;
import com.joint.turman.app.utils.TDevice;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * api - 基类
 * ============================================================================
 * 版权所有 2014 。
 *
 * @author fallenpanda
 *
 * @version 1.0 2014-12-04
 * ============================================================================
 */
public class BaseApi {

    public static final int DEF_PAGE_SIZE = TDevice.getPageSize();

    /**
     * post Result
     *
     * @param params 参数集合
     * @param url 访问地址
     * @param handler 返回处理
     */
    public static void postResult(String url, RequestParams params, AsyncHttpResponseHandler handler) {
        if(params == null)
            params = new RequestParams();

        if(!(AppContext.instance().isLogin() && AppContext.instance().hasToken())){
            if(!AppContext.instance().initLoginInfo()){
                return;
            }
        }

        url += "?u="+AppContext.instance().getToken().username+"&digest="+AppContext.instance().getToken().digest;

        ApiHttpClient.post(url, params, handler);
    }

    /**
     * get Result
     *
     * @param params 参数集合
     * @param url 访问地址
     * @param handler 返回处理
     */
    public static void getResult(String url, RequestParams params, AsyncHttpResponseHandler handler) {
        if(params == null)
            params = new RequestParams();

        if(!(AppContext.instance().isLogin() && AppContext.instance().hasToken())){
            if(!AppContext.instance().initLoginInfo()){
                return;
            }
        }

        url += "?u="+AppContext.instance().getToken().username+"&digest="+AppContext.instance().getToken().digest;

        ApiHttpClient.post(url, params, handler);
    }

}