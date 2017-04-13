package bruce.chang.mylibrary;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.WebView;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by: BruceChang
 * Date on : 2017/4/10.
 * Time on: 17:23
 * Progect_Name:MyCommonUtils
 * Source Github：https://github.com/nisrulz/easydeviceinfo
 * Description:设备信息工具
 */

public class CCGDeviceUtils {


    /**
     * 判断SDCard是否可用
     */
    public static boolean existSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 多个SD卡时 取外置SD卡
     *
     * @return
     */
    public static String getExternalStorageDirectory() {
        // 参考文章
        // http://blog.csdn.net/bbmiku/article/details/7937745
        Map<String, String> map = System.getenv();
        String[] values = new String[map.values().size()];
        map.values().toArray(values);
        String path = values[values.length - 1];
        if (path.startsWith("/mnt/") && !Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                .equals(path)) {
            return path;
        } else {
            return null;
        }
    }


    /**
     * 获取SDCard可用空间大小
     *
     * @return
     */
    public static long getAvailaleSize() {
        if (!existSDCard()) {
            return 0l;
        }
        File path = Environment.getExternalStorageDirectory(); //取得sdcard文件路径
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }
    /**
     * 获取设备可用空间
     *
     * @param cxt
     * @return
     */
    public static int getDeviceUsableMemory(Context cxt) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return (int) (mi.availMem / (1024 * 1024));
    }


    /**
     * 获取SD大小
     *
     * @return
     */
    public static long getAllSize() {
        if (!existSDCard()) {
            return 0l;
        }
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getBlockCount();
        return availableBlocks * blockSize;
    }

    /**
     * 获取AndroidID
     *
     * @param ctx
     * @return
     */
    public static String getAndroidID(Context ctx) {
        String udid = Settings.Secure.getString(ctx.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if (CCGStringUtils.isEmpty(udid) || udid.equals("9774d56d682e549c")
                || udid.length() < 15) {
            SecureRandom random = new SecureRandom();
            udid = new BigInteger(64, random).toString(16);
        }

        if (CCGStringUtils.isEmpty(udid)) {
            udid = "";
        }

        return udid;
    }

    /**
     * 获取设备IMEI码
     *
     * @param ctx
     * @return
     */
    public static String getIMEI(Context ctx) {
        return ((TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    /**
     * 获取设备IMSI码
     *
     * @param ctx
     * @return
     */
    public static String getIMSI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSubscriberId() != null ? tm.getSubscriberId() : null;
    }

    /**
     * 获取设备序列号
     *
     * @return
     */
    public static String getSerial() {
        return Build.SERIAL;
    }

    /**
     * 获取SIM序列号
     *
     * @param ctx
     * @return
     */
    public static String getSIMSerial(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimSerialNumber();
    }


    /**
     * 获取硬件型号
     *
     * @return
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 获取编译厂商
     *
     * @return
     */
    public static String getBuildBrand() {
        return Build.BRAND;
    }

    /**
     * 获取编译服务器主机
     *
     * @return
     */
    public static String getBuildHost() {
        return Build.HOST;
    }

    /**
     * 获取描述Build的标签
     *
     * @return
     */
    public static String getBuildTags() {
        return Build.TAGS;
    }

    /**
     * 获取系统编译时间
     *
     * @return
     */
    public static long getBuildTime() {
        return Build.TIME;
    }

    /**
     * 获取系统编译作者
     *
     * @return
     */
    public static String getBuildUser() {
        return Build.USER;
    }

    /**
     * 获取编译系统版本(5.1)
     *
     * @return
     */
    public static String getBuildVersionRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取开发代号
     *
     * @return
     */
    public static String getBuildVersionCodename() {
        return Build.VERSION.CODENAME;
    }

    /**
     * 获取源码控制版本号
     *
     * @return
     */
    public static String getBuildVersionIncremental() {
        return Build.VERSION.INCREMENTAL;
    }


    /**
     * 获取编译的SDK
     *
     * @return
     */
    public static int getBuildVersionSDK() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取修订版本列表(LMY47D)
     *
     * @return
     */
    public static String getBuildID() {
        return Build.ID;
    }

    /**
     * CPU指令集
     *
     * @return
     */
    public static String[] getSupportedABIS() {
        String[] result = new String[]{"-"};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            result = Build.SUPPORTED_ABIS;
        }
        if (result == null || result.length == 0) {
            result = new String[]{"-"};
        }
        return result;
    }

    /**
     * 获取硬件制造厂商
     *
     * @return
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取系统启动程序版本号
     *
     * @return
     */
    public static String getBootloader() {
        return Build.BOOTLOADER;
    }


    /**
     * 获取系统屏幕版本
     *
     * @param ctx
     * @return
     */
    public static String getScreenDisplayID(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        return String.valueOf(wm.getDefaultDisplay().getDisplayId());
    }

    /**
     * 获取系统屏幕版本号
     *
     * @return
     */
    public static String getDisplayVersion() {
        return Build.DISPLAY;
    }


    /**
     * 获取语言
     *
     * @return
     */
    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取国家
     *
     * @param ctx
     * @return
     */
    public static String getCountry(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        Locale locale = Locale.getDefault();
        return tm.getSimState() == TelephonyManager.SIM_STATE_READY ? tm.getSimCountryIso().toLowerCase(Locale.getDefault()) : locale.getCountry().toLowerCase(locale);
    }

    /**
     * 获取系统版本:5.1.1
     *
     * @return
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }


    /**
     * 获取GSF序列号
     *
     * @param context
     * @return
     */
    //<uses-permission android:name="com.google.android.providers.gsf.permission.READ_GSERVICES"/>
    public static String getGSFID(Context context) {
        String result;
        final Uri URI = Uri.parse("content://com.google.android.gsf.gservices");
        final String ID_KEY = "android_id";
        String[] params = {ID_KEY};
        Cursor c = context.getContentResolver().query(URI, null, null, params, null);
        if (c == null || !c.moveToFirst() || c.getColumnCount() < 2) {
            return null;
        } else {
            result = Long.toHexString(Long.parseLong(c.getString(1)));
        }
        c.close();
        return result;
    }

    /**
     * 获取蓝牙地址
     *
     * @param context
     * @return
     */
    //<uses-permission android:name="android.permission.BLUETOOTH"/>
    @SuppressWarnings("MissingPermission")
    public static String getBluetoothMAC(Context context) {
        String result = null;
        try {
            if (context.checkCallingOrSelfPermission(Manifest.permission.BLUETOOTH)
                    == PackageManager.PERMISSION_GRANTED) {
                BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
                result = bta.getAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Android设备物理唯一标识符
     *
     * @return
     */
    public static String getPsuedoUniqueID() {
        String devIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            devIDShort += (Build.SUPPORTED_ABIS[0].length() % 10);
        } else {
            devIDShort += (Build.CPU_ABI.length() % 10);
        }
        devIDShort += (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);
        String serial;
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            return new UUID(devIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception e) {
            serial = "ESYDV000";
        }
        return new UUID(devIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**
     * 构建标识,包括brand,name,device,version.release,id,version.incremental,type,tags这些信息
     *
     * @return
     */
    public static String getFingerprint() {
        return Build.FINGERPRINT;
    }

    /**
     * 获取硬件信息
     *
     * @return
     */
    public static String getHardware() {
        return Build.HARDWARE;
    }

    /**
     * 获取产品信息
     *
     * @return
     */
    public static String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取设备信息
     *
     * @return
     */
    public static String getDevice() {
        return Build.DEVICE;
    }

    /**
     * 获取主板信息
     *
     * @return
     */
    public static String getBoard() {
        return Build.BOARD;
    }

    /**
     * 获取基带版本(无线电固件版本 Api14以上)
     *
     * @return
     */
    public static String getRadioVersion() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ? Build.getRadioVersion() : "";
    }

    /**
     * 获取的浏览器指纹(User-Agent)
     *
     * @param ctx
     * @return
     */
    public static String getUA(Context ctx) {
        final String system_ua = System.getProperty("http.agent");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return new WebView(ctx).getSettings().getDefaultUserAgent(ctx) + "__" + system_ua;
        } else {
            return new WebView(ctx).getSettings().getUserAgentString() + "__" + system_ua;
        }
    }


    /**
     * 获取google账号
     *
     * @param ctx
     * @return
     */
    //<uses-permission android:name="android.permission.GET_ACCOUNTS"/>
    @SuppressWarnings("MissingPermission")
    public static String[] getGoogleAccounts(Context ctx) {
        if (ctx.checkCallingOrSelfPermission(Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            Account[] accounts = AccountManager.get(ctx).getAccountsByType("com.google");
            String[] result = new String[accounts.length];
            for (int i = 0; i < accounts.length; i++) {
                result[i] = accounts[i].name;
            }
            return result;
        }
        return null;
    }


    /**
     * 获取Cpu内核数
     *
     * @return
     */
    public static int getCPUCoresNum() {
        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    return Pattern.matches("cpu[0-9]", pathname.getName());
                }

            });
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    private static final String suSearchPaths[] = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};

    /**
     * 是否root
     *
     * @return
     */
    public static boolean isRooted() {
        File file;
        boolean flag1 = false;
        for (String suSearchPath : suSearchPaths) {
            file = new File(suSearchPath + "su");
            if (file.isFile() && file.exists()) {
                flag1 = true;
                break;
            }
        }
        return flag1;
    }


    /**
     * 获得root权限
     *
     * @param context
     * @return
     */
    public static boolean getRootPermission(Context context) {
        String packageCodePath = context.getPackageCodePath();
        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd = "chmod 777 " + packageCodePath;
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    /**
     * 当前设备是否是模拟器
     *
     * @return
     */
    public static boolean isRunningOnEmulator() {
        return Build.BRAND.contains("generic")
                || Build.DEVICE.contains("generic")
                || Build.PRODUCT.contains("sdk")
                || Build.HARDWARE.contains("goldfish")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("vbox86p")
                || Build.DEVICE.contains("vbox86p")
                || Build.HARDWARE.contains("vbox86");
    }


    /**
     * 判断是否有软控制键（手机底部几个按钮）
     *
     * @param activity
     * @return
     */
    public boolean isSoftKeyAvail(Activity activity) {
        final boolean[] isSoftkey = {false};
        final View activityRootView = (activity).getWindow().getDecorView().findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int rootViewHeight = activityRootView.getRootView().getHeight();
                int viewHeight = activityRootView.getHeight();
                int heightDiff = rootViewHeight - viewHeight;
                if (heightDiff > 100) { // 99% of the time the height diff will be due to a keyboard.
                    isSoftkey[0] = true;
                }
            }
        });
        return isSoftkey[0];
    }


    /**
     * 震动
     *
     * @param context
     * @param duration
     */
    public static void vibrate(Context context, long duration) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {
                0, duration
        };
        vibrator.vibrate(pattern, -1);
    }


    /**
     * 判断当前设备是否为手机
     *
     * @param context
     * @return
     */
    public static boolean isPhone(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断手机是否处理睡眠
     *
     * @param context
     * @return
     */
    public static boolean isSleeping(Context context) {
        KeyguardManager kgMgr = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return kgMgr.inKeyguardRestrictedInputMode();
    }


}


