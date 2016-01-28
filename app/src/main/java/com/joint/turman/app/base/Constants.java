package com.joint.turman.app.base;

import android.os.Environment;

/**
 * Created by dqf on 2016/1/7.
 */
public class Constants {

    public final static String APP_KEYWORDS = "CSTAndroidApplication";

    public static final String PARAM_USERNAME = "u";
    public static final String PARAM_UID = "uid";

    public static final String INTENT_ACTION_EXIT = "com.joint.turman.app.INTENT_ACTION_EXIT";
    public static final String INTENT_ACTION_LOGOUT = "com.joint.turman.app.LOGOUT";

    public static final String INTENT_ACTION_COMMENT_CHANGED = "com.joint.turman.app.COMMENT_CHANGED";
    public static final String INTENT_ACTION_NOTICE = "com.joint.turman.app.action.APPWIDGET_UPDATE";

    public final static String INTENT_ACTION_FORM_COMMENT = "INTENT_ACTION_FORM_COMMENT";//批注

    public final static String INTENT_ACTION_FORM_USER = "com.joint.turman.app.FORM_USER";//用户

    public final static String INTENT_ACTION_FORM_CLIENT = "com.joint.turman.app.FORM_CLIENT";//客户
    public final static String INTENT_ACTION_FORM_LINKMAN = "com.joint.turman.app.FORM_LINKMAN";//联系人
    public final static String INTENT_ACTION_FORM_PROBACK = "com.joint.turman.app.FORM_PROBACK";//项目反馈
    public final static String INTENT_ACTION_FORM_PROLEAVE = "com.joint.turman.app.FORM_PROLEAVE";//项目请假
    public final static String INTENT_ACTION_FORM_PROSIGNIN = "com.joint.turman.app.FORM_PROSIGNIN";//项目签到
    public final static String INTENT_ACTION_FORM_ANNOUNCE = "com.joint.turman.app.FORM_PROSIGNIN";//公告

    public final static String INTENT_ACTION_MARKREAD = "com.joint.turman.app.MARK_READ";//标记已读

    public final static String BASE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Smartfanzai.cst/";

    public final static String IMAGE_SAVE_PAHT = BASE_DIR +"download_images";

    public static final String WEICHAT_APPID = "wx7aac2075450f71a2";
    public static final String WEICHAT_SECRET = "0101b0595ffe2042c214420fac358abc";

    public static final String QQ_APPID = "100424468";
    public static final String QQ_APPKEY = "c7394704798a158208a74ab60104f0ba";

}
