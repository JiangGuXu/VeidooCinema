package com.bw.movie.activity;

import com.bw.movie.presenter_activity.NearActivitypersenter;
import com.bw.movie.mvp.presenter.BaseActivityPresenter;

public class NearActivity  extends BaseActivityPresenter<NearActivitypersenter>{
    @Override
    public Class<NearActivitypersenter> getClassPresenter() {
        return NearActivitypersenter.class;
    }
}
