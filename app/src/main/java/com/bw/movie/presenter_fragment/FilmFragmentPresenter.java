package com.bw.movie.presenter_fragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.ListView;

import com.bambootang.viewpager3d.FlowTransformer;
import com.bambootang.viewpager3d.LinearLocationTransformer;
import com.bambootang.viewpager3d.LinearRotationTransformer;
import com.bambootang.viewpager3d.LinearScaleTransformer;
import com.bw.movie.R;
import com.bw.movie.adapter.MyAdapterFilmList;
import com.bw.movie.model.FilmList;
import com.bw.movie.mvp.view.AppDelage;

import java.util.ArrayList;
import java.util.List;


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
        addlist();
        mListView = get(R.id.film_list_view);
        myAdapterFilmList = new MyAdapterFilmList(context);
        mListView.setAdapter(myAdapterFilmList);
        myAdapterFilmList.setList(lists);
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
