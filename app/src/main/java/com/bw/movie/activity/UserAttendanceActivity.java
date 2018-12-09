package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.UserAttendanceActivityPresenter;

/**
 * 2018年12月8日 09:02:27
 * 焦浩康
 * 用户签到activity
 */

public class UserAttendanceActivity extends BaseActivityPresenter<UserAttendanceActivityPresenter> {
    @Override
    public Class<UserAttendanceActivityPresenter> getClassPresenter() {
        return UserAttendanceActivityPresenter.class;
    }
}
