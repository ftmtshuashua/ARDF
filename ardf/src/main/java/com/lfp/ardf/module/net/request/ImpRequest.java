package com.lfp.ardf.module.net.request;

/**
 * 请求实现<br/>
 * Created by LiFuPing on 2018/5/28.
 */
public abstract class ImpRequest implements IRequest {

    /**
     * 请求ID，用于区别请求,获取请求结果的时候可以通过ID来判断是哪个请求
     */
    int id;

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

}
