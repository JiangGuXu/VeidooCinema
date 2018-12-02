package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.Recommendeddetailspersenter;

public class Recommendeddetails extends BaseActivityPresenter<Recommendeddetailspersenter> {
    @Override
    public Class<Recommendeddetailspersenter> getClassPresenter() {
        return Recommendeddetailspersenter.class;
    }
}
