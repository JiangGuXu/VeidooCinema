package com.bw.movie.presenter_fragment;

import android.content.Context;
import android.widget.ListView;

import com.bw.movie.R;
import com.bw.movie.adapter.MyAdapterFilmBanner;
import com.bw.movie.adapter.MyAdapterFilmList;
import com.bw.movie.model.FilmList;
import com.bw.movie.model.FilmListData;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.google.gson.Gson;

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
    private ListView mListView;
    private MyAdapterFilmList myAdapterFilmList;
    private List<FilmList> lists =new ArrayList<>();
    private RecyclerCoverFlow mRecyclerCoverFlow;
    private MyAdapterFilmBanner myAdapterFilmBanner;

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
        mRecyclerCoverFlow = (RecyclerCoverFlow) get(R.id.film_list_recyler);
        myAdapterFilmBanner = new MyAdapterFilmBanner(context);
        mRecyclerCoverFlow.setAdapter(myAdapterFilmBanner);
        myAdapterFilmBanner.setListener(new MyAdapterFilmBanner.RecyclerItemListener() {
            @Override
            public void onClick(int position)  {
                mRecyclerCoverFlow.scrollToPosition(position);
            }
        });
        //请求轮播数据
        doHttpBanner();
        //电影展示
        mListView = get(R.id.film_list_view);
        myAdapterFilmList = new MyAdapterFilmList(context);
        mListView.setAdapter(myAdapterFilmList);
        myAdapterFilmList.setList(lists);
    }
    //请求轮播数据
    private void doHttpBanner() {
        String url = "/movieApi/movie/v1/findHotMovieList";
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("count","10");
        new HttpUtil().get(url,map).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                FilmListData filmListData = gson.fromJson(data, FilmListData.class);
                List<FilmListData.ResultBean> result = filmListData.getResult();
                if(result.size()==0){
                    doHttpBanner();
                }
                myAdapterFilmBanner.setList(result);
                mRecyclerCoverFlow.scrollToPosition(2);
            }

            @Override
            public void fail(String data) {

            }
        });
    }

    private void addlist() {
        FilmList filmList = new FilmList();
        filmList.setTitle("热门电影");
        filmList.setUrl("/movieApi/movie/v1/findHotMovieList");
        FilmList filmList1 = new FilmList();
        filmList1.setTitle("正在热映");
        filmList1.setUrl("/movieApi/movie/v1/findReleaseMovieList");
        FilmList filmList2 = new FilmList();
        filmList2.setTitle("即将上映");
        filmList2.setUrl("/movieApi/movie/v1/findComingSoonMovieList");
        lists.add(filmList);
        lists.add(filmList1);
        lists.add(filmList2);
    }
}
