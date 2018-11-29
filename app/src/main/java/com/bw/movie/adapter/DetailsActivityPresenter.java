package com.bw.movie.adapter;

import android.content.Context;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelage;

public class DetailsActivityPresenter extends AppDelage {
    @Override
    public int getLayoutId() {
        return R.layout.activity_details;
    }
    private Context context;
    @Override
    public void getContext(Context context) {
        this.context=context;
    }

    @Override
    public void initData() {
        super.initData();

    }
}
