package com.bw.movie.presenter_activity;

import android.content.Context;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelage;

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
