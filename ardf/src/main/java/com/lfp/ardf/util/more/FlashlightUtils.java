package com.lfp.ardf.util.more;

import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.util.AppUtils;

import java.io.IOException;

import static android.hardware.Camera.Parameters.FLASH_MODE_OFF;
import static android.hardware.Camera.Parameters.FLASH_MODE_TORCH;

/**
 * <pre>
 * desc:
 *      手电筒工具
 * function:
 *      getInstance()       :获得工具
 *      register()          :注册
 *      unregister()        :注销
 *      setFlashlightOn()   :打开手电筒
 *      setFlashlightOff()  :关闭手电筒
 *      isFlashlightOn()    :检查手电筒是否打开
 *      isFlashlightEnable():检查是否支持手电筒
 *
 * Created by LiFuPing on 2018/6/27.
 * </pre>
 */
public class FlashlightUtils {

    private Camera mCamera;

    private FlashlightUtils() {
    }

    /**
     * Return the single {@link FlashlightUtils} instance.
     *
     * @return the single {@link FlashlightUtils} instance
     */
    public static FlashlightUtils getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Register the utils of flashlight.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public boolean register() {
        try {
            mCamera = Camera.open(0);
        } catch (Throwable t) {
            LogUtil.e("register: ", t);
            return false;
        }
        if (mCamera == null) {
            LogUtil.e("register: open camera failed!");
            return false;
        }
        try {
            mCamera.setPreviewTexture(new SurfaceTexture(0));
            mCamera.startPreview();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Unregister the utils of flashlight.
     */
    public void unregister() {
        if (mCamera == null) return;
        mCamera.stopPreview();
        mCamera.release();
    }

    /**
     * Turn on the flashlight.
     */
    public void setFlashlightOn() {
        if (mCamera == null) {
            LogUtil.e("setFlashlightOn: the utils of flashlight register failed!");
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (!FLASH_MODE_TORCH.equals(parameters.getFlashMode())) {
            parameters.setFlashMode(FLASH_MODE_TORCH);
            mCamera.setParameters(parameters);
        }
    }

    /**
     * Turn off the flashlight.
     */
    public void setFlashlightOff() {
        if (mCamera == null) {
            LogUtil.e("setFlashlightOn: the utils of flashlight register failed!");
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (FLASH_MODE_TORCH.equals(parameters.getFlashMode())) {
            parameters.setFlashMode(FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
        }
    }

    /**
     * Return whether the flashlight is working.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public boolean isFlashlightOn() {
        if (mCamera == null) {
            LogUtil.e("setFlashlightOn: the utils of flashlight register failed!");
            return false;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        return FLASH_MODE_TORCH.equals(parameters.getFlashMode());
    }

    /**
     * Return whether the device supports flashlight.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFlashlightEnable() {
        return AppUtils.getApp()
                .getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    private static final class LazyHolder {
        private static final FlashlightUtils INSTANCE = new FlashlightUtils();
    }
}
