package com.lfp.ardf.module.net.imp;

import com.lfp.ardf.module.net.i.RequestNode;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 请求执行者 <br/>
 * Created by LiFuPing on 2018/6/12.
 */
public abstract class RequestCall extends RequestNode {
    /*节点被关闭*/
    private static final int FLAG_SHUTDOWN = 0x1 << 8;
    /*请求已经完成并且出错*/
    private static final int FLAG_COMPLETE_ERROR = 0x1 << 4;
    /*请求已经完成并且成功*/
    private static final int FLAG_COMPLETE_SUCCESSFUL = 0x1 << 5;
    /*请求已经完成*/
    private static final int FLAG_COMPLETE_MASK = 0xF << 4;
    /*请求已初始化准备进入开始阶段*/
    private static final int CALL_PREPARE = 1;
    /*请求已开始*/
    private static final int CALL_STARTED = 3;
    /*请求已结束*/
    private static final int CALL_END = 7;
    /*请求已开始*/
    private static final int FLAG_CALL_MASK = 0xF;

    private Disposable mDisposable;
    private int myflag;

    @Override
    public void start() {
        if (isShutdown()) return;
        if (!isCalled()) {
            myflag &= ~FLAG_CALL_MASK;
            setFlag(CALL_PREPARE);

            Observable.create(new ObservableOnSubscribe<RequestNode>() {
                @Override
                public void subscribe(ObservableEmitter<RequestNode> emitter) throws Exception {
                    try {
                        if (!emitter.isDisposed()) call();
                        if (!emitter.isDisposed()) notifyResponse();
                        if (!emitter.isDisposed()) emitter.onNext(RequestCall.this);
                    } catch (Exception e) {
                        if (!emitter.isDisposed()) throw e;
                    }
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<RequestNode>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            if (isShutdown()) {
                                d.dispose();
                                return;
                            }
                            mDisposable = d;
                            setFlag(CALL_STARTED);
                            notifyStart();
                        }

                        @Override
                        public void onNext(RequestNode request) {
                            setFlag(CALL_END | FLAG_COMPLETE_SUCCESSFUL);
                            try {
                                notifyComplete();
                            } catch (Exception e) {
                                notifyError(e);
                            }
                            notifyEnd();
                        }

                        @Override
                        public void onError(Throwable e) {
                            setFlag(CALL_END | FLAG_COMPLETE_ERROR);
                            notifyError(e);
                            notifyEnd();
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
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
    protected abstract void call() throws Exception;

    @Override
    public void shutdown() {
        setFlag(FLAG_SHUTDOWN);
        if (isCalled() && !isEnd()) {
            if (mDisposable != null) {
                mDisposable.dispose();
                cancel();
            }
            if ((myflag & FLAG_CALL_MASK) == CALL_STARTED) notifyEnd();
        }
    }

    /**
     * 取消请求
     */
    protected abstract void cancel();

    @Override
    public boolean isShutdown() {
        return (myflag & FLAG_SHUTDOWN) != 0;
    }

    private void setFlag(int flag) {
        if ((flag * FLAG_COMPLETE_MASK) != 0) myflag &= ~FLAG_COMPLETE_MASK;
        myflag |= flag;
    }

    /**
     * @return 请求是否已经启动过了
     */
    public boolean isCalled() {
        return (myflag & FLAG_CALL_MASK) != 0;
    }

    /**
     * @return 请求已经结束
     */
    public boolean isEnd() {
        return (myflag & FLAG_CALL_MASK) == CALL_END;
    }

    /**
     * @return 请求执行中出错
     */
    public boolean isError() {
        return (myflag & FLAG_COMPLETE_ERROR) != 0;
    }

    /**
     * @return 请求执行正确
     */
    public boolean isSuccessful() {
        return (myflag & FLAG_COMPLETE_SUCCESSFUL) != 0;
    }

}
