package bruce.chang.mylibrary;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator
 * Date:2017/4/9
 * Time:21:23
 * Author:BruceChang
 * Function: 获取手机应用信息
 */

public class ApkUtils {

    /**
     * 获取应用名称
     *
     * @param context
     * @param packageName
     * @return
     */
    public static String getAPPName(Context context, String packageName) {

        PackageManager packageManager = context.getPackageManager();
        String appName = null;
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            appName = String.valueOf(packageManager.getApplicationLabel(applicationInfo));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return appName;
    }

    /**
     * 获取应用图标
     *
     * @param context
     * @param packageName
     * @return
     */
    public static Drawable getAPPIcon(Context context, String packageName) {

        PackageManager packageManager = context.getPackageManager();
        Drawable appIcon = null;
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            appIcon = applicationInfo.loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return appIcon;
    }


    /**
     * 获取应用更新日期
     *
     * @param context
     * @param packageName
     * @return
     */
    public static long getAppDate(Context context, String packageName) {
        long lastUpdateTime = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            lastUpdateTime = packageInfo.lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return lastUpdateTime;
    }

    /**
     * 获取应用大小
     *
     * @param context
     * @param packageName
     * @return
     */
    public static long getAppSize(Context context, String packageName) {
        long appSize = 0;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            appSize = new File(applicationInfo.sourceDir).length();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appSize;
    }

    /**
     * 获取应用apk文件
     *
     * @param context
     * @param packageName
     * @return
     */
    public static String getAppApk(Context context, String packageName) {

/*        String sourceDir = null;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            sourceDir = applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return sourceDir;*/

        return context.getPackageCodePath();
    }

    /**
     * 获取应用版本名称
     *
     * @param context
     * @param packageName
     * @return
     */
    public static String getAppVersionName(Context context, String packageName) {
        String appVersion = null;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            appVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersion;
    }

    /**
     * 获取应用版本号
     *
     * @param context
     * @param packageName
     * @return
     */
    public static int getAppVersionCode(Context context, String packageName) {
        int appVersionCode = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            appVersionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersionCode;
    }

    /**
     * 获取应用的安装市场
     *
     * @param context
     * @param packageName
     * @return
     */
    public static String getAppInstaller(Context context, String packageName) {
        return context.getPackageManager().getInstallerPackageName(packageName);
    }


