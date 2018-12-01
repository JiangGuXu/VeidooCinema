package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.DetailsActivitypersenter;
/*
 *
 * 2018年11月29日 15:18:30
 * 程丹妮
 * 创建了基本的这个presenter
 * */
public class DetailsActivity extends BaseActivityPresenter<DetailsActivitypersenter> {
    @Override
    public Class<DetailsActivitypersenter> getClassPresenter() {
        return DetailsActivitypersenter.class;
    }

}
