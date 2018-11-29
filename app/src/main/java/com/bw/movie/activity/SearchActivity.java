package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.SearchActivityPresenter;

public class SearchActivity extends BaseActivityPresenter<SearchActivityPresenter> {
    @Override
    public Class<SearchActivityPresenter> getClassPresenter() {
        return SearchActivityPresenter.class;
    }
}
