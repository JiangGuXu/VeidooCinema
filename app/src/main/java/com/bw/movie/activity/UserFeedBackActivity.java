package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.UserFeedBackActivityPresenter;

/**
 * 2018年11月30日 19:14:33
 * 焦浩康
 * 意见反馈页面
 */


public class UserFeedBackActivity extends BaseActivityPresenter<UserFeedBackActivityPresenter> {
    @Override
    public Class<UserFeedBackActivityPresenter> getClassPresenter() {
        return UserFeedBackActivityPresenter.class;
    }
}
