package com.bw.movie.presenter_activity;

import android.content.Context;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelage;
/**
 * 用户登录页面
 * 姜谷蓄
 */

public class LoginActivityPresenter extends AppDelage{
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
    }
}
