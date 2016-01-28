package com.joint.turman.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by dqf on 2016/1/7.
 */
public class AppException extends Exception implements Thread.UncaughtExceptionHandler {

    private static final boolean Debug = false;
    public static final byte TYPE_NETWORK = 1;
    public static final byte TYPE_SOCKET = 2;
    public static final byte TYPE_HTTP_CODE = 3;
    public static final byte TYPE_HTTP_ERROR = 4;
    public static final byte TYPE_XML = 5;
    public static final byte TYPE_IO = 6;
    public static final byte TYPE_RUN = 7;
    public static final byte TYPE_JSON = 8;
    private byte type;
    private int code;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private AppException() {
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    private AppException(byte type, int code, Exception excp) {
        super(excp);
        this.type = type;
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public int getType() {
        return this.type;
    }

    public void makeToast(Context ctx) {
    }
    public void saveErrorLog(Exception excp) {
        String errorlog = "errorlog.txt";
        String savePath = "";
        String logFilePath = "";
        FileWriter fw = null;
        PrintWriter pw = null;

        try {
            String e = Environment.getExternalStorageState();
            File logFile;
            if(e.equals("mounted")) {
                savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/OSChina/Log/";
                logFile = new File(savePath);
                if(!logFile.exists()) {
                    logFile.mkdirs();
                }

                logFilePath = savePath + errorlog;
            }

            if(logFilePath == "") {
                return;
            }

            logFile = new File(logFilePath);
            if(!logFile.exists()) {
                logFile.createNewFile();
            }

            fw = new FileWriter(logFile, true);
            pw = new PrintWriter(fw);
            pw.println("--------------------" + (new Date()).toLocaleString() + "---------------------");
            excp.printStackTrace(pw);
            pw.close();
            fw.close();
        } catch (Exception var18) {
            var18.printStackTrace();
        } finally {
            if(pw != null) {
                pw.close();
            }

            if(fw != null) {
                try {
                    fw.close();
                } catch (IOException var17) {
                    ;
                }
            }

        }

    }

    public static AppException http(int code) {
        return new AppException((byte)3, code, (Exception)null);
    }

    public static AppException http(Exception e) {
        return new AppException((byte)4, 0, e);
    }

    public static AppException socket(Exception e) {
        return new AppException((byte)2, 0, e);
    }

    public static AppException io(Exception e) {
        return !(e instanceof UnknownHostException) && !(e instanceof ConnectException)?(e instanceof IOException?new AppException((byte)6, 0, e):run(e)):new AppException((byte)1, 0, e);
    }

    public static AppException xml(Exception e) {
        return new AppException((byte)5, 0, e);
    }

    public static AppException json(Exception e) {
        return new AppException((byte)8, 0, e);
    }

    public static AppException network(Exception e) {
        return !(e instanceof UnknownHostException) && !(e instanceof ConnectException)?(e instanceof SocketException ?socket(e):http(e)):new AppException((byte)1, 0, e);
    }

    public static AppException run(Exception e) {
        return new AppException((byte)7, 0, e);
    }

    public static AppException getAppExceptionHandler() {
        return new AppException();
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        if(!this.handleException(ex) && this.mDefaultHandler != null) {
            this.mDefaultHandler.uncaughtException(thread, ex);
        }

    }

    private boolean handleException(Throwable ex) {
        if(ex == null) {
            return false;
        } else {
            AppContext context = AppContext.instance();
            if(context == null) {
                return false;
            } else {
                this.getCrashReport(context, ex);
                (new Thread() {
                    public void run() {
                        Looper.prepare();
                        Looper.loop();
                    }
                }).start();
                return true;
            }
        }
    }

    private String getCrashReport(Context context, Throwable ex) {
        PackageInfo pinfo = ((AppContext)context.getApplicationContext()).getPackageInfo();
        StringBuffer exceptionStr = new StringBuffer();
        exceptionStr.append("Version: " + pinfo.versionName + "(" + pinfo.versionCode + ")\n");
        exceptionStr.append("Android: " + Build.VERSION.RELEASE + "(" + Build.MODEL + ")\n");
        exceptionStr.append("Exception: " + ex.getMessage() + "\n");
        StackTraceElement[] elements = ex.getStackTrace();

        for(int i = 0; i < elements.length; ++i) {
            exceptionStr.append(elements[i].toString() + "\n");
        }

        return exceptionStr.toString();
    }
}
