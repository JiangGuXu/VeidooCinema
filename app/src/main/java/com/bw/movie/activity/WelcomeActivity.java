package com.bw.movie.activity;

import com.bw.movie.presenter_activity.WelcomeActivitypersenter;
import com.bw.movie.mvp.presenter.BaseActivityPresenter;

public class WelcomeActivity extends BaseActivityPresenter<WelcomeActivitypersenter>{
    @Override
    public Class<WelcomeActivitypersenter> getClassPresenter() {
        return WelcomeActivitypersenter.class;
    }
}
