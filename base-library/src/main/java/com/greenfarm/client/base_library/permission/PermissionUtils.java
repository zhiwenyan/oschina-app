package com.greenfarm.client.base_library.permission;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Description: 处理权限的工具类
 * Data：3/28/2018-3:23 PM
 *
 * @author: yanzhiwen
 */
public class PermissionUtils {
    private PermissionUtils() {
        throw new UnsupportedOperationException("cannot be instantiated ");
    }

    /**
     * 判断是不是6.0以及以上的系统
     *
     * @return
     */
    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static void executeSuccessMethod(Object object, int requestCode) {
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Log.i("TAG", method.getName());
            //获取改方法有没有打PermissionSuccess打这个注解
            PermissionSuccess successMethod = method.getAnnotation(PermissionSuccess.class);
            if (successMethod != null) {
                if (requestCode == successMethod.requestCode()) {
                    //反射执行该方法
                    executeMethod(object, method);
                }
            }
        }
    }

    /**
     * 反射执行该方法
     *
     * @param object
     * @param method
     */
    private static void executeMethod(Object object, Method method) {
        //第一个参数传执行的类，第二个参数传方法的参数
        try {
            method.setAccessible(true);
            method.invoke(object, new Object[]{});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有没有授予过的权限
     *
     * @param object      Activity or Fragment
     * @param permissions
     * @return
     */
    public static List<String> getDeniedPermission(Object object, String[] permissions) {
        List<String> deniedPermission = new ArrayList<>();
        for (String requestPermission : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(object), requestPermission) == PackageManager.PERMISSION_DENIED) {
                deniedPermission.add(requestPermission);
            }
        }
        return deniedPermission;

    }

    public static Activity getActivity(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        }
        if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        }
        return null;
    }

    /**
     * @param object
     * @param requestCode
     */
    public static void executeFailedMethod(Object object, int requestCode) {
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            //获取改方法有没有打PermissionFailure打这个注解
            PermissionFailure failureMethod = method.getAnnotation(PermissionFailure.class);
            if (failureMethod != null) {
                if (requestCode == failureMethod.requestCode()) {
                    //反射执行该方法
                    executeMethod(object, method);
                }
            }
        }
    }
}
