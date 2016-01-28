package com.joint.turman.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.joint.turman.app.api.ApiHttpClient;
import com.joint.turman.app.base.BaseApplication;
import com.joint.turman.app.base.Constants;
import com.joint.turman.app.bean.Token;
import com.joint.turman.app.model.entity.User;
import com.joint.turman.app.utils.CyptoUtils;
import com.joint.turman.app.utils.HmacSHA256Utils;
import com.joint.turman.app.utils.StringUtils;
import com.joint.turman.app.utils.UIHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by dqf on 2016/1/7.
 */
public class AppContext extends BaseApplication {
    private static final String TAG = AppContext.class.getSimpleName();

    public static final boolean DEBUGMODE = true;

    private static final String KEY_SOFTKEYBOARD_HEIGHT = "KEY_SOFTKEYBOARD_HEIGHT";

    private static final String CANARO_EXTRA_BOLD_PATH = "fonts/canaro_extra_bold.otf";
    public static Typeface canaroExtraBold;

    private static Gson _gson;

    private boolean login = false; // 登录状态
    private String loginUid = ""; // 登录用户Id
    private Token token = null; // 登录用户身份凭证

    private Hashtable<String, Object> memCacheRegion = new Hashtable<String, Object>();

    public synchronized static AppContext instance() {
        return (AppContext) _context;
    }

    public static Gson gson() {
        return _gson;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        _gson = new Gson();
        baseInit();
        initTypeface();
    }

    private void baseInit() {
        //HttpClient初始化
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        client.setCookieStore(myCookieStore);
        ApiHttpClient.setHttpClient(client);
        ApiHttpClient.setCookie(ApiHttpClient.getCookie(this));

        Logger.init().logLevel(DEBUGMODE ? LogLevel.FULL : LogLevel.NONE);


    }

