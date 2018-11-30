package com.bw.movie.presenter_activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.DetailsActivity;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.activity.SelectedSetActivity;
import com.bw.movie.adapter.FilmDetailsAdapterBanner;
import com.bw.movie.adapter.MyAdapterDetails;
import com.bw.movie.adapter.MyAdapterFilmBanner;
import com.bw.movie.adapter.MyAdapterFilmList;
import com.bw.movie.adapter.NearAdepter;
import com.bw.movie.bean.DetailsBannerBean;
import com.bw.movie.bean.Detailsbean;
import com.bw.movie.model.FilmListData;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import recycler.coverflow.RecyclerCoverFlow;

public class DetailsActivitypersenter extends AppDelage {

    private SimpleDraweeView imageView;
    private TextView name;
    private TextView detailsname;
    private String name1;
    private String address;
    private String logo;

    private RecyclerView mListView;
    private MyAdapterFilmList myAdapterFilmList;
    private List<String> titles = new ArrayList<>();
    private List<FilmListData> list = new ArrayList<>();
    private List<String> urls = new ArrayList<>();
    private RecyclerCoverFlow mRecyclerCoverFlow;
    private FilmDetailsAdapterBanner myAdapterFilmBanner;
    private RecyclerView recyclerView;
    private int cinemasId;
    private int mid;
    private List<Detailsbean.Resultbean> result = new ArrayList<>();
    private MyAdapterDetails myAdapterDetails;
    private List<DetailsBannerBean.ResultBean> bannerBeanResult;
    private String movie_name;
    private int i;

    @Override
    public int getLayoutId() {
        return R.layout.activity_details;
    }

    @Override
    public void initData() {
        super.initData();
        imageView = (SimpleDraweeView) get(R.id.activity_detailss);
        recyclerView = (RecyclerView) get(R.id.activity_scheduling);
        name = (TextView) get(R.id.activity_name);
        detailsname = (TextView) get(R.id.activity_detailsname);
        final Intent intent = ((DetailsActivity) context).getIntent();
        //获取值
        name1 = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        logo = intent.getStringExtra("logo");
        cinemasId = intent.getIntExtra("cinemasId", 2);
        imageView.setImageURI(Uri.parse(logo));
        name.setText(name1);
        detailsname.setText(address);

        //轮播图
        mRecyclerCoverFlow = (RecyclerCoverFlow) get(R.id.film_list_recyler);
        myAdapterFilmBanner = new FilmDetailsAdapterBanner(context);
        mRecyclerCoverFlow.setAdapter(myAdapterFilmBanner);
        myAdapterFilmBanner.setListener(new FilmDetailsAdapterBanner.RecyclerItemListener() {
            @Override
            public void onClick(int position) {
                i = position;
                mRecyclerCoverFlow.scrollToPosition(position);
            }

            @Override
            public void getmovieId(int movieId) {
                mid = movieId;
                Log.i("aaaaaaaaa",mid+"------");
                doHttp(String.valueOf(cinemasId),String.valueOf(mid));

            }
        });
        myAdapterDetails = new MyAdapterDetails(context);
        //请求轮播数据
        doHttpBanner();
        Log.i("ccccccccc",cinemasId+"-------");
        //排期数据
        doHttp(String.valueOf(cinemasId),"16");
        //点击事件
        myAdapterDetails.setListener(new MyAdapterDetails.ItemClickListener() {

            @Override
            public void onItemClick(int position) {
                //获取到当前位置的bean对象
                Detailsbean.Resultbean resultbean = result.get(position);
                //获取banner中的电影名
                //跳转
                Intent intent1 = new Intent(context,SelectedSetActivity.class);
                //传递对象
                movie_name = bannerBeanResult.get(i).getName();
                intent1.putExtra("result",resultbean);
                intent1.putExtra("name",name1);//影院名
                intent1.putExtra("address",address);//影院的地址
                intent1.putExtra("movie_name",movie_name);//电影的名称
                ((DetailsActivity)context).startActivity(intent1);
            }
        });


    }

    private void doHttp(String cinemasId,String mid) {
        String url1 = "/movieApi/movie/v1/findMovieScheduleList";
        Map<String,String> map = new HashMap<>();
        map.put("cinemasId", cinemasId);
        map.put("movieId", mid);
        new HttpUtil().get(url1,map,null).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Detailsbean bean = new Gson().fromJson(data, Detailsbean.class);
                result = bean.getResult();
                myAdapterDetails.setList(result);
                LinearLayoutManager s = new LinearLayoutManager(context);
                s.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(s);
                recyclerView.setAdapter(myAdapterDetails);
            }

            @Override
            public void fail(String data) {

            }
        });
        myAdapterDetails.notifyDataSetChanged();
    }

    //请求轮播数据
    private void doHttpBanner() {
        String url = "/movieApi/movie/v1/findMovieListByCinemaId";
        Map<String, String> map = new HashMap<>();
        map.put("cinemaId", String.valueOf(cinemasId));
        new HttpUtil().get(url, map,null).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                DetailsBannerBean detailsBannerBean = gson.fromJson(data, DetailsBannerBean.class);
                bannerBeanResult = detailsBannerBean.getResult();
                if (bannerBeanResult.size() == 0) {
                    doHttpBanner();
                }
                myAdapterFilmBanner.setList(bannerBeanResult);
            }

            @Override
            public void fail(String data) {

            }
        });
    }

    private Context context;

    @Override
    public void getContext(Context context) {
        this.context = context;
    }
}
