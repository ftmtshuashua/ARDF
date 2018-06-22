package com.lfp.androidrapiddevelopmentframework.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.api.Apiserver;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.dialog.ProgressDelayDialog;
import com.lfp.androidrapiddevelopmentframework.event.DemoEvent;
import com.lfp.androidrapiddevelopmentframework.event.InfoEvent;
import com.lfp.androidrapiddevelopmentframework.util.ActionBarControl;
import com.lfp.ardf.adapter.SimpleRecyclerViewAdapter;
import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.dialog.BaseDialog;
import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.module.net.OkHttpRequest;
import com.lfp.ardf.module.net.i.RequestListener;
import com.lfp.ardf.module.net.i.RequestNode;
import com.lfp.ardf.module.net.imp.RequestChain;
import com.lfp.ardf.module.net.imp.RequestMerge;
import com.lfp.ardf.module.net.util.ExceptionTotalUtil;

import java.text.MessageFormat;

/**
 * 网络请求demo
 * Created by LiFuPing on 2018/5/19.
 */
public class Demo_NetRequest extends BaseActivity {
    public static final void start(IAppFramework appfk) {
        Intent intent = new Intent(appfk.getContext(), Demo_NetRequest.class);
        appfk.startActivity(intent);
    }

    static final boolean isDebug = false;

    TextView mTV_Info;
    SimpleRecyclerViewAdapter<RequestEvent> mMenuAdapter;

