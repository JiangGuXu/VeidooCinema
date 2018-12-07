package com.bw.movie.presenter_activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.fragment.CinemaFragment;
import com.bw.movie.fragment.FilmFragment;
import com.bw.movie.fragment.MyFragment;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.bw.movie.utils.net.NetBroadCastReciver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 底部Tab导航键
 * 姜谷蓄
 * 2018-11-27
 */

public class MainActivityPresenter extends AppDelage implements View.OnClickListener {
    private FrameLayout main_frame;
    private ImageView main_img_01, main_img_02, main_img_03;
    private FilmFragment filmFragment;
    private MyFragment myFragment;
    private CinemaFragment cinemaFragment;
    private List<ImageView> list = new ArrayList<>();
    private FragmentManager manager;
    private int width;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private Context context;

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();
        MainActivity activity = (MainActivity) this.context;
        main_frame = get(R.id.main_frame);
        main_img_01 = get(R.id.main_img_01);
        main_img_02 = get(R.id.main_img_02);
        main_img_03 = get(R.id.main_img_03);
        filmFragment = new FilmFragment();
        myFragment = new MyFragment();
        cinemaFragment = new CinemaFragment();
        addList();
        Display display = activity.getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        for (int i = 0; i < list.size(); i++) {
            setSizeMin(list.get(i));
        }
        setSizeMax(main_img_01);
        manager = activity.getSupportFragmentManager();
        manager.beginTransaction().add(R.id.main_frame, myFragment).commit();
        manager.beginTransaction().add(R.id.main_frame, cinemaFragment).commit();
        manager.beginTransaction().add(R.id.main_frame, filmFragment).commit();
        main_img_01.setOnClickListener(this);
        main_img_02.setOnClickListener(this);
        main_img_03.setOnClickListener(this);
        main_img_01.callOnClick();
        //判断用户是否登录成功
        if (SharedPreferencesUtils.getBoolean(context,"isLogin")){
            //开启debug日志数据
            XGPushConfig.enableDebug(context,true);
            //信鸽token注册
            XGPushManager.registerPush(context, new XGIOperateCallback() {
                @Override
                public void onSuccess(Object data, int flag) {
                    //token在设备卸载重装的时候有可能会变
                    Log.d("TPush", "注册成功，设备token为：" + data);
                    XGPushManager.bindAccount(context, "XINGE");
                    XGPushManager.setTag(context,"XINGE");
                    uploadXgInfo(data);
                }
                @Override
                public void onFail(Object data, int errCode, String msg) {
                    Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
                }
            });
        }
    }

    //将信鸽token上传到服务端
    private void uploadXgInfo(Object data) {
        Map<String,String> headMap = new HashMap<>();
        headMap.put("userId", String.valueOf(SharedPreferencesUtils.getInt(context,"userId")));
        headMap.put("sessionId",SharedPreferencesUtils.getString(context,"sessionId"));
        headMap.put("Content-Type","application/x-www-form-urlencoded");
        Map<String,String> bodyMap = new HashMap<>();
        bodyMap.put("token",String.valueOf(data));
        bodyMap.put("os","1");
        //请求接口
        new HttpUtil().postHead("/movieApi/tool/v1/verify/uploadPushToken",bodyMap,headMap).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                RegisterBean bean = new Gson().fromJson(data, RegisterBean.class);
            }

            @Override
            public void fail(String data) {
                RegisterBean bean = new Gson().fromJson(data, RegisterBean.class);
            }
        });
    }

    private void addList() {
        list.add(main_img_01);
        list.add(main_img_02);
        list.add(main_img_03);
    }

    //设置图片变大时的宽高
    public void setSizeMax(ImageView imageView) {
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.height = 70 * (width / 2) / 160;
        params.width = 70 * (width / 2) / 160;
        imageView.setLayoutParams(params);
    }

    //设置图片变小时的宽高
    public void setSizeMin(ImageView imageView) {
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        //1dp*像素密度/160 = 实际像素数   dp转换px公式
        params.height = 55 * (width / 2) / 160;
        params.width = 55 * (width / 2) / 160;
        imageView.setLayoutParams(params);
    }

    //点击切换
    @Override
    public void onClick(View v) {
        for (int i = 0; i < list.size(); i++) {
            setSizeMin(list.get(i));
        }
        switch (v.getId()) {
            case R.id.main_img_01:
                setSizeMax(main_img_01);
                main_img_01.setImageResource(R.drawable.com_icon_film_selected);
                main_img_02.setImageResource(R.drawable.com_icon_cinema_default);
                main_img_03.setImageResource(R.drawable.com_icon_my_default);
                manager.beginTransaction().show(filmFragment).commit();
                manager.beginTransaction().hide(myFragment).commit();
                manager.beginTransaction().hide(cinemaFragment).commit();
                break;
            case R.id.main_img_02:
                setSizeMax(main_img_02);
                main_img_01.setImageResource(R.drawable.com_icon_film_fault);
                main_img_02.setImageResource(R.drawable.com_icon_cinema_selected);
                main_img_03.setImageResource(R.drawable.com_icon_my_default);
                manager.beginTransaction().show(cinemaFragment).commit();
                manager.beginTransaction().hide(myFragment).commit();
                manager.beginTransaction().hide(filmFragment).commit();
                break;
            case R.id.main_img_03:
                setSizeMax(main_img_03);
                main_img_01.setImageResource(R.drawable.com_icon_film_fault);
                main_img_02.setImageResource(R.drawable.com_icon_cinema_default);
                main_img_03.setImageResource(R.drawable.com_icon_my_selected);
                manager.beginTransaction().show(myFragment).commit();
                manager.beginTransaction().hide(cinemaFragment).commit();
                manager.beginTransaction().hide(filmFragment).commit();
                break;
        }
    }
    private void setBreoadcast() {
        BroadcastReceiver receiver=new NetBroadCastReciver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiver,filter);
    }

    public void initnetword(Bundle savedInstanceState) {
    }
}

