package com.bw.movie.presenter_activity;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bw.movie.R;
import com.bw.movie.activity.SearchActivity;
import com.bw.movie.adapter.MyAdapterSearchList;
import com.bw.movie.model.FilmListData;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivityPresenter extends AppDelage implements View.OnClickListener{

    private Button mHot,mRelease,mComingsoon;
    private RecyclerView mRecyclerView;
    private MyAdapterSearchList myAdapterSearchList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }
    private Context context;
    @Override
    public void getContext(Context context) {
        this.context=context;
    }

    @Override
    public void initData() {
        super.initData();
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SearchActivity)context).finish();
            }
        },R.id.search_return);
        mHot = get(R.id.search_hot);
        mRelease = get(R.id.search_release);
        mComingsoon = get(R.id.search_comingsoon);
        mHot.setOnClickListener(this);
        mRelease.setOnClickListener(this);
        mComingsoon.setOnClickListener(this);
        mRecyclerView = get(R.id.search_recyler);
        myAdapterSearchList = new MyAdapterSearchList(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(myAdapterSearchList);
        doHttp("/movieApi/movie/v1/findHotMovieList");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_hot:
                mHot.setBackgroundResource(R.drawable.corners_selected_search);
                mHot.setTextColor(Color.WHITE);
                mRelease.setBackgroundResource(R.drawable.corners_search);
                mRelease.setTextColor(Color.BLACK);
                mComingsoon.setBackgroundResource(R.drawable.corners_search);
                mComingsoon.setTextColor(Color.BLACK);
                doHttp("/movieApi/movie/v1/findHotMovieList");
                break;
            case R.id.search_release:
                mHot.setBackgroundResource(R.drawable.corners_search);
                mHot.setTextColor(Color.BLACK);
                mRelease.setBackgroundResource(R.drawable.corners_selected_search);
                mRelease.setTextColor(Color.WHITE);
                mComingsoon.setBackgroundResource(R.drawable.corners_search);
                mComingsoon.setTextColor(Color.BLACK);
                doHttp("/movieApi/movie/v1/findReleaseMovieList");
                break;
            case R.id.search_comingsoon:
                mHot.setBackgroundResource(R.drawable.corners_search);
                mHot.setTextColor(Color.BLACK);
                mRelease.setBackgroundResource(R.drawable.corners_search);
                mRelease.setTextColor(Color.BLACK);
                mComingsoon.setBackgroundResource(R.drawable.corners_selected_search);
                mComingsoon.setTextColor(Color.WHITE);
                doHttp("/movieApi/movie/v1/findComingSoonMovieList");
                break;
        }
    }


    private void doHttp(String url) {
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("count","10");
        new HttpUtil().get(url,map).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                FilmListData filmListData = gson.fromJson(data, FilmListData.class);
                List<FilmListData.ResultBean> result = filmListData.getResult();
                myAdapterSearchList.setList(result);
            }

            @Override
            public void fail(String data) {

            }
        });
    }
}