    public static String getDeviceInfo(Context context) {
        try{
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if(TextUtils.isEmpty(device_id) ){
                device_id = mac;
            }

            if( TextUtils.isEmpty(device_id) ){
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void setSoftKeyboardHeight(int height) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(KEY_SOFTKEYBOARD_HEIGHT, height);
        apply(editor);
    }

    public static int getSoftKeyboardHeight() {
        return getPreferences().getInt(KEY_SOFTKEYBOARD_HEIGHT, 0);
    }

    /**
     * 用户是否登录
     *
     * @return
     */
    public boolean isLogin() {
        return login;
    }

    /**
     * 获取登录用户 id
     *
     * @return
     */
    public String getLoginUid() {
        return loginUid;
    }

    /**
     * 是否存在 token
     *
     * @return
     */
    public boolean hasToken() {
        return token != null && StringUtils.isNotEmpty(token.username) && StringUtils.isNotEmpty(token.digest);
    }

    /**
     * 获取登录用户 token
     *
     * @return
     */
    public Token getToken() {
        return hasToken() ? token : new Token();
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    /**
     * 获取App唯一标识
     *
     * @return
     */
    public String getAppId() {
        String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
        if (StringUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
        }
        return uniqueID;
    }

    /**
     * 是否启动检查更新
     *
     * @return
     */
    public boolean isCheckUp() {
        String perf_checkup = getProperty(AppConfig.CONF_CHECKUP);
        // 默认是开启
        if (StringUtils.isEmpty(perf_checkup))
            return true;
        else
            return StringUtils.toBool(perf_checkup);
    }

    /**
     * 设置启动检查更新
     *
     * @param b
     */
    public void setConfigCheckUp(boolean b) {
        setProperty(AppConfig.CONF_CHECKUP, String.valueOf(b));
    }

    public boolean containsProperty(String key) {
        Properties props = getProperties();
        return props.containsKey(key);
    }

    public void setProperties(Properties ps) {
        AppConfig.getAppConfig(this).set(ps);
    }

    public Properties getProperties() {
        return AppConfig.getAppConfig(this).get();
    }

    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    public String getProperty(String key) {
        return AppConfig.getAppConfig(this).get(key);
    }

    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }

    public void saveLoginInfo(final User user) {
        setMemCache(AppConfig.TEMP_USER_MY, user);//缓存用户信息
        //未登录||uid为空||uid与userId不相等
        if(!login||StringUtils.isEmpty(loginUid)||(StringUtils.isNotEmpty(loginUid)&&!loginUid.equals(user.getId()))){
            //setJPushAlias(user.getId());//设置推送参数
        }

        //加密
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put(Constants.PARAM_UID, user.getAid());
        params.put(Constants.PARAM_USERNAME, user.getUsername());
        final String digest = HmacSHA256Utils.digest(user.getSalt(), params);

        this.loginUid = user.getId();
        this.login = true;
        this.token = new Token(user.getUsername(), user.getDigest());

        setProperties(new Properties() {
            {
                setProperty("user.uid", CyptoUtils.encode(Constants.APP_KEYWORDS, user.getId()));
                setProperty("user.name", user.getName());
                setProperty("user.companyName", StringUtils.isNotEmpty(user.getCompanyName())?user.getCompanyName():"");
                setProperty("user.companySubName", StringUtils.isNotEmpty(user.getCompanySubName())?user.getCompanySubName():"");
                setProperty("user.face", StringUtils.isNotEmpty(user.getPortrait()) ? user.getPortrait() : "");// 用户头像
                setProperty("user.username", user.getUsername());
                setProperty("user.digest", digest);
                setProperty("user.isRememberMe", String.valueOf(user.isRememberMe()));// 是否记住我的信息
            }
        });
    }

    /**
     * 清除登录信息
     */
    public void cleanLoginInfo() {
        if(hasMemCache(AppConfig.TEMP_USER_MY)){//清除缓存对象
            removeMemCache(AppConfig.TEMP_USER_MY);
        }

        this.loginUid = "";
        this.login = false;
        removeProperty("user.uid", "user.name", "user.companyName", "user.companySubName", "user.face", "user.username",
                "user.digest", "user.isRememberMe");
    }

    /**
     * 判断内存缓存中是否存在对象
     * @param key
     * @return
     */
    public boolean hasMemCache(String key){
        return memCacheRegion.containsKey(key);
    }

    /**
     * 清除内存缓存中是的对象
     * @param key
     * @return
     */
    public void removeMemCache(String key){
        memCacheRegion.remove(key);
    }

    /**
     * 将对象保存到内存缓存中
     * @param key
     * @param value
     */
    public void setMemCache(String key, Object value) {
        memCacheRegion.put(key, value);
    }

    /**
     * 从内存缓存中获取对象
     * @param key
     * @return
     */
    public Object getMemCache(String key){
        return memCacheRegion.get(key);
    }

    /**
     * 清除保存的缓存
     */
    public void cleanCookie() {
        removeProperty(AppConfig.CONF_COOKIE);
    }

    /**
     * 用户注销
     */
    public void Logout() {
        ApiHttpClient.cleanCookie();
        this.cleanCookie();
        this.login = false;
        this.loginUid = "";
        this.token = null;

        setProperty("user.digest", "");//清除密码
        setProperty("user.isRememberMe", String.valueOf(false));//不再自动登录

        //clearJPushAlias();//清除JPush别名

        if(hasMemCache(AppConfig.TEMP_USER_MY))//清除缓存中用户信息对象
            removeMemCache(AppConfig.TEMP_USER_MY);

        UIHelper.sendBroadLogoutApp(this);
    }

    /**
     * 获取登录信息
     *
     * @return
     */
    public User getLoginInfo() {
        User lu = new User();
        lu.setId(CyptoUtils.decode(Constants.APP_KEYWORDS, getProperty("user.uid")));
        lu.setName(getProperty("user.name"));
        lu.setCompanyName(getProperty("user.companyName"));
        lu.setCompanySubName(getProperty("user.companySubName"));
        lu.setPortrait(getProperty("user.face"));
        lu.setUsername(getProperty("user.username"));
        lu.setDigest(getProperty("user.digest"));
        lu.setRememberMe(StringUtils.toBool(getProperty("user.isRememberMe")));
        return lu;
    }

    /**
     * 初始化用户登录信息
     */
    public boolean initLoginInfo() {
        User loginUser = getLoginInfo();
        if (loginUser != null && StringUtils.isNotEmpty(loginUser.getId()) && StringUtils.isNotEmpty(loginUser.getUsername()) && StringUtils.isNotEmpty(loginUser.getDigest())) {
            setMemCache(AppConfig.TEMP_USER_MY, loginUser);//缓存用户信息
            this.login = true;
            this.loginUid = loginUser.getId();
            this.token = new Token(loginUser.getUsername(), loginUser.getDigest());
            return true;
        } else {
            Logout();
            return false;
        }
    }

    //初始化字体
    private void initTypeface() {
        canaroExtraBold = Typeface.createFromAsset(getAssets(), CANARO_EXTRA_BOLD_PATH);
    }

}






















