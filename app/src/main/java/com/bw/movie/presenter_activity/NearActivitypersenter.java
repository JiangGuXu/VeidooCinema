package com.bw.movie.presenter_activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.DetailsActivity;
import com.bw.movie.activity.NearActivity;
import com.bw.movie.activity.SelectedSetActivity;
import com.bw.movie.adapter.FilmDetailsAdapterBanner;
import com.bw.movie.adapter.MyAdapterComments;
import com.bw.movie.adapter.MyAdapterDetails;
import com.bw.movie.adapter.MyAdapterDetailsinside;
import com.bw.movie.adapter.MyAdapterFilmBanner;
import com.bw.movie.adapter.MyAdapterFilmList;
import com.bw.movie.adapter.MyAdapterNearcinma;
import com.bw.movie.bean.Commentsben;
import com.bw.movie.bean.DetailsBannerBean;
import com.bw.movie.bean.Detailsbean;
import com.bw.movie.bean.Detailsinsidebean;
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
/*
 * 附近影院排期页面presenter
 * 2018年11月29日 15:18:30
 * 程丹妮
 * 创建了基本的这个presenter
 * */
public class NearActivitypersenter extends AppDelage implements View.OnClickListener{
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
    private RecyclerView view;
    private RelativeLayout layout;
    private int cinemasId1;
    private TextView start_register1;
    private TextView start_login1;
    private ImageView img;
    private List<DetailsBannerBean.ResultBean> bannerBeanResult;
    private String movie_name;
    private int i;
    private ImageView img1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_plot;
    }

    @Override
    public void initData() {
        super.initData();
        //persenter页面控件
        imageView = (SimpleDraweeView) get(R.id.activity_detailss);
        recyclerView = (RecyclerView) get(R.id.activity_scheduling);
        name = (TextView) get(R.id.activity_name);
        detailsname = (TextView) get(R.id.activity_detailsname);
        img = (ImageView)  get(R.id.activity_img);
        img1 = (ImageView)get(R.id.activity_img1);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NearActivity)context).finish();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintShopCar();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShopCar();
            }
        });
        //详情 评论 控件
        start_register1 = (TextView) get(R.id.start_register1);
        start_login1 = (TextView) get(R.id.start_login1);
        start_register1.setOnClickListener(this);
        start_login1.setOnClickListener(this);
        //属性动画控件
        view = (RecyclerView) get(R.id.activity_recyclertails);
        layout = (RelativeLayout) get(R.id.start_ctrl);


        Intent intent = ((NearActivity) context).getIntent();
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
                i=position;
                mRecyclerCoverFlow.scrollToPosition(position);
            }

            @Override
            public void getmovieId(int movieId) {
                mid = movieId;
                Log.i("aaaaaaaaa", mid + "------");
                doHttp(String.valueOf(cinemasId), String.valueOf(mid));

            }
        });
        myAdapterDetails = new MyAdapterDetails(context);
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
                ((NearActivity)context).startActivity(intent1);
            }
        });
        //请求轮播数据
        doHttpBanner();
        Log.i("ccccccccc", cinemasId + "-------");
        //排期数据
        doHttp(String.valueOf(cinemasId), "6");
        //详情
        doHttnear();
    }

    //打开
    private void showShopCar() {
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layout, "translationY", heightPixels, 0);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        layout.setVisibility(View.VISIBLE);
    }

    //关闭
    private void hintShopCar() {
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layout, "translationY", 0, heightPixels);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.setVisibility(View.GONE);
            }
        }, 1000);

    }

    //详情
    private void doHttnear() {
        String url2 = "http://mobile.bwstudent.com/movieApi/cinema/v1/findCinemaInfo";
        Map<String, String> map = new HashMap<>();
        map.put("cinemaId", cinemasId + "");
        new HttpUtil().get(url2, map, null).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Detailsinsidebean detailsBean = new Gson().fromJson(data, Detailsinsidebean.class);
                Detailsinsidebean.Resultbean result = detailsBean.getResult();
                ArrayList<Detailsinsidebean.Resultbean> resultbeans = new ArrayList<>();
                resultbeans.add(result);
                Intent intent = ((NearActivity) context).getIntent();
                String address = intent.getStringExtra("address");
                String phone1 = intent.getStringExtra("phone");
                String vehicleRoute = intent.getStringExtra("vehicleRoute");
                String content = intent.getStringExtra("content");

                cinemasId = intent.getIntExtra("cinemasId", 2);
                LinearLayoutManager s = new LinearLayoutManager(context);
                s.setOrientation(LinearLayoutManager.VERTICAL);
                view.setLayoutManager(s);
                MyAdapterNearcinma myAdapterDetailsinside = new MyAdapterNearcinma(context, resultbeans);
                view.setAdapter(myAdapterDetailsinside);
            }

            @Override
            public void fail(String data) {

            }
        });
    }

    //排期数据
    private void doHttp(String cinemasId, String mid) {
        String url1 = "/movieApi/movie/v1/findMovieScheduleList";
        Map<String, String> map = new HashMap<>();
        map.put("cinemasId", cinemasId);
        map.put("movieId", mid);
        new HttpUtil().get(url1, map,null).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Detailsbean bean = new Gson().fromJson(data, Detailsbean.class);
                result = bean.getResult();
                myAdapterDetails.setList(result);
                LinearLayoutManager s = new LinearLayoutManager(context);
                s.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(s);
                recyclerView.setAdapter(myAdapterDetails);
                myAdapterDetails.notifyDataSetChanged();
            }

            @Override
            public void fail(String data) {

            }
        });

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
                if (result.size() == 0) {
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

    //详情评论点击
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_login1:
                start_register1.setBackgroundResource(R.drawable.my_attention_title_shape_false);
                start_login1.setBackgroundResource(R.drawable.my_attention_title_shape_true);
                doHttnear();
                break;
            case R.id.start_register1:
                start_login1.setBackgroundResource(R.drawable.my_attention_title_shape_false);
                start_register1.setBackgroundResource(R.drawable.my_attention_title_shape_true);
                doHttpcomments();
                break;
        }
    }
    //评论
    private void doHttpcomments() {
        String url3 = "/movieApi/cinema/v1/findAllCinemaComment";
        Map<String, String> map = new HashMap<>();
        map.put("cinemaId", String.valueOf(cinemasId));
        map.put("page", "1");
        map.put("count", "6");
        new HttpUtil().get(url3,map,null).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Commentsben bean = new Gson().fromJson(data, Commentsben.class);
                List<Commentsben.Resultbean> result = bean.getResult();
                MyAdapterComments myAdapterComments = new MyAdapterComments(context,result);
                LinearLayoutManager s = new LinearLayoutManager(context);
                s.setOrientation(LinearLayoutManager.VERTICAL);
                view.setLayoutManager(s);
                view.setAdapter(myAdapterComments);

            }

            @Override
            public void fail(String data) {

            }
        });
    }

}