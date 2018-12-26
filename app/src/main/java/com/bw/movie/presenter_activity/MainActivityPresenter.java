package com.bw.movie.presenter_activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
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
import com.bw.movie.bean.UserQueryVersionBean;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.fragment.CinemaFragment;
import com.bw.movie.fragment.FilmFragment;
import com.bw.movie.fragment.MyFragment;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.bw.movie.utils.net.NetBroadCastReciver;
import com.bw.movie.utils.net.NetworkUtils;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 底部Tab导航键
 * 姜谷蓄
 * 2018-11-27
 * <p>
 * 2018年12月7日 19:05:34
 * 焦浩康
 * 添加了 打开应用  检查本版更新的功能
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

        //如果登录 去检查本版
        queryVersion();

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

    @Override
    public void successnetwork() {
        super.successnetwork();

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
        new HttpUtil(context).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                RegisterBean bean = new Gson().fromJson(data, RegisterBean.class);
            }

            @Override
            public void fail(String data) {
                RegisterBean bean = new Gson().fromJson(data, RegisterBean.class);
            }

            @Override
            public void notNetwork(View data) {

            }
        }).postHead("/movieApi/tool/v1/verify/uploadPushToken",bodyMap,headMap,null,true,false);

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
        BroadcastReceiver receiver = new NetBroadCastReciver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiver, filter);
    }

    public void initnetword(Bundle savedInstanceState) {
    }


    /**
     * 2018年12月7日 19:08:17
     * 焦浩康
     * 检查本版更新
     */
    //检查本版更新
    //首先判断是否有网络
    public void queryVersion() {
        NetworkUtils networkUtils = new NetworkUtils();
        boolean connected = networkUtils.isConnected(context);
        if (connected) {
            Boolean isLogin1 = SharedPreferencesUtils.getBoolean(context, "isLogin");
            if (isLogin1) {

                //访问检查本版接口
                HashMap<String, String> partMap = new HashMap<String, String>();
                HashMap<String, String> headMap = new HashMap<String, String>();
                int userId = SharedPreferencesUtils.getInt(context, "userId");
                String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                headMap.put("userId", userId + "");
                headMap.put("sessionId", sessionId);
                headMap.put("ak", "010010010000");
                new HttpUtil(context).result(new HttpUtil.HttpListener() {
                    @Override
                    public void success(String data) {
                        Gson gson = new Gson();
                        UserQueryVersionBean userQueryVersionBean = gson.fromJson(data, UserQueryVersionBean.class);
                        int flag = userQueryVersionBean.getFlag();
                        //判断是否可更新
                        if (flag == 1) {
                            String downloadUrl = userQueryVersionBean.getDownloadUrl();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("发现新版本！");
                            builder.setMessage("是否跳转到浏览器更新?");
                            builder.setPositiveButton("去更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //跳转系统浏览器
                                    Uri uri = Uri.parse(downloadUrl.trim());
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    ((MainActivity) context).startActivity(intent);
                                }
                            });

                            builder.setNegativeButton("不更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    builder.create().dismiss();
                                }
                            });
                            builder.create().show();

                        } else if (flag == 2) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("已经是最新的版本了");
                            builder.setMessage("您不用更新了目前是最新本版哦~");
                            builder.setNegativeButton("知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    builder.create().dismiss();
                                }
                            });
                            builder.create().show();
                        } else {
                            Toast.makeText(context, "检查版本失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void fail(String data) {
                        Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void notNetwork(View data) {

                    }
                }).get("/movieApi/tool/v1/findNewVersion", partMap, headMap,"",true,false);
            } else {
                Toast.makeText(context, "检查本版之前请先去登录哦~", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "您当前没有连接网络哦~", Toast.LENGTH_SHORT).show();
        }
    }
}

