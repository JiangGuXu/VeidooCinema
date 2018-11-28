package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.UserAttentionActivityPresenter;



/**
 * 2018年11月28日 18:16:37
 * 焦浩康
 * 这是我的关注  activity
 */
public class UserAttentionActivity extends BaseActivityPresenter<UserAttentionActivityPresenter> {
    @Override
    public Class<UserAttentionActivityPresenter> getClassPresenter() {
        return UserAttentionActivityPresenter.class;
    }
}
