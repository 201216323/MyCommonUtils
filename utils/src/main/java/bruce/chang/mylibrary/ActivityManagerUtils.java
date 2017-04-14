package bruce.chang.mylibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by: BruceChang
 * Date on : 2017/4/13.
 * Time on: 9:34
 * Progect_Name:MyCommonUtils
 * Source Github：
 * Description: 应用程序Activity管理类，用于Activity管理和应用程序的退出
 */

public class ActivityManagerUtils {
    private static Stack<Activity> activityStack;
    private static ActivityManagerUtils activityManager;

    private ActivityManagerUtils() {
    }


    /**
     * 单一实例
     *
     * @return
     */
    public static ActivityManagerUtils getActivityManager() {
        if (activityManager == null) {
            synchronized (ActivityManagerUtils.class) {
                if (activityManager == null) {
                    activityManager = new ActivityManagerUtils();
                }
            }
        }
        return activityManager;
    }

    /**
     * 添加Activity到栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前的Activity （也就是堆栈中最后一个入栈的）
     *
     * @return
     */
    public Activity getCurrentActivity() {
        if (activityStack == null) {
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity  （也是堆栈中最后一个入栈的）
     */
    public void finishCurrentActivity() {
        if (activityStack == null) {
            return;
        }
        Activity activity = activityStack.lastElement();
        activity.finish();
    }


    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivityWithName(Activity activity) {
        if (activityStack == null) {
            return;
        }
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }


    /**
     * 结束指定类名的Activity
     *
     * @param className
     */
    public void finishActivityWithName(Class<?> className) {
        if (activityStack == null) {
            return;
        }
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null && activity.getClass().equals(className)) {
                activity.finish();
                iterator.remove();
            }
        }
    }

    /**
     * 结束所有的Activity
     */
    public void finishAllActivity() {
        if (activityStack == null) {
            return;
        }
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 根据Activity 获取堆中的Activity实例
     *
     * @param activityName
     * @return
     */
    public Activity getActivityByName(String activityName) {
        if (activityStack == null) {
            return null;
        }
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null && TextUtils.equals(activity.getClass().getName(), activityName)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 退出应用程序
     *
     * @param context
     */
    public void appExit(Context context) {

        try {
            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     *
     * @Title: startActivityForPackage
     * @Description: TODO(通过)
     * @param @param context
     * @param @param packageName   包名
     * @return void    返回类型
     * @throws
     */
    @SuppressLint("NewApi")
    public static boolean startActivityForPackage(Context context,String packageName){
        PackageInfo pi = null;
        try {
            pi =context. getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        //		resolveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        resolveIntent.setPackage(pi.packageName);

        List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = apps.iterator().next();
        if (ri != null ) {
            String packageName1 = ri.activityInfo.packageName;
            String className = ri.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

            ComponentName cn = new ComponentName(packageName1, className);

            intent.setComponent(cn);
            context.startActivity(intent);
            return true;
        }
        return false;
    }


    /**
     * 主动回到Home，后台运行
     * @param context
     */
    public static void goHome(Context context) {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(mHomeIntent);
    }


}