    /**
     * 获取Manifest Meta Data
     *
     * @param context
     * @param metaKey
     * @return
     */
    public static String getMetaData(Context context, String metaKey) {
        String msg = "";
        if (!TextUtils.isEmpty(metaKey)) {

            try {
                ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                msg = appInfo.metaData.getString(metaKey);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return msg;
    }

    /**
     * 获取渠道号
     *
     * @param context
     * @param channelKey
     * @return
     */
    public static String getChannelNo(Context context, String channelKey) {
        return getMetaData(context, channelKey);
    }


    /**
     * 是否有权限
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean hasPermission(Context context, String permission) {
        if (context != null && !TextUtils.isEmpty(permission)) {
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    if (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(permission, context.getPackageName())) {
                        return true;
                    }
                    Log.d("ApkUtils", "Have you  declared permission " + permission + " in AndroidManifest.xml ?");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 检查手机上是否安装了指定的软件  ApplicationInfo
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstalled_1(Context context, String packageName) {
        boolean installed = false;
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        List<ApplicationInfo> installedApplications = context.getPackageManager().getInstalledApplications(0);
        for (ApplicationInfo in : installedApplications) {
            if (packageName.equals(in.packageName)) {
                installed = true;
                break;
            } else {
                installed = false;
            }
        }
        return installed;
    }

    /**
     * 检查手机上是否安装了指定的软件  PackageInfo
     *
     * @param context
     * @param packageName 应用包名
     * @return
     */
    public static boolean isInstalled_2(Context context, String packageName) {

        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (PackageInfo packageInfo : packageInfos) {
                packageNames.add(packageInfo.packageName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 获取非系统应用包名
     *
     * @param context
     * @return
     */
    public static List<String> getAppPackageNamelist(Context context) {
        List<String> packList = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packinfos = pm.getInstalledPackages(0);
        for (PackageInfo packinfo : packinfos) {
            String packname = packinfo.packageName;
            packList.add(packname);
        }

        return packList;
    }


    /**
     * 安装apk
     *
     * @param context
     * @param apkfile
     */
    public static void installApk(Context context, File apkfile) {
        if (!apkfile.exists() || !apkfile.isFile() || apkfile.length() <= 0) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("application/vnd.android.package-archive");
        intent.setData(Uri.fromFile(apkfile));
        intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 安装一个apk文件
     *
     * @param context
     * @param filePath
     * @return
     */
    public static boolean installApk_1(Context context, String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile() || file.length() <= 0) {
            return false;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }

    /**
     * 安装一个apk文件
     *
     * @param context
     * @param uriFile
     */
    public static void installApk_2(Context context, File uriFile) {
        if (!uriFile.exists() || !uriFile.isFile() || uriFile.length() <= 0) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(uriFile), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 卸载应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean uninstallApk_1(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }





    /**
     * 启动应用
     *
     * @param context
     * @param packagename
     */
    public static void runApp(Context context, String packagename) {
        context.startActivity(new Intent(context.getPackageManager().getLaunchIntentForPackage(packagename)));
    }

    /**
     * 清除应用内部缓存
     *
     * @param context
     */
    public static void cleanCache(Context context) {
        FileUtils.deleteFileByDirectory(context.getCacheDir());
    }

    /**
     * 清除应用内部数据库
     *
     * @param context
     */
    public static void cleanDatabases(Context context) {
        String filepath = String.format(String.format(context.getFilesDir().getParent() + File.separator + "%s", "databases"));
        FileUtils.deleteFileByDirectory(new File(filepath));
    }

    /**
     * 清除应用内部SP
     *
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        String filepath = String.format(String.format(context.getFilesDir().getParent() + File.separator + "%s", "shared_prefs"));
        FileUtils.deleteFileByDirectory(new File(filepath));
    }

    /**
     * 获取应用签名
     *
     * @param context
     * @return
     */
    public static String getSign(Context context) {
        String pkgName = context.getPackageName();
        try {
            PackageInfo pis = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            return hexdigest(pis.signatures[0].toByteArray());
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(CCGSystemUtils.class.getName() + "the " + pkgName + "'s application not found");
        }
    }

    /**
     * 32位签名
     *
     * @param paramArrayOfByte
     * @return
     */
    public static String hexdigest(byte[] paramArrayOfByte) {
        final char[] hexDigits = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramArrayOfByte);
            byte[] arrayOfByte = localMessageDigest.digest();
            char[] arrayOfChar = new char[32];
            for (int i = 0, j = 0; ; i++, j++) {
                if (i >= 16) {
                    return new String(arrayOfChar);
                }
                int k = arrayOfByte[i];
                arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
                arrayOfChar[++j] = hexDigits[(k & 0xF)];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 清理后台进程和服务
     *
     * @param cxt
     * @return
     */
    public static int gc(Context cxt) {
        //long i = getDeviceUsableMemory(cxt);
        int count = 0;
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = am.getRunningServices(100);
        if (serviceList != null)
            for (ActivityManager.RunningServiceInfo service : serviceList) {
                if (service.pid == android.os.Process.myPid())
                    continue;
                try {
                    android.os.Process.killProcess(service.pid);
                    count++;
                } catch (Exception e) {
                    e.getStackTrace();
                    //continue;
                }
            }

        List<ActivityManager.RunningAppProcessInfo> processList = am.getRunningAppProcesses();
        if (processList != null)
            for (ActivityManager.RunningAppProcessInfo process : processList) {
                if (process.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    String[] pkgList = process.pkgList;
                    for (String pkgName : pkgList) {
                        try {
                            am.killBackgroundProcesses(pkgName);
                            count++;
                        } catch (Exception e) {
                            e.getStackTrace();
                            //continue;
                        }
                    }
                }
            }
        return count;
    }

}
