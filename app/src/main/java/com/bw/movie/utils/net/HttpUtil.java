package com.bw.movie.utils.net;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 *
 * author:赵瑜峰
 * date:2018/11/27
 * 封装网络工具类
 *
 */

public class HttpUtil {

    private final BaseService baseService;
    private Observable<ResponseBody> observable;

    public HttpUtil() {
        //拦截器
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                RequestBody body = request.body();
                String method = request.method();
                Log.i("HttpUtil", "intercept:" + body + "====" + method);
                return chain.proceed(request);
            }
        }).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.8.100/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        baseService = retrofit.create(BaseService.class);
    }

    //get请求
    public HttpUtil get(String url, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        observable = baseService.get(url, map);
        setObservable();
        return this;
    }

    //post请求
    public HttpUtil post(String url, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        observable = baseService.post(url, map);
        setObservable();
        return this;
    }

    //上传文件
    public HttpUtil part(String url, Map<String, String> map, MultipartBody.Part part) {
        if (map == null) {
            map = new HashMap<>();
        }
        observable = baseService.part(url, map, part);
        setObservable();
        return this;
    }

    //被观察者订阅观察者
    private void setObservable() {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    //观察者
    private Observer observer = new Observer<ResponseBody>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String string = responseBody.string();
                listener.success(string);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            String message = e.getMessage();
            listener.fail(message);
        }

        @Override
        public void onComplete() {

        }
    };
    //接口回调
    private HttpListener listener;

    public void result(HttpListener listener) {
        this.listener = listener;
    }

    public interface HttpListener {
        void success(String data);

        void fail(String data);
    }
}
