package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.UserAttentionActivityPresenter;

public class UserAttentionActivity extends BaseActivityPresenter<UserAttentionActivityPresenter> {
    @Override
    public Class<UserAttentionActivityPresenter> getClassPresenter() {
        return UserAttentionActivityPresenter.class;
    }
}
