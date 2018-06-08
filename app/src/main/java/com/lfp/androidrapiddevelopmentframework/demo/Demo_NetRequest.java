package com.lfp.androidrapiddevelopmentframework.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.event.DemoEvent;
import com.lfp.androidrapiddevelopmentframework.net.UnifyResponse;
import com.lfp.androidrapiddevelopmentframework.util.ActionBarControl;
import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.module.net_deprecated.OkHttpRequest;
import com.lfp.ardf.module.net_deprecated.client.OkHttpReqeuestClient;
import com.lfp.ardf.module.net_deprecated.logic.ChainRequestLogic;
import com.lfp.ardf.module.net_deprecated.logic.ImpRequestLogi;
import com.lfp.ardf.module.net_deprecated.logic.ParallelRequestLogic;
import com.lfp.ardf.module.net_deprecated.util.UrlFormat;

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

        mRequestLogic_Chain = new ChainRequestLogic();
        mRequestLogic_Chain.setAppFramework(getAppFk());
        mRequestLogic_Chain.setRequestClient(OkHttpReqeuestClient.getDefualt());

        mRequestLogic_Parallel = new ParallelRequestLogic();
        mRequestLogic_Parallel.setAppFramework(getAppFk());
        mRequestLogic_Parallel.setRequestClient(OkHttpReqeuestClient.getDefualt());
    }

    ImpRequestLogi mRequestLogic_Chain;
    ImpRequestLogi mRequestLogic_Parallel;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_ParallelRequest:
                parallelRequest();
                break;
            case R.id.view_ChainRequest:
                chainRequest();
                break;
            case R.id.view_ClearRequest:
                mRequestLogic_Chain.shutdown();
                mRequestLogic_Parallel.shutdown();
                break;
        }
    }

    void parallelRequest() {
        mTV_Info.setText("");
        mRequestLogic_Parallel.perform(
                new UnifyResponse(getActivity()) {
                    @Override
                    protected void onLog(String str) {
                        super.onLog(str);
                        mTV_Info.append("\n" + str);
                    }

                    @Override
                    public void onRequestResponse(OkHttpRequest request) {
                        super.onRequestResponse(request);
                        if (request.hasNext()) {
                            OkHttpRequest reqeust = (OkHttpRequest) request.getNext();
                            UrlFormat urlFormat = new UrlFormat("http://www.weather.com.cn/data/cityinfo/101190408.html");
                            urlFormat.addQuery("r", MessageFormat.format("修改来自 - ID:{0}", reqeust.getId()));
                            reqeust.setApi(urlFormat.toUrl());
                        }
                    }
                }
                , getApiserver().getWeatherForecast()
                , getApiserver().getWeatherForecast()
                , getApiserver().getWeatherForecast());
    }

    void chainRequest() {
        mTV_Info.setText("");
        mRequestLogic_Chain.perform(
                new UnifyResponse(getActivity()) {

                    @Override
                    protected void onLog(String str) {
                        super.onLog(str);
                        mTV_Info.append("\n" + str);
                    }

                    @Override
                    public void onRequestResponse(OkHttpRequest request) {
                        super.onRequestResponse(request);
                        if (request.hasNext()) {
                            OkHttpRequest reqeust = (OkHttpRequest) request.getNext();
                            UrlFormat urlFormat = new UrlFormat("http://www.weather.com.cn/data/cityinfo/101190408.html");
                            urlFormat.addQuery("r", MessageFormat.format("修改来自 - ID:{0}", reqeust.getId()));
                            reqeust.setApi(urlFormat.toUrl());
                        }
                    }
                }
                , getApiserver().getWeatherForecast()
                , getApiserver().getWeatherForecast()
                , getApiserver().getWeatherForecast());
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
