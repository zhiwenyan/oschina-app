package com.greenfarm.client.base_library.permission;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;

import java.util.List;

/**
 * Description:
 * Data：3/28/2018-2:59 PM
 *
 * @author: yanzhiwen
 */
public class PermissionHelper {

    // 1、Object Fragment Activity 2、int 请求码  3、请求的权限 String[]
    private Object mObject;
    private int mRequestCode;
    private String[] mPermissions;

    public PermissionHelper(Object object) {
        this.mObject = object;
    }

    public static void requestPermission(Activity activity, int requestCode, String[] permissions) {
        PermissionHelper.with(activity).requestCode(requestCode)
                .requestPermission(permissions).request();
    }

    public static void requestPermission(Fragment fragment, int requestCode, String[] permissions) {
        PermissionHelper.with(fragment).requestCode(requestCode)
                .requestPermission(permissions).request();
    }

    /**
     * 传Activity
     *
     * @param activity
     * @return
     */
    public static PermissionHelper with(Activity activity) {
        return new PermissionHelper(activity);
    }

    /**
     * 传Fragment
     *
     * @param fragment
     * @return
     */
    public static PermissionHelper with(Fragment fragment) {
        return new PermissionHelper(fragment);
    }

    /**
     * 请求码
     *
     * @param requestCode
     * @return
     */
    public PermissionHelper requestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    /**
     * 请求的权限
     *
     * @param permissions
     * @return
     */
    public PermissionHelper requestPermission(String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    /**
     * 真正判断和发起请求
     */
    public void request() {
        //1、首先判断当前的版本是不是6.0以上
        if (!PermissionUtils.isOverMarshmallow()) {
            //2、如果不是6.0以上，那么直接执行方法，反射执行
            //执行什么方法不确定，只能用注解，反射的方式去执行
            PermissionUtils.executeSuccessMethod(mObject, mRequestCode);
            return;
        }
        //3、如果是6.0以上，那么首先判断权限是否授予
        //需要申请的权限中，获取没有授权的权限
        List<String> deniedPermission = PermissionUtils.getDeniedPermission(mObject, mPermissions);
        if (deniedPermission.size() == 0) {
            //全部都是授权过的，3.1、如果授予了，反射执行方法
            PermissionUtils.executeSuccessMethod(mObject, mRequestCode);
        } else {
            //3.2、如果没授予，那么我们就申请权限
            ActivityCompat.requestPermissions(PermissionUtils.getActivity(mObject),
                    deniedPermission.toArray(new String[deniedPermission.size()]),
                    mRequestCode);
        }
    }

    /**
     * 处理授权的结果回调
     *
     * @param object
     * @param requestCode
     * @param permissions
     */
    public static void requestPermissionsResult(Object object, int requestCode, String[] permissions) {
        //再次获取下没有授权的权限
        List<String> deniedPermission = PermissionUtils.getDeniedPermission(object, permissions);
        if (deniedPermission.size() == 0) {
            PermissionUtils.executeSuccessMethod(object, requestCode);
        } else {
            //执行没有授权
            PermissionUtils.executeFailedMethod(object, requestCode);
        }
    }
}
