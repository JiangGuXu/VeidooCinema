package com.bw.movie.presenter_activity;

import android.content.Context;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelage;

public class DetailsActivitypersenter extends AppDelage{
    @Override
    public int getLayoutId() {
        return R.layout.activity_details;
    }

    @Override
    public void getContext(Context context) {

    }
}
