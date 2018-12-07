package com.bw.movie.presenter_fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.adapter.MyAdapterFilm;
import com.bw.movie.adapter.MyAdapterFilmBanner;
import com.bw.movie.adapter.MyAdapterFilmList;
import com.bw.movie.bean.FilmListData;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.NetworkUtils;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import recycler.coverflow.RecyclerCoverFlow;


/*
 * 电影页面presenter
 * 2018年11月27日 15:18:35
 * 焦浩康
 * 创建了基本的这个presenter
 * */
public class FilmFragmentPresenter extends AppDelage {

    private Context context;
    private List<String> titles =new ArrayList<>();
    private List<String> urls =new ArrayList<>();
    private RelativeLayout mIsnetword;
    private List<FilmListData.ResultBean> result;
    private MyAdapterFilm myAdapterFilm;
    private XRecyclerView xRecyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_film;
    }

    @Override
    public void getContext(Context context) {
        this.context =context;
    }

    @Override
    public void initData() {
        super.initData();
        mIsnetword = get(R.id.film_isnetword);
        xRecyclerView = get(R.id.film_xrecycler);
        if (!NetworkUtils.isConnected(context)){
            mIsnetword.setVisibility(View.VISIBLE);
        }else{
            mIsnetword.setVisibility(View.GONE);

        }
        //添加数据
        addlist();

        myAdapterFilm = new MyAdapterFilm(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(linearLayoutManager);
        xRecyclerView.setAdapter(myAdapterFilm);
        xRecyclerView.setPullRefreshEnabled(true);
        doHttpBanner();
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NetworkUtils.isConnected(context)){
                    mIsnetword.setVisibility(View.VISIBLE);
                }else{
                    mIsnetword.setVisibility(View.GONE);
                    doHttpBanner();
                }
            }
        },R.id.film_retry_isnetword);

        //请求轮播数据


        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                doHttpBanner();

            }

            @Override
            public void onLoadMore() {
                xRecyclerView.loadMoreComplete();
            }
        });
    }

    @Override
    public void resume() {
        super.resume();
        if (!NetworkUtils.isConnected(context)){
            mIsnetword.setVisibility(View.VISIBLE);
        }else{
            mIsnetword.setVisibility(View.GONE);
            doHttpBanner();
        }
    }

    private void addlist() {
        titles.add("热门电影");
        titles.add("正在热映");
        titles.add("即将上映");
        urls.add("/movieApi/movie/v1/findHotMovieList");
        urls.add("/movieApi/movie/v1/findReleaseMovieList");
        urls.add("/movieApi/movie/v1/findComingSoonMovieList");


    }

    //请求轮播数据
    private void doHttpBanner() {
        String url = "/movieApi/movie/v1/findHotMovieList";
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
                result = filmListData.getResult();
                if(result.size()==0){
                    doHttpBanner();
                }
                myAdapterFilm.setList(titles,urls,result);
                xRecyclerView.refreshComplete();
            }

            @Override
            public void fail(String data) {

            }
        });
    }
}
