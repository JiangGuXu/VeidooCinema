package com.bw.movie.presenter_activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.FindCinemaActivity;
import com.bw.movie.adapter.FindCinemaAdapter;
import com.bw.movie.bean.Recommendedbean;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 查看播放该电影的影院页面
 * 姜谷蓄
 * 2018/12/3
 *
 */

public class FindCinemaActivityPresenter extends AppDelage{
    private TextView movie_name;
    private RecyclerView recyclerView;
    private FindCinemaAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_findcinema;
    }

    private Context context;
    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();
        movie_name = get(R.id.film_name);
        recyclerView = get(R.id.activity_recyclerView);
        adapter = new FindCinemaAdapter(context);
        //获取传值
        String name = ((FindCinemaActivity) context).getIntent().getStringExtra("movie_name");
        int movieId = ((FindCinemaActivity) context).getIntent().getIntExtra("movieId", 22);
        //赋值电影名
        movie_name.setText(name);
        //请求接口
        Map<String,String> map = new HashMap();
        map.put("movieId",String.valueOf(movieId));
        new HttpUtil().get("/movieApi/movie/v1/findCinemasListByMovieId",map,null).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Recommendedbean json = new Gson().fromJson(data, Recommendedbean.class);
                List<Recommendedbean.Resultbean> bean = json.getResult();
                adapter.setList(bean);
                LinearLayoutManager s = new LinearLayoutManager(context);
                s.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(s);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void fail(String data) {

            }
        });
    }
}
