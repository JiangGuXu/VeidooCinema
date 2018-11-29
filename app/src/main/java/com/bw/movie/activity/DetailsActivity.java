package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.DetailsActivitypersenter;

public class DetailsActivity extends BaseActivityPresenter<DetailsActivitypersenter> {
    @Override
    public Class<DetailsActivitypersenter> getClassPresenter() {
        return DetailsActivitypersenter.class;
    }

}
