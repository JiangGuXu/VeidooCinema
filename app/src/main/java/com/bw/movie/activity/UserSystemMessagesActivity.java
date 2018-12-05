package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.UserSystemMessagesActivityPresenter;

/**
 * 2018年12月3日 21:11:00
 * 焦浩康
 * 系统消息activity
 */
public class UserSystemMessagesActivity extends BaseActivityPresenter<UserSystemMessagesActivityPresenter> {
    @Override
    public Class<UserSystemMessagesActivityPresenter> getClassPresenter() {
        return UserSystemMessagesActivityPresenter.class;
    }
}
