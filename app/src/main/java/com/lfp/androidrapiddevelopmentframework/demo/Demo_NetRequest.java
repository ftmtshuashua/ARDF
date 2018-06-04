package com.lfp.androidrapiddevelopmentframework.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.activity.module.home.fragment.DemoFragment;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.net.UnifyResponse;
import com.lfp.ardf.adapter.SimpleRecyclerViewAdapter;
import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.module.net.OkHttpRequest;
import com.lfp.ardf.module.net.client.OkHttpReqeuestClient;
import com.lfp.ardf.module.net.logic.ChainRequestLogic;
import com.lfp.ardf.module.net.logic.ImpRequestLogi;
import com.lfp.ardf.module.net.logic.ParallelRequestLogic;
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

    TextView mTV_Info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("网络请求");
        mTV_Info = findViewById(R.id.view_Info);


        RecyclerView mRecyclerView = findViewById(R.id.view_ReyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter = new SimpleRecyclerViewAdapter(DemoFragment.DemoEntranceHolder.class, R.layout.layout_simpler_textview));


        List<DemoEntrance> arrays = new ArrayList<>();
        arrays.add(mDemoEntrance2);
        arrays.add(mDemoEntrance);
        arrays.add(mDemoCanleReqeust);
        mAdapter.setAndUpdata(arrays);


        mRequestLogic_Chain = new ChainRequestLogic();
        mRequestLogic_Chain.setAppFramework(getAppFk());
        mRequestLogic_Chain.setRequestClient(OkHttpReqeuestClient.getDefualt());

        mRequestLogic_Parallel = new ParallelRequestLogic();
        mRequestLogic_Parallel.setAppFramework(getAppFk());
        mRequestLogic_Parallel.setRequestClient(OkHttpReqeuestClient.getDefualt());
    }

    ImpRequestLogi mRequestLogic_Chain;
    ImpRequestLogi mRequestLogic_Parallel;


    DemoEntrance mDemoEntrance = new DemoEntrance("链式请求测试") {
        @Override
        public void enter() {
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
    };

    DemoEntrance mDemoEntrance2 = new DemoEntrance("并行请求测试") {
        @Override
        public void enter() {
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
    };
    DemoEntrance mDemoCanleReqeust = new DemoEntrance("取消请求") {
        @Override
        public void enter() {
            mRequestLogic_Chain.shutdown();
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
