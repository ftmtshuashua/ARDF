package com.lfp.androidrapiddevelopmentframework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.demo.ADemo_BaseRecyclerViewAdapter;
import com.lfp.androidrapiddevelopmentframework.demo.DemoEntrance;
import com.lfp.ardf.adapter.BaseRecyclerViewAdapter;
import com.lfp.ardf.adapter.SimpleRecyclerViewAdapter;
import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.module.net.OkHttpRequest;
import com.lfp.ardf.module.net.OkHttpResponse;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Demo列表
 * Created by LiFuPing on 2018/5/9.
 */
public class MainActivity extends BaseActivity {
    SimpleRecyclerViewAdapter<DemoEntrance> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = findViewById(R.id.view_ReyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter = new SimpleRecyclerViewAdapter(VHolder.class, R.layout.activity_main_listitem));

        /*配置demo*/
        List<DemoEntrance> arrays = new ArrayList<>();
        arrays.add(new ADemo_BaseRecyclerViewAdapter.Demo("RecyclerViewAdapter", getAppFk()));
        arrays.add(mDemoEntrance);

        mAdapter.setAndUpdata(arrays);

        try {
            LogUtil.e(MessageFormat.format("测试设备：{0}", StatConfig.getMid(getActivity())));
            StatService.startStatService(getApplicationContext(), null, com.tencent.stat.common.StatConstants.VERSION);
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    private static final class VHolder extends BaseRecyclerViewAdapter.BaseViewHolder<DemoEntrance> implements View.OnClickListener {
        TextView mTV_Info;

        public VHolder(View itemView) {
            super(itemView);
            mTV_Info = itemView.findViewById(R.id.view_Info);
            mTV_Info.setOnClickListener(this);
        }

        @Override
        public void onUpdateUI(DemoEntrance data) {
            mTV_Info.setText(data.getInfo());
        }

        @Override
        public void onClick(View v) {
            getSaveData().enter();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
//        test();

    }


    int i = 0;

    void test() {
        if (i > 2) return;
        LogUtil.e(MessageFormat.format("---------------- 测试 {0} ----------------", i));
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(MessageFormat.format("这是发送数据:{0}", i));
                emitter.onComplete();
            }
        }).subscribe(new Observer<String>() {

            String info;

            @Override
            public void onSubscribe(Disposable d) {
                LogUtil.e("subscribe ------> onSubscribe");
            }

            @Override
            public void onNext(String s) {
                info = s;
                LogUtil.e("subscribe ------> onNext:" + info);
                i++;
                test();
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("subscribe ------> onError" + info);
            }

            @Override
            public void onComplete() {
                LogUtil.e("subscribe ------> onComplete" + info);
            }
        });
    }

    void testApi() {
        request(new OkHttpResponse() {

                    @Override
                    public void onChainStart() {
                        super.onChainStart();
                        LogUtil.d("OkHttpResponse -  onStart()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtil.d(MessageFormat.format("OkHttpResponse onError({1})", e.getMessage()));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        LogUtil.d("OkHttpResponse  -  onComplete()");
                    }

                    @Override
                    public void onDataProcess(int id, OkHttpRequest request) {
                        super.onDataProcess(id, request);
                        LogUtil.d(MessageFormat.format("OkHttpResponse Request[{0}]  -  onDataProcess()", id));
                    }

                    @Override
                    public void onResponse(int id, OkHttpRequest request) {
                        super.onResponse(id, request);
                        try {
                            String body = request.getResponse().body().string();
                            LogUtil.d(MessageFormat.format("OkHttpResponse Request[{0}]  -  onResponse():{1}", id, body));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onChainEnd() {
                        super.onChainEnd();
                        LogUtil.d("OkHttpResponse  -  onChainEnd()");
                    }
                }
                , getApiserver().getWeatherForecast()
                , getApiserver().getWeatherForecast().setIgnoreResponse(true)
                , getApiserver().getWeatherForecast()
        );

    }


    DemoEntrance mDemoEntrance = new DemoEntrance("接口请求测试") {
        @Override
        public void enter() {
            testApi();
        }
    };


}
