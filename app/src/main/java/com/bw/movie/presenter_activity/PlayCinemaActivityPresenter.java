package com.bw.movie.presenter_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.DetailsActivity;
import com.bw.movie.activity.PlayCinemaActivity;
import com.bw.movie.activity.SelectedSetActivity;
import com.bw.movie.adapter.MyAdapterDetails;
import com.bw.movie.bean.Detailsbean;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页点击影院详情
 * 姜谷蓄
 */

public class PlayCinemaActivityPresenter extends AppDelage{
    private TextView cinema_name;
    private TextView cinema_address;
    private SimpleDraweeView film_icon;
    private TextView film_name;
    private TextView film_type;
    private TextView film_director;
    private TextView film_time;
    private TextView film_country;
    private ImageView image_back;
    private RecyclerView film_recycler;
    private MyAdapterDetails myAdapter;
    private List<Detailsbean.Resultbean> result;
    private int movieId;
    private int cinemaId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_playcinema;
    }
    private Context context;
    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();
        cinema_name = get(R.id.cinema_name);
        cinema_address = get(R.id.cinema_address);
        film_icon = get(R.id.film_icon);
        film_name = get(R.id.film_name);
        film_type = get(R.id.film_type);
        film_director = get(R.id.film_director);
        film_time = get(R.id.film_time);
        film_country = get(R.id.film_country);
        film_recycler = get(R.id.film_recycler);
        image_back = get(R.id.image_back);
        //获取传值
        String name = ((PlayCinemaActivity) context).getIntent().getStringExtra("movie_name");
        String director = ((PlayCinemaActivity) context).getIntent().getStringExtra("Director");
        String type = ((PlayCinemaActivity) context).getIntent().getStringExtra("type");
        String time = ((PlayCinemaActivity) context).getIntent().getStringExtra("time");
        String country = ((PlayCinemaActivity) context).getIntent().getStringExtra("country");
        String logo = ((PlayCinemaActivity) context).getIntent().getStringExtra("logo");
        String cinemaname = ((PlayCinemaActivity) context).getIntent().getStringExtra("cinema_name");
        String address = ((PlayCinemaActivity) context).getIntent().getStringExtra("address");
        movieId = ((PlayCinemaActivity) context).getIntent().getIntExtra("movieId", 22);
        cinemaId = ((PlayCinemaActivity) context).getIntent().getIntExtra("cinemaId", 22);

        //给控件赋值
        cinema_name.setText(cinemaname);
        cinema_address.setText(address);
        film_icon.setImageURI(logo);
        film_name.setText(name);
        film_type.setText("类型:"+type);
        film_director.setText("导演:"+director);
        film_time.setText("时长:"+time);
        film_country.setText("产地"+country);
        myAdapter = new MyAdapterDetails(context);
        doHttp(String.valueOf(cinemaId),String.valueOf(movieId));
        //点击事件
        myAdapter.setListener(new MyAdapterDetails.ItemClickListener() {

            @Override
            public void onItemClick(int position) {
                //获取到当前位置的bean对象
                Detailsbean.Resultbean resultbean = result.get(position);
                //获取banner中的电影名
                //跳转
                Intent intent1 = new Intent(context,SelectedSetActivity.class);
                //传递对象
                intent1.putExtra("result",resultbean);
                intent1.putExtra("name",cinemaname);//影院名
                intent1.putExtra("address",address);//影院的地址
                intent1.putExtra("movie_name",name);//电影的名称
                ((PlayCinemaActivity)context).startActivity(intent1);
            }
        });
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PlayCinemaActivity)context).finish();
            }
        });
    }

    @Override
    public void successnetwork() {
        super.successnetwork();
        doHttp(String.valueOf(cinemaId),String.valueOf(movieId));
    }

    //排期数据
    private void doHttp(String cinemasId, String mid) {
        String url1 = "/movieApi/movie/v1/findMovieScheduleList";
        Map<String, String> map = new HashMap<>();
        map.put("cinemasId", cinemasId);
        map.put("movieId", mid);
        new HttpUtil(context).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Detailsbean bean = new Gson().fromJson(data, Detailsbean.class);
                result = bean.getResult();
                myAdapter.setList(result);
                LinearLayoutManager s = new LinearLayoutManager(context);
                s.setOrientation(LinearLayoutManager.VERTICAL);
                film_recycler.setLayoutManager(s);
                film_recycler.setAdapter(myAdapter);
            }

            @Override
            public void fail(String data) {

            }

            @Override
            public void notNetwork(View data) {

            }
        }).get(url1, map,null,"PlayCinemaDetails",true,true);

    }
}
