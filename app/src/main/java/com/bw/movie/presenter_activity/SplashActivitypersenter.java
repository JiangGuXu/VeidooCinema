package com.bw.movie.presenter_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.bw.movie.activity.MainActivity;
import com.bw.movie.activity.SplashActivity;
import com.bw.movie.activity.WelcomeActivity;
import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.SharedPreferencesUtils;
/**
 * 3秒跳转页面
 * 姜谷蓄优化
 */
public class SplashActivitypersenter extends AppDelage {
    private int i = 3;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (i>1){
                i--;
                mSecibds.setText(i+"s");
                handler.sendEmptyMessageDelayed(0, 1000);
            }else {
                //判断是否是首次进入
                if (SharedPreferencesUtils.getBoolean(context,"isfrist")){
                    Intent intent = new Intent(context,MainActivity.class);
                    context.startActivity(intent);
                    handler.removeCallbacksAndMessages(null);
                    ((SplashActivity)context).finish();
                }else {
                    //默认是false,跳转到欢迎页
                    Intent intent = new Intent(context,WelcomeActivity.class);
                    context.startActivity(intent);
                    handler.removeCallbacksAndMessages(null);
                    ((SplashActivity)context).finish();
                }
            }
        }
    };
    private TextView mSecibds;
    private TextView text_jump;
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    Context context;

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();
        mSecibds = get(R.id.splash_seconds);
        text_jump = get(R.id.jump);
       //点击跳过跳转
        text_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否是首次进入
                if (SharedPreferencesUtils.getBoolean(context,"isfrist")){
                    Intent intent = new Intent(context,MainActivity.class);
                    context.startActivity(intent);
                    handler.removeCallbacksAndMessages(null);
                    ((SplashActivity)context).finish();
                }else {
                    //默认是false,跳转到欢迎页
                    Intent intent = new Intent(context,WelcomeActivity.class);
                    context.startActivity(intent);
                    handler.removeCallbacksAndMessages(null);
                    ((SplashActivity)context).finish();
                }
            }
        });
        handler.sendEmptyMessageDelayed(0, 1000);
    }
}

