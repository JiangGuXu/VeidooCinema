package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter.MainActivityPresenter;

public class MainActivity extends BaseActivityPresenter<MainActivityPresenter>{

    @Override
    public Class<MainActivityPresenter> getClassPresenter() {
        return MainActivityPresenter.class;
    }
}

