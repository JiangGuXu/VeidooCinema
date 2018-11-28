package com.bw.movie.presenter_activity;

import android.content.Context;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelage;


/**
 * 2018年11月28日 18:16:37
 * 焦浩康
 * 这是我的关注  activity
 */
public class UserAttentionActivityPresenter extends AppDelage {

    private Context context;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_attention;
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }
}
