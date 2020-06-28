package com.douzi.dd.utils;

import android.util.Log;

/**
 * 日志工具类, 有以下功能: (1)通过SHOW_LOG全局控制是否打印日志 (2)通过设置TAG设置默认的log tag (3)默认打印格式为： [类名]
 * -> 方法名 : 日志内容 (4)使用xxOld打印没有格式的log (5)使用Logger.Buider可选打印类名\方法名\代码行数\线程名
 * (6)initTime和printTime进行耗时计算
 *
 * @author zhuyuanshuo
 * @date 2015-5-5 上午11:08:14
 */
public class Logger {

    public static String TAG = "DouziLog";

    public static  boolean SHOW_LOG = true;

    private static final int MAX_LOG_ROW_CHAR_SIZE = 1000;

    private static long lastTime;

    // [+] 新增风格

    public static void d(String msg) {
        defaultLog(TAG, Log.DEBUG, msg);
    }

    public static void i(String msg) {
        defaultLog(TAG, Log.INFO, msg);
    }

    public static void v(String msg) {
        defaultLog(TAG, Log.VERBOSE, msg);
    }

    public static void w(String msg) {
        defaultLog(TAG, Log.WARN, msg);
    }

    public static void e(String msg) {
        defaultLog(TAG, Log.ERROR, msg);
    }

    public static void wtf(String msg) {
        defaultLog(TAG, Log.ASSERT, msg);
    }

    public static void d(String tag, String msg) {
        defaultLog(tag, Log.DEBUG, msg);
    }

    public static void i(String tag, String msg) {
        defaultLog(tag, Log.INFO, msg);
    }

    public static void v(String tag, String msg) {
        defaultLog(tag, Log.VERBOSE, msg);
    }

    public static void w(String tag, String msg) {
        defaultLog(tag, Log.WARN, msg);
    }

    public static void e(String tag, String msg) {
        defaultLog(tag, Log.ERROR, msg);
    }

    public static void wtf(String tag, String msg) {
        defaultLog(tag, Log.ASSERT, msg);
    }

    // [-] 新增风格
    // [+] 兼容原有的风格
    public static void dOld(String msg) {
        if (!SHOW_LOG) {
            return;
        }
        Log.d(TAG, msg);
    }

    public static void iOld(String msg) {
        if (!SHOW_LOG) {
            return;
        }
        Log.i(TAG, msg);
    }

    public static void vOld(String msg) {
        if (!SHOW_LOG) {
            return;
        }
        Log.v(TAG, msg);
    }

    public static void wOld(String msg) {
        if (!SHOW_LOG) {
            return;
        }
        Log.w(TAG, msg);
    }

    public static void eOld(String msg) {
        if (!SHOW_LOG) {
            return;
        }
        Log.e(TAG, msg);
    }

    public static void wtfOld(String msg) {
        if (!SHOW_LOG) {
            return;
        }
        Log.wtf(TAG, msg);
    }

    public static void dOld(String tag, String msg) {
        if (!SHOW_LOG) {
            return;
        }
        Log.d(tag, msg);
    }

    public static void iOld(String tag, String msg) {
        if (!SHOW_LOG) {
            return;
        }
        Log.i(tag, msg);
    }

    public static void vOld(String tag, String msg) {
        if (!SHOW_LOG) {
            return;
        }
        Log.v(tag, msg);
    }

    public static void wOld(String tag, String msg) {
        if (!SHOW_LOG) {
            return;
        }
        Log.w(tag, msg);
    }

    public static void eOld(String tag, String msg) {
        if (!SHOW_LOG) {
            return;
        }
        Log.e(tag, msg);
    }

    public static void wtfOld(String tag, String msg) {
        if (!SHOW_LOG) {
            return;
        }
        Log.wtf(tag, msg);
    }

    // [-] 兼容原有的风格
    // [+] 计时
    public static void initTime() {
//        if (!SHOW_LOG) {
//            return;
//        }
        lastTime = System.currentTimeMillis();
    }

