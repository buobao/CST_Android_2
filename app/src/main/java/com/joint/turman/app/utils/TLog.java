package com.joint.turman.app.utils;

/**
 * Created by dqf on 2016/1/7.
 */
import android.util.Log;

public class TLog {
    public static final String LOG_TAG = "SIMICO";
    public static boolean DEBUG = true;

    public TLog() {
    }

    public static final void analytics(String log) {
        if(DEBUG) {
            Log.d("SIMICO", log);
        }

    }

    public static final void error(String log) {
        if(DEBUG) {
            Log.e("SIMICO", "" + log);
        }

    }

    public static final void log(String log) {
        if(DEBUG) {
            Log.i("SIMICO", log);
        }

    }

    public static final void log(String tag, String log) {
        if(DEBUG) {
            Log.i(tag, log);
        }

    }

    public static final void logv(String log) {
        if(DEBUG) {
            Log.v("SIMICO", log);
        }

    }

    public static final void warn(String log) {
        if(DEBUG) {
            Log.w("SIMICO", log);
        }

    }
}
