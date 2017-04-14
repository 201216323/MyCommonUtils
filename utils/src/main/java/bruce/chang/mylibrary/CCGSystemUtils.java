package bruce.chang.mylibrary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by: BruceChang
 * Date on : 2017/4/10.
 * Time on: 18:33
 * Progect_Name:MyCommonUtils
 * Source Github：
 * Description:系统工具
 */

@SuppressLint("SimpleDateFormat")
public class CCGSystemUtils {


    /**
     * 调用系统发送短信
     *
     * @param cxt
     * @param smsBody
     */
    public static void sendSMS(Context cxt, String smsBody) {
        Uri smsToUri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", smsBody);
        cxt.startActivity(intent);
    }

    /**
     * 发送短信
     *
     * @param context
     * @param phoneNumber
     * @param content
     */
    public static void sendSms(Context context, String phoneNumber, String content) {
        Uri uri = Uri.parse("smsto:"
                + (TextUtils.isEmpty(phoneNumber) ? "" : phoneNumber));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", TextUtils.isEmpty(content) ? "" : content);
        context.startActivity(intent);
    }

    /**
     * 跳转到拨号
     *
     * @param activity
     * @param phoneNumber
     */
    public static void forwardToDial(Activity activity, String phoneNumber) {
        if (activity != null && !TextUtils.isEmpty(phoneNumber)) {
            activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
        }
    }

