package com.bw.movie.presenter_fragment;

import android.content.Context;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelage;

/*
* 2018年11月27日 15:03:25
* 焦浩康
* 创建了基本的这个fragment
* */
public class MyFragmentPresenter extends AppDelage {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void getContext(Context context) {

    }
}
