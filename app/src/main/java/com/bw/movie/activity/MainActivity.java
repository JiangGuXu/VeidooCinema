package com.bw.movie.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.MainActivityPresenter;

public class MainActivity extends BaseActivityPresenter<MainActivityPresenter> {


    @Override
    public Class<MainActivityPresenter> getClassPresenter() {
        return MainActivityPresenter.class;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        daleagt.initnetword(savedInstanceState);
        super.onCreate(savedInstanceState);
    }
}
