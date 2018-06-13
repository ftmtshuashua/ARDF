package com.lfp.androidrapiddevelopmentframework.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.api.Apiserver;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.dialog.ProgressDelayDialog;
import com.lfp.androidrapiddevelopmentframework.event.DemoEvent;
import com.lfp.androidrapiddevelopmentframework.util.ActionBarControl;
import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.dialog.BaseDialog;
import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.module.net.imp.RequestChain;
import com.lfp.ardf.module.net.i.RequestNode;
import com.lfp.ardf.module.net.imp.RequestMerge;
import com.lfp.ardf.module.net.i.RequestListener;

import java.text.MessageFormat;

/**
 * 网络请求demo
 * Created by LiFuPing on 2018/5/19.
 */
public class Demo_NetRequest extends BaseActivity implements View.OnClickListener {
    public static final void start(IAppFramework appfk) {
        Intent intent = new Intent(appfk.getContext(), Demo_NetRequest.class);
        appfk.startActivity(intent);
    }

    TextView mTV_Info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_netrequest);
        new ActionBarControl(getActivity())
                .setTitle("网络请求 Demo")
                .setSubTitle("并发请求/链式请求")
                .showBack()
                .setBackFinishActivity(getActivity());

        mTV_Info = findViewById(R.id.view_Info);

        findViewById(R.id.view_ParallelRequest).setOnClickListener(this);
        findViewById(R.id.view_ChainRequest).setOnClickListener(this);
        findViewById(R.id.view_ClearRequest).setOnClickListener(this);


        createListener();
        singleRequest();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_ParallelRequest:
                mergeRequest();
                break;
            case R.id.view_ChainRequest:
                testNewRequest();
                break;
            case R.id.view_ClearRequest:
                hhRequest();
                break;
        }
    }

    long mTime;
    RequestListener mRequestListener;

    void createListener() {
        mRequestListener = new RequestListener() {
            BaseDialog mDialog = new ProgressDelayDialog(getAppFk());

            @Override
            public void onStart(RequestNode request) {
                mDialog.show();
                LogUtil.e(MessageFormat.format("---- onStart id:{0}", request.getId()));
                mTime = System.currentTimeMillis();
            }

            @Override
            public void onError(RequestNode request, Throwable e) {
                LogUtil.e(MessageFormat.format("---- onError id:{0}  e:{1}", request.getId(), e.getMessage()));
            }

            @Override
            public void onResponse(RequestNode request) {
                LogUtil.e(MessageFormat.format("---- onResponse id:{0}", request.getId()));
            }

            @Override
            public void onComplete(RequestNode request) {
                LogUtil.e(MessageFormat.format("---- onComplete id:{0}", request.getId()));
            }

            @Override
            public void onEnd(RequestNode request) {
                LogUtil.e(MessageFormat.format("---- onEnd id:{0}   耗时:{1}ms", request.getId(), (System.currentTimeMillis() - mTime)));
                mDialog.dismiss();
            }
        };
    }

    /*单个请求*/
    void singleRequest() {
        RequestNode request = Apiserver.getNewRequest();
        request.setRequestListener(mRequestListener);
        request.start();
    }

    /*链式请求*/
    void testNewRequest() {
        RequestChain mRequestChain = RequestChain.request(Apiserver.getNewRequest(), Apiserver.getNewRequest());
        mRequestChain.setRequestListener(mRequestListener);
        mRequestChain.start();
    }

    /*并发请求*/
    void mergeRequest() {
        RequestMerge request = new RequestMerge(Apiserver.getNewRequest(), Apiserver.getNewRequest());
        request.setRequestListener(mRequestListener);
        request.start();
    }

    /*混合请求*/
    void hhRequest() {
        RequestMerge request = new RequestMerge(Apiserver.getNewRequest(), Apiserver.getNewRequest());
        RequestChain mRequestChain = RequestChain.request(Apiserver.getNewRequest(), request);
        mRequestChain.setRequestListener(mRequestListener);
        mRequestChain.start();
    }

    /*Demo入口*/
    public static final class Demo extends DemoEvent {

        public Demo(IAppFramework appfk) {
            super(appfk, "网络请求");
            setInfo("并发请求/链式请求解决方案");
        }

        @Override
        public void call() {
            Demo_NetRequest.start(getAppFk());
        }
    }
}