    RequestListener mRequestListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_netrequest);
        new ActionBarControl(getActivity())
                .setTitle("网络请求 Demo")
                .setSubTitle("并发请求/链式请求(请求500ms延时)")
                .showBack()
                .setBackFinishActivity(getActivity());

        mTV_Info = findViewById(R.id.view_Info);

        RecyclerView MenuView = findViewById(R.id.view_RecyclerViewMenu);
        MenuView.setLayoutManager(new LinearLayoutManager(getContext()));
        MenuView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        MenuView.setAdapter(mMenuAdapter = new SimpleRecyclerViewAdapter(MenuHolder.class, R.layout.layout_simpler_textview));


        mRequestListener = new RequestListener<OkHttpRequest>() {
            BaseDialog mDialog = new ProgressDelayDialog(getAppFk());
            long mTime; /*记录执行开始时间*/
            static final String partition = "<<<<<<<";

            @Override
            public void onStart(RequestNode request) {
                if (isDebug)
                    LogUtil.e(MessageFormat.format("---- onStart id:{0}", request.getId()));

                mDialog.show();
                mTime = System.currentTimeMillis();

                mTV_Info.setText("");
                mTV_Info.append(MessageFormat.format("--------->>>  Start  {0}", getThreadName()));
            }

            @Override
            public void onError(OkHttpRequest request, Throwable e) {
                if (isDebug)
                    LogUtil.e(MessageFormat.format("---- onError id:{0}  e:{1}", request.getId(), e.getMessage()));

                mTV_Info.append("\n");
                mTV_Info.append(partition);
                mTV_Info.append("\n");
                mTV_Info.append(getThreadName());
                mTV_Info.append("\n");
                mTV_Info.append(MessageFormat.format("index:{0}  -  出错了", request.getIndex()));
                mTV_Info.append(e.getMessage());
                ExceptionTotalUtil.show(e);
            }

            @Override
            public void onResponse(final OkHttpRequest request) {
                try { /*模拟网络延时*/
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                if (isDebug)
                    LogUtil.e(MessageFormat.format("---- onResponse id:{0}", request.getId()));

                final String threadName = getThreadName();
                /* 由于onResponse方法在子线程中回调,所以这里更新UI必须放到主线程*/
                mTV_Info.post(new Runnable() {
                    @Override
                    public void run() {
                        mTV_Info.append("\n");
                        mTV_Info.append(partition);
                        mTV_Info.append("\n");
                        mTV_Info.append(threadName);
                        mTV_Info.append("\n");
                        mTV_Info.append(MessageFormat.format("index:{0}  -  请求完成", request.getIndex()));
                    }
                });
            }

            @Override
            public void onComplete(OkHttpRequest request) {
                if (isDebug)
                    LogUtil.e(MessageFormat.format("---- onComplete id:{0}", request.getId()));
                mTV_Info.append("\n");
                mTV_Info.append(partition);
                mTV_Info.append("\n");
                mTV_Info.append(getThreadName());
                mTV_Info.append("\n");
                mTV_Info.append(MessageFormat.format("index:{0}  -  回复数据", request.getIndex()));
            }

            @Override
            public void onEnd(RequestNode request) {
                if (isDebug)
                    LogUtil.e(MessageFormat.format("---- onEnd id:{0}   耗时:{1}ms", request.getId(), (System.currentTimeMillis() - mTime)));

                mDialog.dismiss();


                long elapsed = System.currentTimeMillis() - mTime;
                mTV_Info.append("\n");
                mTV_Info.append(MessageFormat.format("--------->>>  end  {0}", getThreadName()));
                mTV_Info.append(MessageFormat.format("\n总计耗时:{0,number,0}ms", elapsed));

            }


        };
        mMenuAdapter.addData(new SingleRequestEvent(mRequestListener));
        mMenuAdapter.addData(new MergeRequestEvent(mRequestListener));
        mMenuAdapter.addData(new ChainRequestEvent(mRequestListener));
        mMenuAdapter.addData(new HHRequestEvent(mRequestListener));
    }

    public static String getThreadName() {
        return "Thread:" + Thread.currentThread().getName();
    }

    private static final class MenuHolder extends SimpleRecyclerViewAdapter.NotProguardViewHolder<RequestEvent> implements View.OnClickListener {
        TextView mTV_Info;

        public MenuHolder(View itemView) {
            super(itemView);
            mTV_Info = itemView.findViewById(R.id.view_Info);
            mTV_Info.setOnClickListener(this);
        }

        @Override
        public void onUpdateUI(RequestEvent data) {
            mTV_Info.setText(data.getInfo());
        }

        @Override
        public void onClick(View v) {
            getSaveData().call();
        }
    }

    private abstract static class RequestEvent extends InfoEvent {
        RequestListener mRequestListener;

        public RequestEvent(String info, RequestListener lisenter) {
            super(info);
            mRequestListener = lisenter;
        }

        public RequestListener getRequestListener() {
            return mRequestListener;
        }
    }

    /*单个请求*/
    private static final class SingleRequestEvent extends RequestEvent {

        public SingleRequestEvent(RequestListener lisenter) {
            super("单请求", lisenter);
        }

        @Override
        public void call() {
            RequestNode request = Apiserver.getNewRequest();
            request.setRequestListener(getRequestListener());
            request.start();
        }
    }

    /*并发请求*/
    private static final class MergeRequestEvent extends RequestEvent {

        public MergeRequestEvent(RequestListener lisenter) {
            super("并发请求", lisenter);
        }

        @Override
        public void call() {
            RequestMerge request = RequestMerge.request(Apiserver.getNewRequest(), Apiserver.getNewRequest());
            request.setRequestListener(mRequestListener);
            request.start();
        }
    }

    /*链式请求*/
    private static final class ChainRequestEvent extends RequestEvent {

        public ChainRequestEvent(RequestListener lisenter) {
            super("链式请求", lisenter);
        }

        @Override
        public void call() {
            RequestChain mRequestChain = RequestChain.request(Apiserver.getNewRequest(), Apiserver.getNewRequest());
            mRequestChain.setRequestListener(mRequestListener);
            mRequestChain.start();
        }
    }

    /*混合请求*/
    private static final class HHRequestEvent extends RequestEvent {

        public HHRequestEvent(RequestListener lisenter) {
            super("混合请求", lisenter);
        }

        @Override
        public void call() {
            RequestChain mRequestChain = RequestChain.request(
                    Apiserver.getNewRequest()
                    , RequestMerge.request(
                            RequestChain.request(
                                    Apiserver.getNewRequest()
                                    , Apiserver.getNewRequest())
                            , Apiserver.getNewRequest())
            );
            mRequestChain.setRequestListener(mRequestListener);
            mRequestChain.start();
        }
    }


    /*Demo入口*/
    public static final class Demo extends DemoEvent {

        public Demo(IAppFramework appfk) {
            super(appfk, "网络请求", "并发请求/链式请求解决方案");
        }

        @Override
        public void call() {
            Demo_NetRequest.start(getAppFk());
        }
    }
}
