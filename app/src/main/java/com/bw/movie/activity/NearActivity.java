package com.bw.movie.activity;

import com.bw.movie.presenter_activity.NearActivitypersenter;
import com.bw.movie.mvp.presenter.BaseActivityPresenter;
/*
 * 附近影院页面presenter
 * 2018年11月29日 15:18:30
 * 程丹妮
 * 创建了基本的这个presenter
 * */
public class NearActivity  extends BaseActivityPresenter<NearActivitypersenter>{
    @Override
    public Class<NearActivitypersenter> getClassPresenter() {
        return NearActivitypersenter.class;
    }
}
