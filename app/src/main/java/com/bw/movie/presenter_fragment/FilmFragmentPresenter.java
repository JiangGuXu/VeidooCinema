package com.bw.movie.presenter_fragment;

import android.content.Context;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelage;



public class FilmFragmentPresenter extends AppDelage {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_film;
    }

    @Override
    public void getContext(Context context) {

    }
}
