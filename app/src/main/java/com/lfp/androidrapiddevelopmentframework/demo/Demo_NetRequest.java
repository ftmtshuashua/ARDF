package com.lfp.androidrapiddevelopmentframework.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lfp.androidrapiddevelopmentframework.MainActivity;
import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.net.UnifyResponse;
import com.lfp.ardf.adapter.SimpleRecyclerViewAdapter;
import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.module.net.OkHttpRequest;
import com.lfp.ardf.module.net.client.OkHttpReqeuestClient;
import com.lfp.ardf.module.net.logic.ChainRequestLogic;
import com.lfp.ardf.module.net.logic.ImpRequestLogi;
import com.lfp.ardf.module.net.util.UrlFormat;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 网络请求demo
 * Created by LiFuPing on 2018/5/19.
 */
public class Demo_NetRequest extends BaseActivity {
    public static final void start(IAppFramework appfk) {
        Intent intent = new Intent(appfk.getContext(), Demo_NetRequest.class);
        appfk.startActivity(intent);
    }

    SimpleRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("网络请求");

        RecyclerView mRecyclerView = findViewById(R.id.view_ReyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter = new SimpleRecyclerViewAdapter(MainActivity.DemoEntranceHolder.class, R.layout.layout_simpler_textview));


        List<DemoEntrance> arrays = new ArrayList<>();
        arrays.add(mDemoEntrance);
        arrays.add(mDemoCanleReqeust);
        mAdapter.setAndUpdata(arrays);

        mRequestLogic = new ChainRequestLogic();
        mRequestLogic.setAppFramework(getAppFk());
        mRequestLogic.setRequestClient(OkHttpReqeuestClient.getDefualt());
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    LogUtil.e("------------notify Activity onDestroy() ------------");
                    finish();
                    break;
                case 1:
                    mRequestLogic.shutdown();
                    LogUtil.e("----- 取消请求 ------");
                    break;
            }
        }
    };

    ImpRequestLogi mRequestLogic;

    void testApi() {
//        mHandler.sendEmptyMessageDelayed(1, 450);
        mRequestLogic.perform(
                new UnifyResponse(getActivity()) {
                    @Override
                    public void onRequestResponse(OkHttpRequest request) {
                        try {
                            LogUtil.e("------->> 模拟耗时操作 1.5s");
                            Thread.sleep(1500); /*模拟耗时操作*/

                            OkHttpRequest reqeust = (OkHttpRequest) request.getNext();
                            UrlFormat urlFormat = new UrlFormat("http://www.weather.com.cn/data/cityinfo/101190408.html");
                            urlFormat.addQuery("r", MessageFormat.format("修改来自 - ID:{0}", reqeust.getId()));
                            reqeust.setApi(urlFormat.toUrl());

                        } catch (Exception e) {
                        }
                    }
                }
                , getApiserver().getWeatherForecast()
                , getApiserver().getWeatherForecast()
                , getApiserver().getWeatherForecast());
    }


    DemoEntrance mDemoEntrance = new DemoEntrance("接口请求测试") {
        @Override
        public void enter() {
            testApi();
        }
    };

    DemoEntrance mDemoCanleReqeust = new DemoEntrance("取消请求") {
        @Override
        public void enter() {
//            getReqeustManager().cancelRequest();
            mRequestLogic.shutdown();
        }
    };

    /*Demo入口*/
    public static final class Demo extends DemoEntrance {
        IAppFramework appfk;

        public Demo(IAppFramework appfk) {
            super("网络请求");
            this.appfk = appfk;
        }

        @Override
        public void enter() {
            Demo_NetRequest.start(appfk);
        }
    }
}
