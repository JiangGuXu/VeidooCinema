package com.bw.movie.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.UltimateBar;

/**
 *
 * author:赵瑜峰
 * date:2018/11/27
 */
public abstract class BaseActivityPresenter<T extends AppDelage> extends AppCompatActivity {

    protected T daleagt;
    public abstract Class<T> getClassPresenter();
    public BaseActivityPresenter(){
        try {
            daleagt = getClassPresenter().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daleagt.create(getLayoutInflater(),null,savedInstanceState);
        setContentView(daleagt.view());
        daleagt.getContext(this);
        daleagt.initData();
        UltimateBar.newImmersionBuilder().applyNav(false).build(this).apply();
    }
    @Override
    public void onResume() {
        super.onResume();
        daleagt.resume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        daleagt.destry();
        daleagt=null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        daleagt.restart();
    }
}
