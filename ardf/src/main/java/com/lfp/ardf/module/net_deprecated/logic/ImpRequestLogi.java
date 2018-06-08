package com.lfp.ardf.module.net_deprecated.logic;

import com.lfp.ardf.framework.I.ILifeCycleObserved;
import com.lfp.ardf.framework.util.SimpleLifeCycleObserve;
import com.lfp.ardf.module.net_deprecated.client.IRequestClient;
import com.lfp.ardf.module.net_deprecated.observer.IRequestObserver;
import com.lfp.ardf.module.net_deprecated.request.IRequest;
import com.lfp.ardf.util.CpuUtile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 请求规则基础实现类，做一些必要的基础处理和控制<br/>
 * 这个类会过滤掉空请求，然后分配请求ID。这个ID与传入顺序相关，与是否为空无关，保证一个可预测的ID<br/>
 * Created by LiFuPing on 2018/5/28.
 */
public abstract class ImpRequestLogi<R extends IRequest> implements IRequestLogic<R> {

    /*允许同时运行的线程数*/
    int maxConcurrency;
    IRequestClient<R> mIRequestClient;

    public ImpRequestLogi() {
        maxConcurrency = CpuUtile.getThreadValue(3, 10);
    }

    /**
     * @return 返回允许同时运行的线程数
     */
    public int getMaxConcurrency() {
        return maxConcurrency;
    }

    @Override
    public void setRequestClient(IRequestClient<R> client) {
        mIRequestClient = client;
    }

    @Override
    public void perform(IRequestObserver<R> observer, R... reqeust_array) {
        if (reqeust_array == null || reqeust_array.length == 0) return;
        List<R> array = new ArrayList<>();
        for (int i = 0; i < reqeust_array.length; i++) {
            R request = reqeust_array[i];
            if (request != null) {
                request.setId(i);
                array.add(request);
            }
        }
        perform(getRequestClient(), observer, array);
    }

    @Override
    public void perform(IRequestObserver<R> observer, Iterable<R> reqeust_array) {
        if (reqeust_array == null) return;
        Iterator<R> iterator = reqeust_array.iterator();
        List<R> array = new ArrayList<>();
        int index = 0;
        while (iterator.hasNext()) {
            R reqeust = iterator.next();
            reqeust.setId(index++);
            if (reqeust != null) array.add(reqeust);
        }
        if (array.isEmpty()) return;
        perform(getRequestClient(), observer, array);
    }

    /**
     * 根据客户端请求逻辑执行请求列表
     *
     * @param client        请求客户端
     * @param observer      请求观察者
     * @param request_array 请求列表
     */
    protected abstract void perform(IRequestClient<R> client, IRequestObserver<R> observer, List<R> request_array);

    /**
     * @return 返回请求客户端
     */
    public IRequestClient<R> getRequestClient() {
        return mIRequestClient;
    }

    /**
     * 绑定框架 ,以便在必要的时候对请求做处理，例如在Activity关闭的时候中断请求
     *
     * @param appfk 框架
     */
    public void setAppFramework(ILifeCycleObserved appfk) {
        appfk.registeredObserve(mContentObserve);
    }

    /**
     * 在活动页面关闭的时候应该结束所有请求
     */
    SimpleLifeCycleObserve mContentObserve = new SimpleLifeCycleObserve() {
        @Override
        public void onDestroy() {
            super.onDestroy();
            shutdown();
        }
    };

}
