package com.lfp.androidrapiddevelopmentframework.activity.module.home.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.base.BaseFragment;
import com.lfp.androidrapiddevelopmentframework.util.ActionBarControl;
import com.lfp.androidrapiddevelopmentframework.widget.WebProgressBar;
import com.lfp.ardf.util.ViewUtils;
import com.lfp.ardf.widget.WebViewFk;

/**
 * 集成<br>
 * Created by LiFuPing on 2018/6/4.
 */
public class ReadmeFragment extends BaseFragment {

    public static Fragment newInstance() {
        ReadmeFragment fragment = new ReadmeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_discover, null);
    }

    final String URL = "https://3888.group/ardf/readme";

    WebProgressBar mWebProgressBar;
    WebViewFk mWebViewFx;

    ActionBarControl mActionBarControl;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActionBarControl = new ActionBarControl(view).setfitsSystemWindows().setTitle("集成");
        mActionBarControl.setBackOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebViewFx.canGoBack()) mWebViewFx.goBack();
            }
        });
        mWebProgressBar = view.findViewById(R.id.view_WebProgressBar);
        mWebProgressBar.setMaxProgress(100);

        mWebViewFx = view.findViewById(R.id.view_WebView);
        mWebViewFx.setOnLoadingProgressChangeLisenter(mOnLoadingProgressChangeLisenter);
        mWebViewFx.setOnTitleChange(mOnTitleChange);

        mWebViewFx.loadUrl(URL);
    }

    WebViewFk.OnLoadingProgressChangeLisenter mOnLoadingProgressChangeLisenter = new WebViewFk.OnLoadingProgressChangeLisenter() {

        @Override
        public void onWebStart(WebView view, String url, Bitmap favicon) {
            ViewUtils.setVisibility(mWebProgressBar, View.VISIBLE);
        }

        @Override
        public void onWebLoading(boolean finish, int progress) {
            mWebProgressBar.setProgress(progress);
        }

        @Override
        public void onWebFinish(WebView view, String url) {
            ViewUtils.setVisibility(mWebProgressBar, View.GONE);

            if (view.canGoBack()) mActionBarControl.showBack();
            else mActionBarControl.hiddenBack();
        }
    };
    WebViewFk.OnTitleChange mOnTitleChange = new WebViewFk.OnTitleChange() {
        @Override
        public void onTitleChange(String title) {
            mActionBarControl.setTitle(title);
        }
    };

    @Override
    public void onFragmentShow() {
        super.onFragmentShow();
        mWebViewFx.resume();
    }

    @Override
    public void onFragmentHidden() {
        super.onFragmentHidden();
        mWebViewFx.pause();
    }

}
