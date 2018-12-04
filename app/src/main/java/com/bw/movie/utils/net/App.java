package com.bw.movie.utils.net;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.StrictMode;

import com.facebook.drawee.backends.pipeline.Fresco;

public class App extends Application {
    @SuppressLint("NewApi")
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
}
