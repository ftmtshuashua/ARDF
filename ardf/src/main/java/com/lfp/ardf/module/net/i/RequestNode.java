package com.lfp.ardf.module.net.i;

import com.lfp.ardf.exception.MsgException;
import com.lfp.ardf.module.net.imp.RequestCall;
import com.lfp.ardf.module.net.imp.RequestChain;
import com.lfp.ardf.module.net.imp.RequestMerge;

/**
 * 请求节点
 * <p>
 * 单纯的节点数据结构,它的具体逻辑需要子类来完成,参考
 * <p>
 * {@link ( RequestCall )} 请求发起者 - 发起请求并回复请求结果
 * <p>
 * {@link ( RequestChain )} 请求链条 - 将请求节点串成一个链条,顺序执行
 * <p>
 * {@link ( RequestMerge )} 合并请求 - 将请求节点并行执行
 * <p>
 * Created by LiFuPing on 2018/6/8.
 */
public abstract class RequestNode {

    /*用户自己分配当ID*/
    private static final int FLAG_USERS_ASSIGNED_ID = 0x1;

    /**
     * 请求节点监听器.
     * 当节点状态改变时,通过notify方法通知监听者
     */
    private RequestListener monitor;

    /**
     * 下一个节点,
     * 多个节点链接成一个有顺序的链条
     */
    private RequestNode next;

    /**
     * 节点ID,默认情况下它当值为节点传入顺序当下标
     * <p>
     * 每个节点都包含一个独立的/顺序的/稳定的ID,在节点状态变化的时候通过这个ID很容定位状态变化当节点
     * <p>
     * 空节点也应该消耗一个ID,来保证ID不会随着节点边空而改变
     */
    private int id;
    private int myflag;

    /**
     * 设置这个节点当监听器,它用来获取节点节点状态
     *
     * @param l 监听器
     */
    public void setRequestListener(RequestListener l) {
        monitor = l;
    }

    /**
     * 分配唯一的ID给节点,通过节点ID来区分回复节点
     * <p>
     * 注意默认情况下系统分配 ID 是从 0 开始 +1 递增,所以手动设置当时候尽量使用一个比较大大值
     *
     * @param id
     */
    public void setId(int id) {
        if (id < 0) throw new MsgException("ID不满足条件 ID >= 0 !");
        myflag |= FLAG_USERS_ASSIGNED_ID;
        this.id = id;
    }

    /**
     * 在用户没有手动分配ID当情况下,请求链会自动为节点分配一个与执行顺序相同当ID
     *
     * @param index
     */
    public void indexId(int index) {
        if ((myflag & FLAG_USERS_ASSIGNED_ID) != 0) return;
        if (index < 0) throw new MsgException("ID不满足条件 ID >= 0 !");
        this.id = index;
    }

    /**
     * 节点唯一标示
     *
     * @return 节点ID
     */
    public int getId() {
        return id;
    }

    /**
     * 通过它来给请求链分配正确当ID
     *
     * @return 表示节点的长度
     */
    public int length() {
        return 1;
    }

    /**
     * 设置需要与当前节点关联的下一个节点
     *
     * @param reqeust 节点
     */
    public void setNext(RequestNode reqeust) {
        next = reqeust;
    }

    /**
     * @return 当前节点后面时候还有节点
     */
    public boolean hasNext() {
        return next != null;
    }

    /**
     * @return 下一个节点
     */
    public RequestNode getNext() {
        return next;
    }

    /**
     * 开始执行节点流程
     */
    public abstract void start();

    /**
     * 关闭节点流程,被关闭当节点不能重新执行
     */
    public abstract void shutdown();

    /**
     * 当调用shutdown() 方法之后,它应该返回True
     *
     * @return 节点流程是否关闭
     */
    public abstract boolean isShutdown();


    protected void notifyStart() {
        notifyStart(this);
    }

    protected void notifyStart(RequestNode request) {
        if (monitor != null) monitor.onStart(request);
    }

    protected void notifyError(Throwable e) {
        notifyError(this, e);
    }

    protected void notifyError(RequestNode request, Throwable e) {
        if (monitor != null && !isShutdown()) monitor.onError(request, e);
    }

    protected void notifyResponse() {
        notifyResponse(this);
    }

    protected void notifyResponse(RequestNode request) {
        if (monitor != null && !isShutdown()) monitor.onResponse(request);
    }

    protected void notifyComplete() {
        notifyComplete(this);
    }

    protected void notifyComplete(RequestNode request) {
        if (monitor != null && !isShutdown()) monitor.onComplete(request);
    }

    protected void notifyEnd() {
        notifyEnd(this);
    }

    protected void notifyEnd(RequestNode request) {
        if (monitor != null) monitor.onEnd(request);
    }


}