    public static void printTime(String description) {
//        if (!SHOW_LOG) {
//            return;
//        }
        long currentTime = System.currentTimeMillis();
        Log.i(TAG, description + "'s time: " + (currentTime - lastTime) + "ms");
        lastTime = currentTime;
    }

    // [-] 计时

    /**
     * 默认的Log, 显示类名和方法名
     *
     * @param tag
     * @param logType
     * @param content
     */
    private static void defaultLog(String tag, int logType, String content) {
        log(tag, logType, content, true, false, true, false);
    }

    /**
     * 显示log信息的全部参数设置方法
     *
     * @param tag
     * @param logType
     * @param content
     * @param showClz
     * @param showLineNumber 在showClz = true时才有用
     * @param showMethod
     * @param showThreadName
     */
    static void log(String tag, int logType, String content, boolean showClz, boolean showLineNumber,
                    boolean showMethod, boolean showThreadName) {
        if (!SHOW_LOG) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (showClz) {
            String clzFullName = Thread.currentThread().getStackTrace()[5].getClassName();
            int lastPointerIndex = clzFullName.lastIndexOf('.');
            String clzSimpleName = lastPointerIndex == -1 ? clzFullName : clzFullName.substring(lastPointerIndex + 1);
            sb.append('[');
            sb.append(clzSimpleName);
            if (showLineNumber) {
                sb.append(':');
                sb.append(Thread.currentThread().getStackTrace()[5].getLineNumber());
            }
            sb.append(']');
        }

        if (showMethod) {
            sb.append(" -> ");
            sb.append(Thread.currentThread().getStackTrace()[5].getMethodName());
        }

        if (showThreadName) {
            if (showClz || showMethod || showLineNumber) {
                sb.append(',');
            }
            sb.append(" thread -> ");
            sb.append(Thread.currentThread().getName());
        }

        if (showClz || showMethod || showThreadName) {
            sb.append(" : ");
        }

        sb.append(content);

        String logContent = sb.toString();

        switch (logType) {
            case Log.ERROR:
                Log.e(tag, logContent);
                break;
            case Log.INFO:
                Log.i(tag, logContent);
                break;
            case Log.VERBOSE:
                Log.v(tag, logContent);
                break;
            case Log.WARN:
                Log.w(tag, logContent);
                break;
            case Log.ASSERT:
                Log.wtf(tag, logContent);
                break;
            case Log.DEBUG:
                // Fall through, log debug by default
            default:
                printLongLog(tag, logContent);
                break;
        }
    }

    /**
     * 打印超过1000个字符的log
     *
     * @param tag
     * @param msg
     */
    private static void printLongLog(String tag, String msg) {
        try {
            for (int i = 0; i <= msg.length() / MAX_LOG_ROW_CHAR_SIZE; i++) {
                int start = i * MAX_LOG_ROW_CHAR_SIZE;
                int end = (i + 1) * MAX_LOG_ROW_CHAR_SIZE;
                end = end > msg.length() ? msg.length() : end;
                Log.d(tag, msg.substring(start, end));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static class Builder {
        private boolean showClz;
        private boolean showLineNumber;
        private boolean showMethod;
        private boolean showThreadName;
        private String content;
        private String tag = TAG;

        public Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder withClz() {
            showClz = true;
            return this;
        }

        public Builder withLineNumber() {
            showLineNumber = true;
            return this;
        }

        public Builder withMethod() {
            showMethod = true;
            return this;
        }

        public Builder withThread() {
            showThreadName = true;
            return this;
        }

        public Builder content(String msg) {
            content = msg;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public void d() {
            log(Log.DEBUG);
        }

        public void v() {
            log(Log.VERBOSE);
        }

        public void i() {
            log(Log.INFO);
        }

        public void w() {
            log(Log.WARN);
        }

        public void e() {
            log(Log.ERROR);
        }

        public void wtf() {
            log(Log.ASSERT);
        }

        private void log(int logType) {
            Logger.log(tag, logType, content, showClz, showLineNumber, showMethod, showThreadName);
        }
    }

    private Logger() {
    }
}
