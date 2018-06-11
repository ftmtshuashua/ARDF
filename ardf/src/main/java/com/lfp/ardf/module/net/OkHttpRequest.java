package com.lfp.ardf.module.net;

import com.lfp.ardf.exception.NetStateException;
import com.lfp.ardf.module.net.i.IRequest;
import com.lfp.ardf.module.net_deprecated.util.UrlFormat;

import java.text.MessageFormat;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <br/>
 * Created by LiFuPing on 2018/6/11.
 */
public class OkHttpRequest extends IRequest {

    static final int FLAG_RUNING = 0x1;
    static final int FLAG_SHUTDOWN = 0x1 << 1;

    String api;

    Call mCall;
    Disposable mDisposable;

    int flag;

    public OkHttpRequest(String api) {
        setApi(api);
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getApi() {
        return api;
    }

    @Override
    protected void call() {
        if (isShutdown()) return;

        Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> emitter) throws Exception {
                try {
                    Request request = new Request.Builder().url(new UrlFormat(api).toEncodeUrl()).build();

                    mCall = OkHttpRequestClient.getInstance().mHttpClient.newCall(request);
                    Response response = mCall.execute();

                    if (!response.isSuccessful())
                        throw new NetStateException(MessageFormat.format("{0}{1}", response.code(), response.message()));

                } catch (Exception e) {
                    if (!emitter.isDisposed()) throw e;
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Void>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        flag |= FLAG_RUNING;
                        mDisposable = d;
                        notifyStart();
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        notifyNext();
                        notifyEnd();
                    }

                    @Override
                    public void onError(Throwable e) {
                        flag &= ~FLAG_RUNING;
                        notifyError(e);
                        shutdown();
                        notifyEnd();
                    }

                    @Override
                    public void onComplete() {
                        flag &= ~FLAG_RUNING;
                        notifyComplete();
                        shutdown();
                    }
                });


        notifyComplete();
    }

    @Override
    public void shutdown() {
        flag |= FLAG_SHUTDOWN;
        if ((flag & FLAG_RUNING) != 0) {
            mCall.cancel();
            mDisposable.dispose();
        }
    }

    public boolean isShutdown() {
        return (flag & FLAG_SHUTDOWN) != 0;
    }
}
