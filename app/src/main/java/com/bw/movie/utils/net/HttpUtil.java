package com.bw.movie.utils.net;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.utils.cache.CacheUtils;

import java.io.File;
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
 * author:赵瑜峰
 * date:2018/11/27
 * 封装网络工具类
 */

public class HttpUtil {

    private final BaseService baseService;
    private final LodingDialog lodingDialog;
    private Observable<ResponseBody> observable;
    private Context context;
    private String type;
    private boolean isloading;
    private boolean iscache;

    public HttpUtil(Context context) {
        this.context = context;
        LodingDialog.Builder builder = new LodingDialog.Builder(context);
        lodingDialog = builder.create();

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
                .baseUrl("http://mobile.bwstudent.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        baseService = retrofit.create(BaseService.class);
    }

    //get请求
    public HttpUtil get(String url, Map<String, String> map, Map<String, String> mapHead,String type, boolean isloading, boolean iscache) {
        this.type = type;
        this.isloading = isloading;
        this.iscache = iscache;
        if (NetworkUtils.isConnected(context)) {
            if (isloading) {
                lodingDialog.show();
            }
            if (map == null) {
                map = new HashMap<>();
            }
            if (mapHead == null) {
                mapHead = new HashMap<>();
            }
            observable = baseService.get(url, map, mapHead);
            setObservable();
        } else {
            if (iscache) {
                String query = CacheUtils.getCacheUtils().query(type);
                if (query != null) {
                    listener.success(query);
                    Toast.makeText(context, "没有网络", Toast.LENGTH_SHORT).show();
                } else {
                    View inflate = View.inflate(context, R.layout.not_network, null);
                    listener.notNetwork(inflate);
                }
            } else {
                View inflate = View.inflate(context, R.layout.not_network, null);
                listener.notNetwork(inflate);
            }
        }
            return this;

    }
    //post请求
    public HttpUtil post(String url,Map<String, String> map,String type, boolean isloading, boolean iscache) {
            this.type = type;
            this.isloading = isloading;
            this.iscache = iscache;
            if (NetworkUtils.isConnected(context)) {
                if (isloading) {
                    lodingDialog.show();
                }
                if (map == null) {
                    map = new HashMap<>();
                }
                observable = baseService.post(url, map);
                setObservable();
            } else {
                if(iscache){
                    String query = CacheUtils.getCacheUtils().query(type);
                    if (query != null) {
                        listener.success(query);
                        Toast.makeText(context,"没有网络",Toast.LENGTH_SHORT).show();
                    } else {
                        View inflate = View.inflate(context, R.layout.not_network, null);
                        listener.notNetwork(inflate);
                    }
                }else{
                    View inflate = View.inflate(context, R.layout.not_network, null);
                    listener.notNetwork(inflate);
                }
        }


        return this;
    }

    //postHead请求
    public HttpUtil postHead(String url, Map<String, String> map, Map<String, String> mapHead,String type, boolean isloading, boolean iscache) {
        this.type = type;
        this.isloading = isloading;
        this.iscache = iscache;
        if (NetworkUtils.isConnected(context)) {
            if (isloading) {
                lodingDialog.show();
            }
            if (map == null) {
                map = new HashMap<>();
            }
            if (mapHead == null) {
                mapHead = new HashMap<>();
            }
            observable = baseService.postHead(url, map, mapHead);
            setObservable();
        } else {
            if(iscache){
                String query = CacheUtils.getCacheUtils().query(type);
                if (query != null) {
                    listener.success(query);
                    Toast.makeText(context,"没有网络",Toast.LENGTH_SHORT).show();
                } else {
                    View inflate = View.inflate(context, R.layout.not_network, null);
                    listener.notNetwork(inflate);
                }
            }else{
                View inflate = View.inflate(context, R.layout.not_network, null);
                listener.notNetwork(inflate);
            }
        }


        return this;
    }

    //postHead请求
    public HttpUtil postUploadImage(String url, Map<String, File> map, Map<String, String> mapHead,String type, boolean isloading, boolean iscache) {
        this.type = type;
        this.isloading = isloading;
        this.iscache = iscache;
        if (NetworkUtils.isConnected(context)) {
            if (isloading) {
                lodingDialog.show();
            }
            if (map == null) {
                map = new HashMap<>();
            }
            if (mapHead == null) {
                mapHead = new HashMap<>();
            }
            observable = baseService.postUploadImage(url, map, mapHead);
            setObservable();
        } else {
            if(iscache){
                String query = CacheUtils.getCacheUtils().query(type);
                if (query != null) {
                    listener.success(query);
                    Toast.makeText(context,"没有网络",Toast.LENGTH_SHORT).show();
                } else {
                    View inflate = View.inflate(context, R.layout.not_network, null);
                    listener.notNetwork(inflate);
                }
            }else{
                View inflate = View.inflate(context, R.layout.not_network, null);
                listener.notNetwork(inflate);
            }
        }

        return this;
    }

    //postHead请求
    public HttpUtil postForm(String url, Map<String, String> map, Map<String, String> mapForm, Map<String, String> mapHead,String type, boolean isloading, boolean iscache) {
        this.type = type;
        this.isloading = isloading;
        this.iscache = iscache;
        if (NetworkUtils.isConnected(context)) {
            if (isloading) {
                lodingDialog.show();
            }
            if (map == null) {
                mapHead = new HashMap<>();
            }
            if (map == null) {
                mapForm = new HashMap<>();
            }
            if (mapHead == null) {
                mapHead = new HashMap<>();
            }
            observable = baseService.postForm(url, map, mapForm, mapHead);
            setObservable();
        } else {
            if(iscache){
                String query = CacheUtils.getCacheUtils().query(type);
                if (query != null) {
                    listener.success(query);
                    Toast.makeText(context,"没有网络",Toast.LENGTH_SHORT).show();
                } else {
                    View inflate = View.inflate(context, R.layout.not_network, null);
                    listener.notNetwork(inflate);
                }
            }else{
                View inflate = View.inflate(context, R.layout.not_network, null);
                listener.notNetwork(inflate);
            }
        }


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
                String data = responseBody.string();
                lodingDialog.dismiss();
                if (iscache) {
                    CacheUtils.getCacheUtils().insert(data, type);
                }
                listener.success(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            String error = e.getMessage();
            lodingDialog.dismiss();
            listener.fail(error);
        }

        @Override
        public void onComplete() {

        }
    };
    //接口回调
    private HttpListener listener;

    public HttpUtil result(HttpListener listener) {
        this.listener = listener;
        return this;
    }

    public interface HttpListener {
        void success(String data);

        void fail(String data);

        void notNetwork (View data);
    }
}
