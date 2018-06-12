package com.lfp.ardf.module.net.i;

import com.lfp.ardf.exception.MsgException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * <br/>
 * Created by LiFuPing on 2018/6/12.
 */
public abstract class SimpleRequest extends IRequest {
    /*请求出错李*/
    static final int FLAG_COMPLETE_ERROR = 0x1 << 4;
    /*请求成功了*/
    static final int FLAG_COMPLETE_SUCCESSFUL = 0x1 << 5;
    /*请求已经完成*/
    static final int FLAG_COMPLETE_MASK = 0xF << 4;


    /*这个请求已经Call*/
    static final int FLAG_CALL_MASK = 0xF;

    /*已经开始*/
    public static final int CALL_STARTED = 1;

    /*已经结束*/
    public static final int CALL_END = 3;

    Disposable mDisposable;


    int myflag;

    void checRuning() {
        if (isRuning()) throw new MsgException("当前请求正在执行,等待他执行完成之后才允许操作");
    }

    @Override
    public void start() {
        boolean isCalled = false;
        if (isCall()) {
            isCalled = true;
            checRuning();
            if (isSuccessful()) {
                notifyComplete();
                notifyEnd();
            } else if (isError()) {
                isCalled = false;
            }
        }

        if (!isCalled) {
            setFlag(CALL_STARTED);
            Observable.create(new ObservableOnSubscribe<IRequest>() {
                @Override
                public void subscribe(ObservableEmitter<IRequest> emitter) throws Exception {
                    try {
                        call();
                        notifyResponse();
                        if (!emitter.isDisposed()) emitter.onNext(SimpleRequest.this);
                    } catch (Exception e) {
                        if (!emitter.isDisposed()) throw e;
                    }
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<IRequest>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            setFlag(CALL_STARTED);
                            mDisposable = d;
                            notifyStart();
                        }

                        @Override
                        public void onNext(IRequest request) {
                            setFlag(CALL_END | FLAG_COMPLETE_SUCCESSFUL);
                            notifyComplete();
                            notifyEnd();
                        }

                        @Override
                        public void onError(Throwable e) {
                            setFlag(CALL_END | FLAG_COMPLETE_ERROR);
                            notifyError(e);
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }

    @Override
    public void setRequestListener(RequestListener l) {
        checRuning();
        super.setRequestListener(l);
    }

    @Override
    public void shutdown() {
        if (mDisposable != null) mDisposable.dispose();
        cancel();
        notifyEnd();
    }


    private void setFlag(int flag) {
        myflag |= flag;
    }

    /*已经执行过流程*/
    public boolean isCall() {
        return (myflag & FLAG_CALL_MASK) != 0;
    }

    /*已经执行结束*/
    public boolean isRuning() {
        if (isCall()) return (myflag & CALL_END) == 0;
        return false;
    }

    /*执行中出错*/
    public boolean isError() {
        return (myflag & FLAG_COMPLETE_ERROR) != 0;
    }

    /*执行正确*/
    public boolean isSuccessful() {
        return (myflag & FLAG_COMPLETE_SUCCESSFUL) != 0;
    }
}
