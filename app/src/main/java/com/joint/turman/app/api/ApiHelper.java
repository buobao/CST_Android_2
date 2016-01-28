package com.joint.turman.app.api;

import com.joint.turman.app.AppContext;
import com.joint.turman.app.base.Constants;

/**
 * Api 工具类
 * ============================================================================
 * 版权所有 2015
 *
 * @author fallenpanda
 *
 * @version 1.0 2015-12-1
 * ============================================================================
 */
public class ApiHelper {

    /**
     * 请求头Agent
     *
     * @param appContext 上下文
     * @return String
     */
	public static String getUserAgent(AppContext appContext) {
		StringBuilder ua = new StringBuilder(Constants.APP_KEYWORDS);//产品
		ua.append('/' + appContext.getPackageInfo().versionName+'_'+appContext.getPackageInfo().versionCode);//App版本
		ua.append("/Android");// 手机系统平台
		ua.append("/" + android.os.Build.VERSION.RELEASE);// 手机系统版本
		ua.append("/" + android.os.Build.MODEL); // 手机型号
		ua.append("/" + appContext.getAppId());// 客户端唯一标识
		return ua.toString();
	}

}
