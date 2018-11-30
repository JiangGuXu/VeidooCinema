package com.bw.movie.activity;

import com.bw.movie.presenter_activity.DetailsFilmActivityPresenter;
import com.bw.movie.mvp.presenter.BaseActivityPresenter;

public class DetailsFilmActivity extends BaseActivityPresenter<DetailsFilmActivityPresenter> {
    @Override
    public Class<DetailsFilmActivityPresenter> getClassPresenter() {
        return DetailsFilmActivityPresenter.class;
    }
}
