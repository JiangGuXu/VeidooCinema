package com.bw.movie.presenter_activity;

import android.content.Context;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelage;



public class UserInfoActivityPresenter extends AppDelage {

    private Context context;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void getContext(Context context) {
        this.context =context;
    }

    @Override
    public void initData() {
        super.initData();

    }
}
