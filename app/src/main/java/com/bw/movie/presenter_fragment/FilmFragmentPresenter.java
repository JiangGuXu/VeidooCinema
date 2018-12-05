package com.bw.movie.presenter_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.activity.SearchActivity;
import com.bw.movie.adapter.MyAdapterFilmBanner;
import com.bw.movie.adapter.MyAdapterFilmList;
import com.bw.movie.bean.FilmListData;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;


/*
 * 电影页面presenter
 * 2018年11月27日 15:18:35
 * 焦浩康
 * 创建了基本的这个presenter
 * */
public class FilmFragmentPresenter extends AppDelage {

    private Context context;
    private RecyclerView mListView;
    private MyAdapterFilmList myAdapterFilmList;
    private List<String> titles =new ArrayList<>();
    private List<FilmListData> list =new ArrayList<>();
    private List<String> urls =new ArrayList<>();
    private RecyclerCoverFlow mRecyclerCoverFlow;
    private MyAdapterFilmBanner myAdapterFilmBanner;
    private RelativeLayout mRelativeLayout;
    private int width;
    private int widths;
    private View view1;
    private RelativeLayout relativeLayout;

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
        //添加数据
        addlist();
        //轮播图
        mRelativeLayout = get(R.id.film_search_relative);
        View view = get(R.id.film_divider_view);
        view1 = get(R.id.film_divider_view1);
        width = view.getWidth();
        relativeLayout = get(R.id.film_divider_relative);
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).startActivity(new Intent(context, SearchActivity.class));
            }
        });
        mRecyclerCoverFlow = (RecyclerCoverFlow) get(R.id.film_list_recyler);
        myAdapterFilmBanner = new MyAdapterFilmBanner(context);
        mRecyclerCoverFlow.setAdapter(myAdapterFilmBanner);
        mRecyclerCoverFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {

            }
        });
        myAdapterFilmBanner.setListener(new MyAdapterFilmBanner.RecyclerItemListener() {
            @Override
            public void onClick(int position)  {
                mRecyclerCoverFlow.scrollToPosition(position);
            }

            @Override
            public void getmovieId(int movieId) {

            }
        });
        //请求轮播数据
        doHttpBanner();
        //电影展示
        mListView =(RecyclerView) get(R.id.film_list_view);
        myAdapterFilmList = new MyAdapterFilmList(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(linearLayoutManager);
        mListView.setAdapter(myAdapterFilmList);
        //请求影片数据
        for (int i = 0; i <urls.size() ; i++) {
            doHttp(urls.get(i));
        }
        myAdapterFilmList.setList(list,titles);
    }
    //请求数据并放入list集合中
    private void doHttp( String url) {
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
                    list.add(filmListData);
                }

                @Override
                public void fail(String data) {

                }
            });
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
                List<FilmListData.ResultBean> result = filmListData.getResult();
                if(result.size()==0){
                    doHttpBanner();
                }
                widths = width/result.size();
                myAdapterFilmBanner.setList(result);
                mRecyclerCoverFlow.scrollToPosition(result.size()/2);
            }

            @Override
            public void fail(String data) {

            }
        });
    }

    private void addlist() {
        titles.add("热门电影");
        titles.add("正在热映");
        titles.add("即将上映");
        urls.add("/movieApi/movie/v1/findHotMovieList");
        urls.add("/movieApi/movie/v1/findReleaseMovieList");
        urls.add("/movieApi/movie/v1/findComingSoonMovieList");


    }
}
