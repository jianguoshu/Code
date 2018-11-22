package com.douzi.dd.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class DeviceUtil {

    private static final String TAG = "DeviceUtil";

    // 当前屏幕的密度因子
    private static float screenScale = 0.0f;

    private static int screenDPI = 0;

    private static float screenScaleDensity = 0.0f;

    // 当前屏幕宽度【单位：像素】
    private static float screenWidthInPixels;

    // 当前屏幕高度【单位：像素】
    private static float screenHeightPixels;

    // 当前屏幕宽度【单位：dp/dip】
    private static float screenWidthInDip;

    // 当前屏幕高度【单位：dp/dip】
    private static float screenHeightDip;

    public static final int HDPI = 240;

    private static boolean isMeizuM9 = false;
    private static boolean isXT800 = false;
    private static boolean isS5360 = false;
    private static boolean isU2 = false;

    // surfaceView在dialog中detach会crash的问题
    private static boolean isHtcHero = false;

    private static boolean isZten880e = false;
    private static boolean isNexus6p = false;

    static {

        String model = Build.MODEL.trim().toLowerCase();

        if (model.equals("m9"))
            isMeizuM9 = true;
        else if (model.equals("htc hero"))
            isHtcHero = true;
        else if (model.contains("xt800"))
            isXT800 = true;
        else if (model.contains("s5360"))
            isS5360 = true;
        else if (model.equals("u2"))
            isU2 = true;
        else if (model.contains("zte n880e")) {
            isZten880e = true;
        } else if (model.contains("nexus 6p")) {
            isNexus6p = true;
        }
    }

    /**
     * 根据构造函数获得当前手机的屏幕系数
     */
    public static void init(Context mContext) {
        // 获取当前屏幕
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();

        Context localContext = mContext.getApplicationContext();
        WindowManager windowManager = (WindowManager) localContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);

        screenDPI = mDisplayMetrics.densityDpi;

        // 密度因子
        screenScale = ((float) mDisplayMetrics.densityDpi) / 160;
        screenScaleDensity = mDisplayMetrics.scaledDensity;

        screenWidthInPixels = mDisplayMetrics.widthPixels;
        screenHeightPixels = mDisplayMetrics.heightPixels;

        screenWidthInDip = DeviceUtil.px2dip(screenWidthInPixels);
        screenHeightDip = DeviceUtil.px2dip(screenHeightPixels);

        // 有时候会发现获取的宽高是颠倒的。目前app只支持竖屏，所以这里永远认为短的是width，长的是height
        if (screenWidthInPixels > screenHeightPixels) {
            // 认为获取的宽度异常
            float temp = screenHeightPixels;
            screenHeightPixels = screenWidthInPixels;
            screenWidthInPixels = temp;
        }
    }

    /**
     * 获取当前屏幕密度因子
     *
     * @return
     */
    public static float getScreenScale() {
        return screenScale;
    }

    /**
     * 通过反射机制，获取status bar高度
     *
     * @param mContext
     * @return
     */
    public static int getStatusBarHeight(Context mContext) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());

            int statusBarHeight = mContext.getResources().getDimensionPixelSize(x);

            return statusBarHeight;
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return 0;
    }

    /**
     * 沉浸式状态栏中设置StatusBar高度，API大于M时返回StatusBar高度，否则返回0
     *
     * @param mContext
     * @return
     */
    public static int getImmarsionBarHeight(Context mContext) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getStatusBarHeight(mContext);
        } else {
            return 0;
        }
    }

    /**
     * 显示手机顶部状态栏
     *
     * @param activity
     */
    public static void showStatusBar(Activity activity) {
        if (activity == null) {
            return;
        }

        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().setAttributes(params);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 隐藏手机状态栏
     *
     * @param activity
     */
    public static void hideStatusBar(Activity activity) {
        if (activity == null) {
            return;
        }

        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(params);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 修改window背景色
     *
     * @param activity
     * @param colorResId
     */
    public static void changeWindowBackground(Activity activity, int colorResId) {
        if (activity == null) {
            return;
        }

        int colorInt = activity.getResources().getColor(colorResId);
        ColorDrawable colorDrawable = new ColorDrawable(colorInt);
        activity.getWindow().setBackgroundDrawable(colorDrawable);
    }

    public static void changeViewBackground(Activity activity, View view, int colorResId) {
        if (activity == null) {
            return;
        }

        int colorInt = activity.getResources().getColor(colorResId);
        view.setBackgroundColor(colorInt);
    }

    /**
     * 密度转换像素
     */
    public static int dip2px(float dipValue) {
        return (int) (dipValue * screenScale + 0.5f);
    }

    /**
     * 像素转换密度
     */
    public static int px2dip(float pxValue) {
        return (int) (pxValue / screenScale + 0.5f);
    }

    public static int sp2px(int spValue) {
        return (int) (spValue * screenScaleDensity + 0.5f);
    }

    public static int px2sp(int spValue) {
        return (int) (spValue / screenScaleDensity + 0.5f);
    }

    /**
     * 判断是否是三星手机
     *
     * @return
     */
    public static boolean isSamsung() {
        String brand = Build.BRAND.trim();
        // SM和SCL开头的可能是韩版或日版三星手机，从友盟上的bug机型上推测的
        return brand.equalsIgnoreCase("samsung") || brand.indexOf("SM") == 0 || brand.indexOf("SCL") == 0;
    }


    /**
     * getMhz:获取CPU的主频
     *
     * @return
     */

    public static int getCPUMhz() {
        int result = 0;
        BufferedReader br = null;

        try {
            Process process = Runtime.getRuntime().exec(
                    "/system/bin/cat /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");

            InputStream in = process.getInputStream();
            br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = br.readLine()) != null) {
                result = Integer.parseInt(line.trim()) / 1000;
                break;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    /**
     * 是否ARMV7
     *
     * @return
     */
    public static boolean isArmV7() {
        boolean result = false;
        BufferedReader br = null;
        try {
            Process process = Runtime.getRuntime().exec("/system/bin/cat /proc/cpuinfo");
            InputStream in = process.getInputStream();
            br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = br.readLine()) != null) {
                line = line.toLowerCase();
                if (-1 != line.indexOf("armv7")) {
                    result = true;
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    /**
     * 获取当前屏幕密度
     * /**
     * 获取屏幕DPI
     *
     * @return
     */
    public static int getScreenDpi() {
        return screenDPI;
    }

    /**
     * 获取设备名称
     *
     * @return
     */
    public static String getDeviceName() {
        return " " + Build.MODEL.replaceAll("[ |\\/|\\_|\\&|\\|]", "") + " ";
    }

    /**
     * 获取是否root
     *
     * @return
     */
    public static boolean getIsRootByFile() {
        File f = null;
        final String kSuSearchPaths[] = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        try {
            for (int i = 0; i < kSuSearchPaths.length; i++) {
                f = new File(kSuSearchPaths[i] + "su");
                if (f != null && f.exists()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取ROM总容量.
     */
    public static float getROMMemery() {
        StatFs statfs = new StatFs(Environment.getDataDirectory().getPath());
        long AvailableBlockCount = statfs.getAvailableBlocks();
        float avROMsize = statfs.getBlockSize() * AvailableBlockCount / 1024 / 1024;

        long TotalBlockCount = statfs.getBlockCount();
        float totalROMsize = statfs.getBlockSize() * TotalBlockCount / 1024 / 1024;

        return totalROMsize;
    }

    /**
     * Gets the number of cores available in this device, across all processors.
     * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
     *
     * @return The number of cores, or 1 if failed to get result
     */
    public static int getNumCores() {
        // Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                // Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            // Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            // Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());

            if (files == null) {
                return 1;
            }

            return files.length;
        } catch (Exception e) {
            // Print exception
            e.printStackTrace();
            // Default to return 1 core
            return 1;
        }
    }

    /**
     * 获得sim卡的id
     *
     * @param context
     * @return
     */
    public static @Nullable String getSid(Context context) {
        try {
            TelephonyManager telephone = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            return telephone.getSimSerialNumber();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 判断设备是否支持多点触控
     *
     * @param context
     * @return
     */
    public static boolean isSupportMultiTouch(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean isSupportMultiTouch = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
        return isSupportMultiTouch;
    }


    public static float getScreenWidthInPixels() {
        return screenWidthInPixels;
    }

    public static float getScreenHeightPixels() {
        return screenHeightPixels;
    }

    public static float getScreenWidthInDip() {
        return screenWidthInDip;
    }

    public static float getScreenHeightDip() {
        return screenHeightDip;
    }

    public static boolean isMeizuM9() {
        return isMeizuM9;
    }

    public static boolean isXT800() {
        return isXT800;
    }

    public static boolean isS5360() {
        return isS5360;
    }

    public static boolean isU2() {
        return isU2;
    }

    public static boolean isHtcHero() {
        return isHtcHero;
    }

    public static boolean isZten880e() {
        return isZten880e;
    }

    public static boolean isNexus6p() {
        return isNexus6p;
    }

    private static boolean isMiuiOS = false;
    private static boolean isMiuiOSChecked = false;

    /**
     * 是否是miui
     *
     * @return
     */
    public static boolean isMIUI() {
        if (isMiuiOSChecked) {
            return isMiuiOS;
        }

        isMiuiOSChecked = true;

        try {
            final BuildProperties prop = BuildProperties.getInstance();

            isMiuiOS = prop.getProperty("ro.miui.ui.version.code", null) != null
                    || prop.getProperty("ro.miui.ui.version.name", null) != null
                    || prop.getProperty("ro.miui.internal.storage", null) != null;
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return isMiuiOS;
    }

    private static boolean isColorOS = false;
    private static boolean isColorOSChecked = false;

    /**
     * 判断系统是否是oppo或者vivo手机的ColorOS
     * <p>
     * "ro.rom.different.version" -> "ColorOS3.0.0"
     *
     * @return
     */
    public static boolean isColorOS() {
        if (isColorOSChecked) {
            return isColorOS;
        }

        isColorOSChecked = true;

        try {
            final BuildProperties prop = BuildProperties.getInstance();

            String productName = prop.getProperty("ro.product.name", null);
            if (!TextUtils.isEmpty(productName)) {
                if (productName.contains("R11")
                        || productName.contains("r11")) {
                    isColorOS = false;
                    return false;
                }
            }

            String osName = prop.getProperty("ro.rom.different.version", null);
            if (!TextUtils.isEmpty(osName)) {
                if (osName.startsWith("ColorOS")) {
                    isColorOS = true;
                    return true;
                }
            }

            String displayId = prop.getProperty("ro.vivo.os.build.display.id", null);
            if (!TextUtils.isEmpty(displayId)) {
                if (displayId.toLowerCase().startsWith("Funtouch OS_2.6".toLowerCase())) {
                    isColorOS = true;
                    return true;
                }
            }

            String manufacturer = prop.getProperty("ro.product.manufacturer", null);
            if (!TextUtils.isEmpty(manufacturer)) {
                if (manufacturer.toLowerCase().startsWith("OPPO".toLowerCase())) {
                    isColorOS = true;
                    return true;
                }
            }
        } catch (Throwable e) {
//            e.printStackTrace();
        }

        return isColorOS;
    }

    private static boolean isEMUI4OS = false;
    private static boolean isEMUI4OSChecked = false;

    /**
     * 是否是华为的EMUI4.0系统
     *
     * @return
     */
    public static boolean isEMUI4() {
        if (Build.VERSION.SDK_INT < 23) {
            return false;
        }

        if (isEMUI4OSChecked) {
            return isEMUI4OS;
        }

        isEMUI4OSChecked = true;

        try {
            final BuildProperties prop = BuildProperties.getInstance();
            isEMUI4OS = prop.getProperty("ro.build.version.emui", null) != null
                    || prop.getProperty("ro.confg.hw_systemversion", null) != null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isEMUI4OS;
    }

    private static boolean isFLYMEOS = false;
    private static boolean isFLYMEOSChecked = false;

    /**
     * 判断是否是Flyme系统
     *
     * @return
     */
    public static boolean isFlyme() {
        if (isFLYMEOSChecked) {
            return isFLYMEOS;
        }

        isFLYMEOSChecked = true;

        try {
            final BuildProperties prop = BuildProperties.getInstance();
            isFLYMEOS = prop.getProperty("ro.build.user", null).equals("flyme");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return isFLYMEOS;
    }

    public static float dynamicsAdapt(float pix) {
        return getScreenWidthInDip() * pix / 360;
    }

    public static boolean isVivoY85() {
        return "vivoY85A".equals(DeviceUtil.getDeviceName());
    }

}
