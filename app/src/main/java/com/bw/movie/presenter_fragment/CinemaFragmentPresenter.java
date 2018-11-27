package com.bw.movie.presenter_fragment;

import android.content.Context;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelage;

/*
 * 影院页面presenter
 * 2018年11月27日 15:18:30
 * 焦浩康
 * 创建了基本的这个presenter
 * */
public class CinemaFragmentPresenter extends AppDelage {

    private Context context;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cinema;
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
