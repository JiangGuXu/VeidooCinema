package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.RegisterActivityPresenter;

public class RegisterActivity extends BaseActivityPresenter<RegisterActivityPresenter>{
    @Override
    public Class<RegisterActivityPresenter> getClassPresenter() {
        return RegisterActivityPresenter.class;
    }
}
