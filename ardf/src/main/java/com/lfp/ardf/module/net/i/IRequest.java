package com.lfp.ardf.module.net.i;

/**
 * 无状态请求发起者
 * <p>
 * Created by LiFuPing on 2018/6/8.
 */
public abstract class IRequest {
    IRequestMonitor monitor;
    IRequest mext;
    int id;


    /**
     * 开始这个请求
     */
    public void start() {
        if (monitor != null) monitor.onStart();
        call();
    }

    /**
     * 这个方法用来给子类实现,不同当请求方式当实现是不相同的.
     * <p>
     * 如果设置链monitor.
     * 在开始它之前会发送一个onStart信号,
     * 完成之后会发送一个onEnd信号
     * <p>
     * 所以call方法不应该是异步的,这会导致onEnd信号发送之后,请求实际上是未完成的
     */
    protected abstract void call();

    /**
     * 设置这个请求当监测器,它用来获取请求当开始与结束状态
     *
     * @param l 监测器
     */
    public void setMonitor(IRequestMonitor l) {
        monitor = l;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNextRequest(IRequest reqeust) {
        mext = reqeust;
    }

    public boolean hasNextRequest() {
        return mext != null;
    }

    public IRequest getNextRequest() {
        return mext;
    }


    /*关闭请求*/
    public abstract void shutdown();
}
