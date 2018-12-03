package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.UserReSertInfoActivityPresenter;

/**
 * 2018年12月1日 10:08:53
 * 焦浩康
 * 这是修改用户信息的activity
 */
public class UserReSertInfoActivity extends BaseActivityPresenter<UserReSertInfoActivityPresenter> {
    @Override
    public Class<UserReSertInfoActivityPresenter> getClassPresenter() {
        return UserReSertInfoActivityPresenter.class;
    }
}
