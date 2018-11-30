package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.ReSetpasswordActivityPresenter;

public class ReSetpasswordActivity extends BaseActivityPresenter<ReSetpasswordActivityPresenter> {
    @Override
    public Class<ReSetpasswordActivityPresenter> getClassPresenter() {
        return ReSetpasswordActivityPresenter.class;
    }
}
