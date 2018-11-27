package com.bw.movie.fragment;

import android.support.v4.app.Fragment;

import com.bw.movie.mvp.presenter.BaseFragmentPresenter;
import com.bw.movie.presenter_fragment.CinemaFragmentPresenter;
import com.bw.movie.presenter_fragment.MyFragmentPresenter;

/**
 * 影院页面
 * <p>
 * <p>
 * 焦浩康
 * 2018年11月27日 15:12:37
 * 完成了基本的创建
 */
public class CinemaFragment extends BaseFragmentPresenter<CinemaFragmentPresenter> {
    @Override
    public Class<CinemaFragmentPresenter> getClassPresenter() {
        return CinemaFragmentPresenter.class;
    }
}
