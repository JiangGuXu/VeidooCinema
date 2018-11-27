package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.LoginActivityPresenter;

public class LoginActivity extends BaseActivityPresenter<LoginActivityPresenter>{
    @Override
    public Class<LoginActivityPresenter> getClassPresenter() {
        return LoginActivityPresenter.class;
    }
}
