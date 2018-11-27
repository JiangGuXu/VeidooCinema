package com.bw.movie.fragment;

import android.support.v4.app.Fragment;

import com.bw.movie.mvp.presenter.BaseFragmentPresenter;
import com.bw.movie.presenter_fragment.FilmFragmentPresenter;

/**
 * 电影页面
 * <p>
 * <p>
 * 焦浩康
 * 2018年11月27日 15:12:37
 * 完成了基本的创建
 */
public class FilmFragment extends BaseFragmentPresenter<FilmFragmentPresenter> {
    @Override
    public Class<FilmFragmentPresenter> getClassPresenter() {
        return FilmFragmentPresenter.class;
    }
}
