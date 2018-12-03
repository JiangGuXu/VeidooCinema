package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.PlayCinemaActivityPresenter;

public class PlayCinemaActivity extends BaseActivityPresenter<PlayCinemaActivityPresenter>{
    @Override
    public Class<PlayCinemaActivityPresenter> getClassPresenter() {
        return PlayCinemaActivityPresenter.class;
    }
}
