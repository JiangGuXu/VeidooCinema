package com.bw.movie.presenter_activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.RegisterActivity;
import com.bw.movie.mvp.view.AppDelage;
/**
 * 用户登录页面
 * 姜谷蓄
 */

public class LoginActivityPresenter extends AppDelage implements View.OnClickListener {
    private TextView quick_register;
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    private Context context;
    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();
        quick_register = get(R.id.quick_register);
        quick_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击立即注册，跳转到注册页面
            case R.id.quick_register:
                Intent intent = new Intent(context, RegisterActivity.class);
                context.startActivity(intent);
                break;
        }
    }
}
