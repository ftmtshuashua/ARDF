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
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.base.BaseFragment;
import com.lfp.androidrapiddevelopmentframework.widget.WebProgressBar;
import com.lfp.ardf.util.StatusBarUtil;
import com.lfp.ardf.util.ViewUtil;
import com.lfp.ardf.widget.WebViewFk;

/**
 * 发现<br/>
 * Created by LiFuPing on 2018/6/4.
 */
public class DiscoverFragment extends BaseFragment implements View.OnClickListener {

    public static Fragment newInstance() {
        DiscoverFragment fragment = new DiscoverFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_discover, null);
    }


    final String URL = "https://www.baidu.com";
    WebProgressBar mWebProgressBar;
    WebViewFk mWebViewFx;
    TextView mTV_Title;

    View mV_Back;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StatusBarUtil.fitLayoutAtFullScreen(view.findViewById(R.id.layout_Actionabr), true);
        mTV_Title = view.findViewById(R.id.view_Title);
        mV_Back = view.findViewById(R.id.view_Back);
        mV_Back.setOnClickListener(this);
        mWebProgressBar = view.findViewById(R.id.view_WebProgressBar);
        mWebProgressBar.setMaxProgress(100);

        mWebViewFx = view.findViewById(R.id.view_WebView);
        mWebViewFx.loadUrl(URL);
        mWebViewFx.setOnLoadingProgressChangeLisenter(mOnLoadingProgressChangeLisenter);
        mWebViewFx.setOnTitleChange(mOnTitleChange);

    }

    WebViewFk.OnLoadingProgressChangeLisenter mOnLoadingProgressChangeLisenter = new WebViewFk.OnLoadingProgressChangeLisenter() {

        @Override
        public void onWebStart(WebView view, String url, Bitmap favicon) {
            ViewUtil.setVisibility(mWebProgressBar, View.VISIBLE);
        }

        @Override
        public void onWebLoading(boolean finish, int progress) {
            mWebProgressBar.setProgress(progress);
        }

        @Override
        public void onWebFinish(WebView view, String url) {
            ViewUtil.setVisibility(mWebProgressBar, View.GONE);

            ViewUtil.setVisibility(mV_Back, view.canGoBack() ? View.VISIBLE : View.GONE);

        }
    };
    WebViewFk.OnTitleChange mOnTitleChange = new WebViewFk.OnTitleChange() {
        @Override
        public void onTitleChange(String title) {
            mTV_Title.setText(title);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_Back:
                if (mWebViewFx.canGoBack()) mWebViewFx.goBack();
                break;
        }
    }
}
