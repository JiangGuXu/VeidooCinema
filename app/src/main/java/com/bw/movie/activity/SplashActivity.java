package com.bw.movie.activity;

import com.bw.movie.presenter_activity.SplashActivitypersenter;
import com.bw.movie.mvp.presenter.BaseActivityPresenter;

public class SplashActivity extends BaseActivityPresenter<SplashActivitypersenter>{
    @Override
    public Class<SplashActivitypersenter> getClassPresenter() {
        return SplashActivitypersenter.class;
    }
}
