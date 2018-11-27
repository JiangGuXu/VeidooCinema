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

public class SplashActivitypersenter extends AppDelage {
    private static final int MAG = 123;
    private int i = 3;
    private MyHanlder myHanlder = new MyHanlder();
    private TextView mSecibds;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData() {
        super.initData();
        mSecibds = get(R.id.splash_seconds);
        mSecibds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, MainActivity.class));
            }
        });

        myHanlder.sendEmptyMessageDelayed(0, 1000);
    }

    class MyHanlder extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            i--;
            mSecibds.setText(i + "s");
            if (i < 1) {
                myHanlder.removeCallbacksAndMessages(null);
                if (SharedPreferencesUtils.getBoolean(context, "isfrist")) {
                    context.startActivity(new Intent(context, MainActivity.class));
                } else {
                    context.startActivity(new Intent(context, WelcomeActivity.class));
                }
                ((SplashActivity) context).finish();
            } else {
                myHanlder.sendEmptyMessageDelayed(0, 1000);
            }
        }
    }

    private Context context;

    @Override
    public void getContext(Context context) {
        this.context = context;
    }


}