    /**
     * 直接呼叫号码
     *
     * @param activity
     * @param phoneNumber
     */
    public static void callPhone(Activity activity, String phoneNumber) {
        if (activity != null && !TextUtils.isEmpty(phoneNumber)) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            activity.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
        }
    }

    /**
     * 发邮件
     *
     * @param mContext
     * @param mailID
     */
    public static void sendMail(Context mContext, String mailID) {
        Uri uri = Uri.parse("mailto:" + mailID);
        mContext.startActivity(new Intent(Intent.ACTION_SENDTO, uri));
    }

    /**
     * 打开浏览器
     *
     * @param context
     * @param url
     */
    public static void openWeb(Context context, String url) {
        Uri uri = Uri.parse(url);
        context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    /**
     * 打开联系人
     *
     * @param context
     * @param requestCode
     */
    public static void openContacts(Activity context, int requestCode) {
        Uri uri = Uri.parse("content://contacts/people");
        context.startActivityForResult(new Intent(Intent.ACTION_PICK, uri), requestCode);
    }

    /**
     * 打开系统设置
     *
     * @param context
     * @param action
     */
    public static void openSettings(Activity context, String action) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.android.settings", action);
        intent.setComponent(comp);
        intent.setAction("android.intent.action.VIEW");
        context.startActivityForResult(intent, 0);
    }



    /**
     * 是否是系统应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isSystemApp(Context context, String packageName) {
        boolean isSys = false;
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 0);
            if (applicationInfo != null && (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                isSys = true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            isSys = false;
        }
        return isSys;
    }

    /**
     * 服务是否在运行
     *
     * @param context
     * @param className
     * @return
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> servicesList = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo si : servicesList) {
            if (className.equals(si.service.getClassName())) {
                isRunning = true;
            }
        }
        return isRunning;
    }

    /**
     * 进程是否运行
     */
    public static boolean isProessRunning(Context context, String proessName) {
        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : lists) {
            if (info.processName.equals(proessName)) {
                isRunning = true;
                return isRunning;
            }
        }

        return isRunning;
    }

    /**
     * 停止服务
     *
     * @param context
     * @param className
     * @return
     */
    public static boolean stopRunningService(Context context, String className) {
        Intent intent_service = null;
        boolean ret = false;
        try {
            intent_service = new Intent(context, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (intent_service != null) {
            ret = context.stopService(intent_service);
        }
        return ret;
    }

    /**
     * 获取进程名字
     *
     * @param appContext
     * @return
     */
    public static String getProcessName(Context appContext) {
        String currentProcessName = null;
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                currentProcessName = processInfo.processName;
                break;
            }
        }
        return currentProcessName;
    }
    /**
     * 结束进程
     *
     * @param context
     * @param pid
     * @param processName
     */
    public static void killProcesses(Context context, int pid, String processName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName;
        try {
            if (!processName.contains(":")) {
                packageName = processName;
            } else {
                packageName = processName.split(":")[0];
            }
            activityManager.killBackgroundProcesses(packageName);
            Method forceStopPackage = activityManager.getClass().getDeclaredMethod("forceStopPackage", String.class);
            forceStopPackage.setAccessible(true);
            forceStopPackage.invoke(activityManager, packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 运行脚本
     *
     * @param script
     * @return
     */
    public static String runScript(String script) {
        String sRet;
        try {
            final Process m_process = Runtime.getRuntime().exec(script);
            final StringBuilder sbread = new StringBuilder();
            Thread tout = new Thread(new Runnable() {
                public void run() {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(m_process.getInputStream()),
                            8192);
                    String ls_1;
                    try {
                        while ((ls_1 = bufferedReader.readLine()) != null) {
                            sbread.append(ls_1).append("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            tout.start();

            final StringBuilder sberr = new StringBuilder();
            Thread terr = new Thread(new Runnable() {
                public void run() {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(m_process.getErrorStream()),
                            8192);
                    String ls_1;
                    try {
                        while ((ls_1 = bufferedReader.readLine()) != null) {
                            sberr.append(ls_1).append("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            terr.start();

            m_process.waitFor();
            while (tout.isAlive()) {
                Thread.sleep(50);
            }
            if (terr.isAlive())
                terr.interrupt();
            String stdout = sbread.toString();
            String stderr = sberr.toString();
            sRet = stdout + stderr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sRet;
    }




    /**
     * 判断当前应用程序是否后台运行
     *
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
            }
        }
        return false;
    }



    /**
     * 创建桌面快捷方式
     *
     * @param cxt
     * @param shortCutName
     * @param icon
     * @param cls
     */
    public static void createDeskShortCut(Context cxt, String shortCutName, int icon, Class<?> cls) {
        Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutIntent.putExtra("duplicate", false);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortCutName);
        Parcelable ico = Intent.ShortcutIconResource.fromContext(cxt.getApplicationContext(), icon);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, ico);
        Intent intent = new Intent(cxt, cls);
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        cxt.sendBroadcast(shortcutIntent);
    }

    /**
     * 创建快捷方式
     *
     * @param ctx
     * @param shortCutName
     * @param iconId
     * @param presentIntent
     */
    public static void createShortcut(Context ctx, String shortCutName, int iconId, Intent presentIntent) {
        Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutIntent.putExtra("duplicate", false);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortCutName);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(ctx, iconId));
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, presentIntent);
        ctx.sendBroadcast(shortcutIntent);
    }

    /**
     * 分享文本
     *
     * @param ctx
     * @param title
     * @param text
     */
    public static void shareText(Context ctx, String title, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        //intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        ctx.startActivity(Intent.createChooser(intent, title));
       /* List<ResolveInfo> ris = getShareTargets(ctx);
        if (ris != null && ris.size() > 0) {
            ctx.startActivity(Intent.createChooser(intent, title));
        }*/
    }

    /**
     * 分享文件(此方法是调用FileUtils.shareFile中的方式)
     *
     * @param ctx
     * @param title
     * @param filePath
     */
    public static void shareFile(Context ctx, String title, String filePath) {
        FileUtils.shareFile(ctx, title, filePath);
    }


    /**
     * 获取可接受分享的应用
     *
     * @param ctx
     * @return
     */
    public static List<ResolveInfo> getShareTargets(Context ctx) {
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        PackageManager pm = ctx.getPackageManager();
        return pm.queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
    }

    /**
     * 获取当前系统的语言
     *
     * @return
     */
    public static String getCurrentLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统的语言
     *
     * @param ctx
     * @return
     */
    public static String getLanguage(Context ctx) {
        if (ctx != null) {
            return ctx.getResources().getConfiguration().locale.getLanguage();
        }
        return null;
    }

    /**
     * GPS是否打开
     *
     * @param context
     * @return
     */
    //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    /**
     * 判断输入负是否处于激活状态
     *
     * @param context
     * @return
     */
    public static boolean isActiveSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    /**
     * 显示软键盘
     *
     * @param context
     * @param editText
     */
    public static void showSoftInputMethod(Context context, EditText editText) {
        if (context != null && editText != null) {
            editText.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, 0);
        }
    }

    /**
     * 关闭软键盘
     *
     * @param context
     * @param editText
     */
    public static void closeSoftInputMethod(Context context, EditText editText) {
        if (context != null && editText != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        }
    }


    /**
     * 显示输入法
     *
     * @param context
     * @param view
     */
    public static void showInputSoftFromWindowMethod(Context context, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示软键盘
     *
     * @param context
     */
    public static void showSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 关闭软键盘
     *
     * @param context
     */
    public static void closeSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && ((Activity) context).getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 打开微信扫描
     *
     * @param context
     */
    public static void toWeChatScan(Context context) {
        try {
            Uri uri = Uri.parse("weixin://dl/scan");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, R.string.wechaterr, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 打开支付宝扫描
     *
     * @param context
     */
    public static void toAliPayScan(Context context) {
        try {
            Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=10000007");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, R.string.alipayerr, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 打开支付宝支付码
     *
     * @param context
     */
    public static void toAliPayPayCode(Context context) {
        try {
            Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=20000056");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, R.string.alipayerr, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取随机数
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandomNumber(int min, int max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 复制到剪切板
     *
     * @param context
     * @param content
     */
    @TargetApi(11)
    public static void coptyToClipBoard(Context context, String content) {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", content);
            clipboard.setPrimaryClip(clip);
        } else {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(content);
        }
    }

}
