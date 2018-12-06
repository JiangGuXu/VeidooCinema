package com.bw.movie.utils.net;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.StrictMode;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class App extends Application {
    private static App mainApp;

    public static IWXAPI mWxApi;
    public static App getMainApp(){
        mainApp = new App();
        return mainApp;
    }
    @SuppressLint("NewApi")
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        mWxApi = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516", false);
        // 将该app注册到微信
        mWxApi.registerApp("wxb3852e6a6b7d9516");

    }
}
