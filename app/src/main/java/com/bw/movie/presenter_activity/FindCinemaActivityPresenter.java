package com.bw.movie.presenter_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.FindCinemaActivity;
import com.bw.movie.activity.PlayCinemaActivity;
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
    private List<Recommendedbean.Resultbean> bean;
    private int movieId;
    private String name;
    private String director;
    private String type;
    private String time;
    private String country;
    private String logo;

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
        name = ((FindCinemaActivity) context).getIntent().getStringExtra("movie_name");
        director = ((FindCinemaActivity) context).getIntent().getStringExtra("Director");
        type = ((FindCinemaActivity) context).getIntent().getStringExtra("type");
        time = ((FindCinemaActivity) context).getIntent().getStringExtra("time");
        country = ((FindCinemaActivity) context).getIntent().getStringExtra("country");
        logo = ((FindCinemaActivity) context).getIntent().getStringExtra("logo");
        movieId = ((FindCinemaActivity) context).getIntent().getIntExtra("movieId", 22);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FindCinemaActivity)context).finish();
            }
        },R.id.image_back);
        //赋值电影名
        movie_name.setText(name);
        //请求接口
        Map<String,String> map = new HashMap();
        map.put("movieId",String.valueOf(movieId));
        new HttpUtil().get("/movieApi/movie/v1/findCinemasListByMovieId",map,null).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Recommendedbean json = new Gson().fromJson(data, Recommendedbean.class);
                bean = json.getResult();
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
        adapter.setListener(new FindCinemaAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //获取点击条目的电影院信息
                String address = bean.get(position).getAddress();
                String cinema_name = bean.get(position).getName();
                int id = bean.get(position).getId();
                // 跳转影院的排期
                Intent intent = new Intent(context, PlayCinemaActivity.class);
                intent.putExtra("cinema_name", cinema_name);
                intent.putExtra("address",address);
                intent.putExtra("logo",logo);
                intent.putExtra("movie_name",name);
                intent.putExtra("movieId",movieId);
                intent.putExtra("Director",director);
                intent.putExtra("type",type);
                intent.putExtra("time",time);
                intent.putExtra("country",country);
                intent.putExtra("cinemaId",id);
                ((FindCinemaActivity)context).startActivity(intent);
            }
        });
    }
}
