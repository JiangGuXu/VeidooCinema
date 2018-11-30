package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.SelectedSetActivityPresenter;

public class SelectedSetActivity extends BaseActivityPresenter<SelectedSetActivityPresenter>{
    @Override
    public Class<SelectedSetActivityPresenter> getClassPresenter() {
        return SelectedSetActivityPresenter.class;
    }
}
