package com.lfp.ardf.module.net.i;


/**
 * 合并请求,并发调用<br/>
 * Created by LiFuPing on 2018/6/12.
 */
public abstract class MergeRequest extends IRequest {

    public MergeRequest(IRequest... reqeust_array) {

    }

    public MergeRequest(Iterable<IRequest> reqeust_array) {

    }

    @Override
    public void start() {

    }

    @Override
    protected void call() throws Exception {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void setId(int id) {
        super.setId(id);

    }

    @Override
    public int length() {
        return super.length();
    }
}
