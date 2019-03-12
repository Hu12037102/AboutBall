package com.work.guaishouxingqiu.aboutball.http;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.FileUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.NetWorkUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 14:54
 * 更新时间: 2019/3/4 14:54
 * 描述: 网络管理类
 */
public class RetrofitManger {
    private static final int MAX_CACHE_SIZE = 20 * 1024 * 1024;
    private static final int READ_TIME_OUT = 15 * 1000;
    private static final int CONNECT_TIME_OUT = 10 * 1000;
    private Retrofit mRetrofit;

    private RetrofitManger() {
        initRetrofit();
    }

    public <T> T create(Class<T> clazz) {
        return DataUtils.checkData(mRetrofit.create(clazz));
    }

    private static RetrofitManger mRetrofitManger;

    public static RetrofitManger getDefault() {
        synchronized (RetrofitManger.class) {
            if (mRetrofitManger == null) {
                synchronized (RetrofitManger.class) {
                    mRetrofitManger = new RetrofitManger();
                }
            }
        }
        return mRetrofitManger;
    }

    private void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(IApi.BASE_URL)
                .client(createOKHttp())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient createOKHttp() {
        return new OkHttpClient.Builder()
               // .addInterceptor(mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(new Cache(FileUtils.getNetCachFile(), RetrofitManger.MAX_CACHE_SIZE))
                .addInterceptor(new NetCacheInterceptor())
                .addInterceptor(new HeadInterceptor())
                .readTimeout(RetrofitManger.READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(RetrofitManger.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .build();
    }

    private  final  HttpLoggingInterceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
        LogUtils.w("HttpLoggingInterceptor--",message);
    });

    /**
     * 网络头部请求参数
     */
    public static class HeadInterceptor implements Interceptor {

        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();

            request = request.newBuilder()
                    .header("Authorization", UserManger.get().getToken())
                    .build();
            return chain.proceed(request);
        }
    }

    /**
     * 加入网络缓存拦截器
     */
    public static class NetCacheInterceptor implements Interceptor {
        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            if (NetWorkUtils.isNetCanUse()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            } else {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if (NetWorkUtils.isNetCanUse()) {
                response = response.newBuilder().removeHeader("Pragma")
                        .header("Cache-Control", "public,max-age=" + 60 * 60).build();
            } else {
                response = response.newBuilder().removeHeader("Pragma")
                        .header("Cache-Control", "public,only-if-cached,max-stale=" + 7 * 24 * 60 * 60).build();
            }
            return response;
        }
    }
}
