package com.bw.movie.presenter_activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bw.movie.R;
import com.bw.movie.activity.SearchActivity;
import com.bw.movie.adapter.MyAdapterSearchList;
import com.bw.movie.bean.FilmListData;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 * 推荐影院的排期
 * 2018年11月28日 15:18:30
 * 赵瑜峰
 * 创建了基本的这个presenter
 * */
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
        Intent intent = ((SearchActivity) context).getIntent();
        int id = intent.getIntExtra("position", 0);
        mRecyclerView = get(R.id.search_recyler);
        myAdapterSearchList = new MyAdapterSearchList(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(myAdapterSearchList);
        if(id==0){
            hot();
        }else if(id==1){
            release();
        }else if(id==2){
            comingsoon();
        }
        myAdapterSearchList.result(new MyAdapterSearchList.SearchFouceListener() {
            @Override
            public void fouceChange(String data) {
                if("热门电影".equals(data)){
                    hot();
                }else if("正在上映".equals(data)){
                    release();
                }else if("即将上映".equals(data)){
                    comingsoon();
                }
            }

        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_hot:
                hot();
                break;
            case R.id.search_release:
               release();
                break;
            case R.id.search_comingsoon:
                comingsoon();
                break;
        }
    }

    private void comingsoon() {
        mHot.setBackgroundResource(R.drawable.corners_search);
        mHot.setTextColor(Color.BLACK);
        mRelease.setBackgroundResource(R.drawable.corners_search);
        mRelease.setTextColor(Color.BLACK);
        mComingsoon.setBackgroundResource(R.drawable.corners_selected_search);
        mComingsoon.setTextColor(Color.WHITE);
        doHttp("/movieApi/movie/v1/findComingSoonMovieList","即将上映");
    }

    private void release() {
        mHot.setBackgroundResource(R.drawable.corners_search);
        mHot.setTextColor(Color.BLACK);
        mRelease.setBackgroundResource(R.drawable.corners_selected_search);
        mRelease.setTextColor(Color.WHITE);
        mComingsoon.setBackgroundResource(R.drawable.corners_search);
        mComingsoon.setTextColor(Color.BLACK);
        doHttp("/movieApi/movie/v1/findReleaseMovieList","正在上映");
    }

    private void hot() {
        mHot.setBackgroundResource(R.drawable.corners_selected_search);
        mHot.setTextColor(Color.WHITE);
        mRelease.setBackgroundResource(R.drawable.corners_search);
        mRelease.setTextColor(Color.BLACK);
        mComingsoon.setBackgroundResource(R.drawable.corners_search);
        mComingsoon.setTextColor(Color.BLACK);
        doHttp("/movieApi/movie/v1/findHotMovieList","热门电影");
    }


    private void doHttp(String url, final String title) {
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("count","10");
        Map<String,String> mapHead = new HashMap<>();
        if(SharedPreferencesUtils.getBoolean(context,"isLogin")){
            int userId = SharedPreferencesUtils.getInt(context, "userId");
            String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
            mapHead.put("userId",userId+"");
            mapHead.put("sessionId",sessionId);
        }
        new HttpUtil().get(url,map,mapHead).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                FilmListData filmListData = gson.fromJson(data, FilmListData.class);
                List<FilmListData.ResultBean> result = filmListData.getResult();
                myAdapterSearchList.setList(result,title);
            }

            @Override
            public void fail(String data) {

            }
        });
    }
}