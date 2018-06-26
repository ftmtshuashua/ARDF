package com.lfp.androidrapiddevelopmentframework.util;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.lfp.ardf.debug.LogUtil;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Runtime;

import java.util.List;

/**
 * 权限管理<br>
 * Created by LiFuPing on 2018/6/5.
 */
public abstract class PermissionManager {
    Runtime mRuntime;

    public PermissionManager(Fragment fragment){
        mRuntime = AndPermission.with(fragment).runtime();
    }
    public PermissionManager(android.app.Fragment fragment){
        mRuntime = AndPermission.with(fragment).runtime();
    }
    public PermissionManager(Context context){
        mRuntime = AndPermission.with(context).runtime();
    }

    public void request(String... permissions){
        mRuntime.permission(permissions)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        LogUtil.e("onGranted: %s", data);
                        PermissionManager.this.onGranted(data);
                        onFinish(data);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        LogUtil.e("onDenied: %s", data);
                        PermissionManager.this.onDenied(data);
                        onFinish(data);
                    }
                })
                .start();
    }
    public void request(String[]... groups){
        mRuntime.permission(groups)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        LogUtil.e_Pretty("onGranted: %s", data);
                        PermissionManager.this.onGranted(data);
                        onFinish(data);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        LogUtil.e_Pretty("onDenied: %s", data);
                        PermissionManager.this.onDenied(data);
                        onFinish(data);
                    }
                })
                .start();
    }


    public void onFinish(List<String> data){

    }
    public void onGranted(List<String> data){

    }
    public void onDenied(List<String> data){

    }

}
