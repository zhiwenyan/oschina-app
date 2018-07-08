package com.greenfarm.client.base_library.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;


public class AppManagerUtil {

    private static Stack<Activity> activityStack;
    private static AppManagerUtil instance;

    private AppManagerUtil() {

    }

    /**
     * A single instance
     */
    public static AppManagerUtil instance() {
        if (instance == null) {
            instance = new AppManagerUtil();
        }
        return instance;
    }

    /**
     * 绑定 Activity 添加到集合统一管理
     *
     * @param activity
     */
    public void attachActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        if (activityStack.contains(activity)) {
            return;
        }
        activityStack.add(activity);
    }

    /**
     * 解除 Activity 绑定，移除统一管理
     *
     * @param activity
     */
    public void detachActivity(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            int size = activityStack.size();
            for (int i = 0; i < size; i++) {
                // 遍历所有的 subscriptions ，逐一移除
                Activity equalActivity = activityStack.get(i);
                if (equalActivity.getClass().getName().equals(activity.getClass().getName())) {
                    activityStack.remove(i);
                    i--;
                    size--;
                }
            }
        }
    }

    /**
     * Gets the current Activity (stack last pressed into)
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * The finish of the specified Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && activityStack != null && activityStack.size() > 0) {
            int size = activityStack.size();
            for (int i = 0; i < size; i++) {
                // 遍历所有的 subscriptions ，逐一移除
                Activity equalActivity = activityStack.get(i);
                if (equalActivity.getClass().getName().equals(activity.getClass().getName())) {
                    activityStack.remove(i);
                    equalActivity.finish();
                    i--;
                    size--;
                }
            }
        }
    }

    /**
     * End of the specified class name of the Activity
     */
    public void finishActivity(Class<? extends Activity> cls) {
        if (cls != null && activityStack != null && activityStack.size() > 0) {
            int size = activityStack.size();
            for (int i = 0; i < size; i++) {
                // 遍历所有的 subscriptions ，逐一移除
                Activity equalActivity = activityStack.get(i);
                if (equalActivity.getClass().getName().equals(cls.getName())) {
                    activityStack.remove(i);
                    equalActivity.finish();
                    i--;
                    size--;
                }
            }
        }
    }

    /**
     * Finish all the Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * Exit the application
     */
    @SuppressWarnings("deprecation")
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = ( ActivityManager ) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {

        }
    }
}