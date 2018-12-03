package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.FindCinemaActivityPresenter;

public class FindCinemaActivity extends BaseActivityPresenter<FindCinemaActivityPresenter>{
    @Override
    public Class<FindCinemaActivityPresenter> getClassPresenter() {
        return FindCinemaActivityPresenter.class;
    }
}
