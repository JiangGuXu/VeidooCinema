package com.bw.movie.presenter_fragment;

import android.content.Context;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelage;

public class MyFragmentPresenter extends AppDelage {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void getContext(Context context) {

    }
}
