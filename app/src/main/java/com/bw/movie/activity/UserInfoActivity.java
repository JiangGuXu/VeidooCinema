package com.bw.movie.activity;


import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.UserInfoActivityPresenter;

/**
 * 2018年11月28日 10:19:58
 * 焦浩康
 * 我的信息页面
 * 例如my页面点击我的信息跳转这个页面
 */
public class UserInfoActivity extends BaseActivityPresenter<UserInfoActivityPresenter> {

    @Override
    public Class<UserInfoActivityPresenter> getClassPresenter() {
        return UserInfoActivityPresenter.class;
    }

}
