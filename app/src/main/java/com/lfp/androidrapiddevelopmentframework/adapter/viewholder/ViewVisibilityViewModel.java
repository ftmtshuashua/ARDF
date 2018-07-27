package com.lfp.androidrapiddevelopmentframework.adapter.viewholder;

import android.view.View;

import com.lfp.androidrapiddevelopmentframework.event.InfoEvent;
import com.lfp.ardf.util.ViewUtils;

/**
 * <pre>
 * desc:
 *      View可见度控制
 *
 * function:
 *
 * Created by LiFuPing on 2018/7/26.
 * </pre>
 */
public class ViewVisibilityViewModel extends InfoEvent {
    View mControlView;

    public ViewVisibilityViewModel( View view , String info) {
        super(info);
        mControlView = view;
    }

    @Override
    public void call() {
        ViewUtils.setVisibility(mControlView, mControlView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
}
