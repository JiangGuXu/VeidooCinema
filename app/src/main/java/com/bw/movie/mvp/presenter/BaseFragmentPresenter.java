package com.bw.movie.mvp.presenter;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.NetBroadCastReciver;

/**
 *
 * author:赵瑜峰
 * date:2018/11/27
 */
public abstract class BaseFragmentPresenter<T extends AppDelage> extends Fragment {
    protected T daleagt;
    private boolean isRegistered = false;
    private NetBroadCastReciver netBroadCastReciver;

    public abstract Class<T> getClassPresenter();
    public BaseFragmentPresenter(){
        try {
            daleagt = getClassPresenter().newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        daleagt.create(getLayoutInflater(),container,savedInstanceState);
        return daleagt.view();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        daleagt.getContext(getActivity());
        daleagt.initData();
        netBroadCastReciver = new NetBroadCastReciver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(netBroadCastReciver, filter);
        isRegistered = true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        daleagt.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        daleagt.destry();
        daleagt=null;
        if (isRegistered) {
            getActivity().unregisterReceiver(netBroadCastReciver);
        }
    }

}
