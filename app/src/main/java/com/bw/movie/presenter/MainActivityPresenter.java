package com.bw.movie.presenter;

import android.content.Context;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelage;

public class MainActivityPresenter extends AppDelage {
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
    private Context context;
    @Override
    public void getContext(Context context) {
        this.context=context;
    }
}
