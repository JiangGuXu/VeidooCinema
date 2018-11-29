package com.bw.movie.activity;

import com.bw.movie.adapter.DetailsActivityPresenter;
import com.bw.movie.mvp.presenter.BaseActivityPresenter;

public class DetailsActivity extends BaseActivityPresenter<DetailsActivityPresenter> {
    @Override
    public Class<DetailsActivityPresenter> getClassPresenter() {
        return DetailsActivityPresenter.class;
    }
}
