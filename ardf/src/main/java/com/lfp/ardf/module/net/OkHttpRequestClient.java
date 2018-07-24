package com.lfp.ardf.module.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp请求客户端 <br>
 * Created by LiFuPing on 2018/6/11.
 */
public class OkHttpRequestClient {
    private static OkHttpRequestClient mInstance;
    OkHttpClient mHttpClient;

    public OkHttpRequestClient() {
        mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.MINUTES)
                .cookieJar(new CookieHolper())
                .addNetworkInterceptor(new DelectUserAgent())
                .build();
    }


    public static final OkHttpRequestClient getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpRequestClient.class) {
                if (mInstance == null) mInstance = new OkHttpRequestClient();
            }
        }

        return mInstance;
    }


    /*Cookie*/
    private static final class CookieHolper implements CookieJar {

        private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url, cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            if (cookies == null) cookies = new ArrayList<>();

            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    }


    /*删除UserAgent*/
    public class DelectUserAgent implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            request = request.newBuilder().removeHeader("User-Agent").build();

            Response response = chain.proceed(request);
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            return response.newBuilder().body(okhttp3.ResponseBody.create(mediaType, content)).build();
        }
    }


}
