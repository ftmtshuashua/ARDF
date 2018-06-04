package com.lfp.ardf.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lfp.ardf.debug.LogUtil;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * 延时Dialog <br/>
 * 调用show()方法不会立即显示这个Dialog,它会等到一定时间并且未调用dismiss()才会显示<br/>
 * Created by LiFuPing on 2018/5/31.
 */
public class BaseDelayDialog extends BaseDialog {
    /**
     * 默认延时时间
     */
    public static final long DEFUALT_DELAY_TIME = 100;
    Disposable mDisposable;
    long delay_time = DEFUALT_DELAY_TIME;

    public BaseDelayDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * 设置延时时间
     *
     * @param time
     */
    public void setDelayTime(long time) {
        delay_time = time;
    }

    @Override
    public void show() {
        LogUtil.e(MessageFormat.format("mDisposable == null:{0} && !isShowing():{1}", mDisposable == null, !isShowing()));
        if (mDisposable == null && !isShowing()) {
            mDisposable = Observable.timer(DEFUALT_DELAY_TIME, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    LogUtil.e("显示：" + aLong);
                    if (!isShowing()) {
                        BaseDelayDialog.super.show();
                    }
                    mDisposable = null;
                }
            });
        }
    }

    @Override
    public void dismiss() {
        if (mDisposable != null) mDisposable.dispose();
        super.dismiss();
    }

}