package com.bw.movie.mvp.presenter;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.activity.SplashActivity;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.UltimateBar;
import com.bw.movie.utils.net.NetBroadCastReciver;
import com.bw.movie.utils.net.NetworkUtils;

/**
 *
 * author:赵瑜峰
 * date:2018/11/27
 */
public abstract class BaseActivityPresenter<T extends AppDelage> extends AppCompatActivity {
    private boolean isRegistered = false;
    public T daleagt;
    private NetBroadCastReciver netBroadCastReciver;
    private View view;

    public abstract Class<T> getClassPresenter();
    public BaseActivityPresenter(){
        try {
            daleagt = getClassPresenter().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daleagt.create(getLayoutInflater(),null,savedInstanceState);
        setContentView(daleagt.view());
        daleagt.getContext(this);
        daleagt.initData();
        UltimateBar.newImmersionBuilder().applyNav(false).build(this).apply();
        if(this instanceof SplashActivity){

        }else {
//            view = View.inflate(this, R.layout.not_network, null);
//            view.findViewById(R.id.film_retry_isnetword).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    isNetwork();
//                }
//            });
            netBroadCastReciver = new NetBroadCastReciver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
            filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netBroadCastReciver, filter);
            isRegistered = true;
        }
    }
    @Override
    public void onResume() {
        super.onResume();

//        if(this instanceof SplashActivity){
//            return;
//        }else {
//            isNetwork();
//            daleagt.resume();
//        }
    }
    public void isNetwork(){
        if(!NetworkUtils.isConnected(this)){
            setContentView(view);
        }else{
            setContentView(daleagt.view());
            daleagt.successnetwork();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        daleagt.destry();
        daleagt=null;
        if (isRegistered) {
            unregisterReceiver(netBroadCastReciver);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        daleagt.restart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        daleagt.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        daleagt.onActivityResult(requestCode,resultCode,data);
    }
}
