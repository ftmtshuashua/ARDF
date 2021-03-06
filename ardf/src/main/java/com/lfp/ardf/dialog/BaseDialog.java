package com.lfp.ardf.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lfp.ardf.R;
import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.framework.util.SimpleLifeCycleObserve;
import com.lfp.ardf.util.ScreenUtils;

/**
 * <br>
 * Created by LiFuPing on 2018/5/31.
 */
public class BaseDialog extends Dialog {
    /**
     * 默认最小宽度
     */
    public static final int DEFUALT_MIN_WIDTH = 300;

    /**
     * 默认边距
     */
    public static final int DEFUALT_MARGIN = 30;

    public BaseDialog(@NonNull Context c) {
        super(c, R.style.ARDF_BaseDialogStyle);

        if (c instanceof IAppFramework) {
            attachAppFk((IAppFramework) c);
        }
    }

    public BaseDialog(@NonNull IAppFramework appfk) {
        super(appfk.getContext(), R.style.ARDF_BaseDialogStyle);
        attachAppFk(appfk);
    }


    /**
     * 如果这个Dialog是在框架中调用的，这监听框架生命周期
     * @param appfk IAppFramework
     */
    protected void attachAppFk(IAppFramework appfk) {
        appfk.registeredObserve(new SimpleLifeCycleObserve() {
            @Override
            public void onDestroy() {
                super.onDestroy();
                dismiss();
                LogUtil.e("BaseDialog  -   dismiss();");
            }
        });
    }


    public void setSmartContentView(View view) {
        setSmartContentView(view, DEFUALT_MIN_WIDTH, DEFUALT_MARGIN);
    }

    public void setSmartContentView(@LayoutRes int resource) {
        setSmartContentView(resource, DEFUALT_MIN_WIDTH, DEFUALT_MARGIN);
    }

    public void setSmartContentView(@LayoutRes int resource, int min_width, int margin) {
        View view = LayoutInflater.from(getContext()).inflate(resource, null);
        setSmartContentView(view, min_width, margin);
    }

    /**
     * 为这个Dialog设置一个内容布局,可以设置他的最小宽度和边距<br>
     *
     * @param view      内容布局
     * @param min_width 当布局宽度小于这个至的时候，使用这个值来作为布局的宽度
     * @param margin    当内容宽度太大的时候，使用这个值来作为布局与屏幕边框的距离
     */
    public void setSmartContentView(View view, int min_width, int margin) {
        min_width = ScreenUtils.dip2px(min_width);
        margin = ScreenUtils.dip2px(margin);
        view.setMinimumWidth(min_width);
        setContentView(view);
        ViewGroup.MarginLayoutParams mParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        mParams.leftMargin = margin;
        mParams.rightMargin = margin;
        mParams.topMargin = margin;
        mParams.bottomMargin = margin;

    }


}
