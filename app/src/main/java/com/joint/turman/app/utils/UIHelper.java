package com.joint.turman.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.joint.turman.app.activity.main.MainActivity;
import com.joint.turman.app.base.Constants;

/**
 * Created by dqf on 2016/1/7.
 */
public class UIHelper {
    private final static String TAG = "UIHelper";
    public final static String DELETE = "del";

    //---------------------------------------------------------------------
    //表单
    public final static int ACTION_FORM_OPERATE_NEW = 0x01;//新建
    public final static int ACTION_FORM_OPERATE_UPDATE= 0x02;//修改
    public final static int ACTION_FORM_OPERATE_DELETE = 0x03;//删除

    public final static int ACTION_FORM_CATALOG_CLIENT = 0x01;//客户
    public final static int ACTION_FORM_CATALOG_LINKMAN = 0x02;//联系人
    public final static int ACTION_FORM_CATALOG_PROBACK = 0x03;//反馈
    public final static int ACTION_FORM_CATALOG_PROLEAVE = 0x04;//请假
    public final static int ACTION_FORM_CATALOG_ANNOUNCE = 0x05;//公告
    public final static int ACTION_FORM_CATALOG_CONTRACT = 0x06;//成交
    public final static int ACTION_FORM_CATALOG_PROCEEDSPLAN = 0x07;//履约

    public final static String BUNDLE_KEY_FORM_WHAT = "BUNDLE_KEY_FORM_WHAT";
    public final static String BUNDLE_KEY_FORM_TYPE = "BUNDLE_KEY_FORM_TYPE";
    public final static String BUNDLE_KEY_FORM_KEYID = "BUNDLE_KEY_FORM_KEYID";
    public final static int LISTVIEW_DATATYPE_RESUME_TARGET = 0xF1;//履历 - 目标Id
    public final static int LISTVIEW_DATATYPE_USER = 0x0D;//用户一览

    //---------------------------------------------------------------------
    //列表
    public final static int LISTVIEW_OPERATE_NONE = 0x00;//无
    public final static int LISTVIEW_OPERATE_SHOW = 0x01;//详情
    public final static int LISTVIEW_OPERATE_RESULT = 0x02;//返回Result
    public final static int LISTVIEW_OPERATE_CHOOSE = 0x03;//选择item

    public final static int LISTVIEW_OPERATE_NEW_SIGNIN = 0x11;//新建签到
    public final static int LISTVIEW_OPERATE_NEW_PROBACK = 0x12; //新建项目反馈
    public final static int LISTVIEW_OPERATE_NEW_PROLEAVE = 0x13; //新建项目请假

    public final static int LISTVIEW_DATATYPE_CLIENT = 0x01; //客户一览
    public final static int LISTVIEW_DATATYPE_LINKMAN = 0x02; //联系人一览
    public final static int LISTVIEW_DATATYPE_PROINFO = 0x03; //项目一览
    public final static int LISTVIEW_DATATYPE_SIGNIN = 0x04; //考勤一览
    public final static int LISTVIEW_DATATYPE_PROBACK = 0x05; //反馈一览
    public final static int LISTVIEW_DATATYPE_COMMENT = 0x06; //批注一览
    public final static int LISTVIEW_DATATYPE_ANNOUNCE = 0x07; //公告一览
    public final static int LISTVIEW_DATATYPE_KNOWLEDGE = 0x08; //知识库一览

    public final static int LISTVIEW_DATATYPE_PROINFO_SPECIAL = 0x09; //某一项目下的信息
    public final static int LISTVIEW_DATATYPE_CALENDER_BYTIME = 0x110;//日历事件（时间筛选）
    public final static int LISTVIEW_DATATYPE_DYNMSG = 11; //我的交流（首页）
    public final static int LISTVIEW_DATATYPE_ANNOUNCEMENT = 12; //我的公告（首页）
    public final static int LISTVIEW_DATATYPE_NOTICE = 13;//我的通知（首页）
    public final static int LISTVIEW_DATATYPE_PROINFO_RELATED = 14; //用户相关项目（项目总监，项目组成员，新建人）

    public final static int LISTVIEW_DATATYPE_PROINFO_NOTFINISH = 1; //未完成项目（项目一览）实施中项目
    public final static int LISTVIEW_DATATYPE_PROINFO_FINISHED = 2; //已完成项目（项目一览）




    public static void showMainActivity(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * 发送广播-注销用户
     * @param context 上下文
     */
    public static void sendBroadLogoutApp(Context context) {
        Intent intent = new Intent(Constants.INTENT_ACTION_LOGOUT);
        context.sendBroadcast(intent);
    }
}
