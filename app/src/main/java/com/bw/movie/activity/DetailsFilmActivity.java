package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.DetailsFilmActivityPresenter;

import cn.jzvd.Jzvd;

public class DetailsFilmActivity extends BaseActivityPresenter<DetailsFilmActivityPresenter> {
    @Override
    public Class<DetailsFilmActivityPresenter> getClassPresenter() {
        return DetailsFilmActivityPresenter.class;
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

}
