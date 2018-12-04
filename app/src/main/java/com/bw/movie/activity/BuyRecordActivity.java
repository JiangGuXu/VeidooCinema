package com.bw.movie.activity;

import com.bw.movie.mvp.presenter.BaseActivityPresenter;
import com.bw.movie.presenter_activity.BuyRecordActivityPresenter;

public class BuyRecordActivity extends BaseActivityPresenter<BuyRecordActivityPresenter>{
    @Override
    public Class<BuyRecordActivityPresenter> getClassPresenter() {
        return BuyRecordActivityPresenter.class;
    }

    @Override
    public void onResume() {
        super.onResume();
        daleagt.onresume();
    }
}
