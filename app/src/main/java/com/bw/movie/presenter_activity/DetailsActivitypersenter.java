package com.bw.movie.presenter_activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.bw.movie.adapter.NearAdepter;
import com.bw.movie.bean.Commentsben;
import com.bw.movie.bean.DetailsBannerBean;
import com.bw.movie.bean.Detailsbean;
import com.bw.movie.bean.Detailsinsidebean;
import com.bw.movie.bean.FilmListData;
import com.bw.movie.bean.Focus;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

/*
 * 推荐影院的排期
 * 2018年11月27日 15:18:30
 * 程丹妮
 * 创建了基本的这个presenter
 * 姜谷蓄修改
 * */
public class DetailsActivitypersenter extends AppDelage implements View.OnClickListener {

    private SimpleDraweeView imageView;
    private TextView name;
    private TextView detailsname;
    private String name1;
    private String address;
    private String logo;
    private Detailsinsidebean.Resultbean result6;
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
    private RelativeLayout layout;
    private List<DetailsBannerBean.ResultBean> bannerBeanResult;
    private String movie_name;
    private int i;
    private RecyclerView view;

    private int cinemasId1;
    private TextView textView;
    private TextView start_register;
    private TextView start_login;
    private ImageView img;
    private ImageView img1;
    private LinearLayout linear;
    private EditText text;
    private TextView textView1;


    @Override
    public int getLayoutId() {
        return R.layout.activity_details;
    }

    @Override
    public void initData() {
        super.initData();
        //persenter页面控件
        imageView = (SimpleDraweeView) get(R.id.activity_detailss);
        img1 = (ImageView) get(R.id.activity_img1);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DetailsActivity) context).finish();
            }
        });
        linear = get(R.id.details_linear);
        text =(EditText) get(R.id.details_text);
        //影院的控件
        img = (ImageView) get(R.id.activity_img);
        textView1 = (TextView) get(R.id.line_details);
        start_register = (TextView) get(R.id.start_register);
        start_login = (TextView) get(R.id.start_login);
        start_register.setOnClickListener(this);
        start_login.setOnClickListener(this);
        //属性动画控件
        view = (RecyclerView) get(R.id.activity_recyclertails);
        layout = (RelativeLayout) get(R.id.start_ctrl);
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

        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        },R.id.details_send);
        recyclerView = (RecyclerView) get(R.id.activity_scheduling);
        name = (TextView) get(R.id.activity_name);
        detailsname = (TextView) get(R.id.activity_detailsname);
        //获取值
        Intent intent = ((DetailsActivity) context).getIntent();
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
        //轮播的条目点击事件
        myAdapterFilmBanner.setListener(new FilmDetailsAdapterBanner.RecyclerItemListener() {
            @Override
            public void onClick(int position) {
                i = position;
                mRecyclerCoverFlow.scrollToPosition(position);
            }

            @Override
            public void getmovieId(int movieId) {
                mid = movieId;
                Log.i("aaaaaaaaa", mid + "------");
                doHttp(String.valueOf(cinemasId), String.valueOf(mid));
            }
        });
        //轮播图的滑动监听
        mRecyclerCoverFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                mid =  bannerBeanResult.get(position).getId();
                doHttp(String.valueOf(cinemasId), String.valueOf(mid));
            }
        });
        myAdapterDetails = new MyAdapterDetails(context);
        //请求轮播数据
        doHttpBanner();
        //排期数据
        doHttp(String.valueOf(cinemasId), "16");
        doHttpetails();
        //点击事件
        myAdapterDetails.setListener(new MyAdapterDetails.ItemClickListener() {

            @Override
            public void onItemClick(int position) {
                //获取到当前位置的bean对象
                Detailsbean.Resultbean resultbean = result.get(position);
                //获取banner中的电影名
                //跳转
                Intent intent1 = new Intent(context, SelectedSetActivity.class);
                //传递对象
                movie_name = bannerBeanResult.get(i).getName();
                intent1.putExtra("result", resultbean);
                intent1.putExtra("name", name1);//影院名
                intent1.putExtra("address", address);//影院的地址
                intent1.putExtra("movie_name", movie_name);//电影的名称
                ((DetailsActivity) context).startActivity(intent1);
            }
        });
    }

    @Override
    public void successnetwork() {
        super.successnetwork();
        //请求轮播数据
        doHttpBanner();
        //排期数据
        doHttp(String.valueOf(cinemasId), "16");
        doHttpetails();
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

    private void send() {
        String s = text.getText().toString();
        if(TextUtils.isEmpty(s)){
            Toast.makeText(context, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }
        if(SharedPreferencesUtils.getBoolean(context,"isLogin")){
            int userId = SharedPreferencesUtils.getInt(context, "userId");
            String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
            Map<String,String> mapform = new HashMap<>();
            mapform.put("cinemaId",result6.getId()+"");
            mapform.put("commentContent",s);
            Map<String,String> maphead = new HashMap<>();
            maphead.put("userId",userId+"");
            maphead.put("sessionId",sessionId);
            new HttpUtil(context).result(new HttpUtil.HttpListener() {
                @Override
                public void success(String data) {
                    Gson gson = new Gson();
                    Focus focus = gson.fromJson(data, Focus.class);
                    if(focus.getStatus().equals("0000")){
                        Toast.makeText(context, "评论成功", Toast.LENGTH_SHORT).show();
                        doHttpcomments();
                    }else{
                        Toast.makeText(context, "评论失败", Toast.LENGTH_SHORT).show();
                    }
                    text.setText("");
                }

                @Override
                public void fail(String data) {

                }

                @Override
                public void notNetwork(View data) {

                }
            }).postHead("/movieApi/cinema/v1/verify/cinemaComment",mapform,maphead,"DetailsFocus",true,true);
        }else{
            Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
        }

    }


    //影院的详情
    private void doHttpetails() {
        String url2 = "http://mobile.bwstudent.com/movieApi/cinema/v1/findCinemaInfo";
        Map<String, String> map = new HashMap<>();
        map.put("cinemaId", cinemasId + "");
        new HttpUtil(context).result(new HttpUtil.HttpListener() {



            @Override
            public void success(String data) {
                Detailsinsidebean detailsBean = new Gson().fromJson(data, Detailsinsidebean.class);
                result6 = detailsBean.getResult();
                ArrayList<Detailsinsidebean.Resultbean> resultbeans = new ArrayList<>();
                resultbeans.add(result6);
                Intent intent = ((DetailsActivity) context).getIntent();
                String address = intent.getStringExtra("address");
                String phone1 = intent.getStringExtra("phone");
                String vehicleRoute = intent.getStringExtra("vehicleRoute");
                String content = intent.getStringExtra("content");
                cinemasId1 = intent.getIntExtra("cinemasId", 2);
                LinearLayoutManager s = new LinearLayoutManager(context);
                s.setOrientation(LinearLayoutManager.VERTICAL);
                view.setLayoutManager(s);

                MyAdapterDetailsinside myAdapterDetailsinside = new MyAdapterDetailsinside(context, resultbeans);
                view.setAdapter(myAdapterDetailsinside);
            }

            @Override
            public void fail(String data) {

            }

            @Override
            public void notNetwork(View data) {

            }
        }).get(url2, map, null,"Detailsinsi",true,true);
    }

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
                myAdapterDetails.setList(result);
                LinearLayoutManager s = new LinearLayoutManager(context);
                s.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(s);
                recyclerView.setAdapter(myAdapterDetails);
            }

            @Override
            public void fail(String data) {

            }

            @Override
            public void notNetwork(View data) {

            }
        }).get(url1, map, null,"DetailsDetails",true,true);
        myAdapterDetails.notifyDataSetChanged();
    }

    //请求轮播数据
    private void doHttpBanner() {
        String url = "/movieApi/movie/v1/findMovieListByCinemaId";
        Map<String, String> map = new HashMap<>();
        map.put("cinemaId", String.valueOf(cinemasId));
        new HttpUtil(context).result(new HttpUtil.HttpListener() {
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

            @Override
            public void notNetwork(View data) {

            }
        }).get(url, map, null,"DetailsBanner",true,true);
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
            case R.id.start_login:
                linear.setVisibility(View.GONE);
                start_register.setBackgroundResource(R.drawable.almy_details_false);
                start_login.setBackgroundResource(R.drawable.my_details_true);
                doHttpetails();
                break;
            case R.id.start_register:
                linear.setVisibility(View.VISIBLE);
                start_login.setBackgroundResource(R.drawable.almy_details_false);
                start_register.setBackgroundResource(R.drawable.my_details_true);
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
        new HttpUtil(context).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Commentsben bean = new Gson().fromJson(data, Commentsben.class);
                List<Commentsben.Resultbean> result = bean.getResult();
                MyAdapterComments myAdapterComments = new MyAdapterComments(context, result);
                LinearLayoutManager s = new LinearLayoutManager(context);
                s.setOrientation(LinearLayoutManager.VERTICAL);
                view.setLayoutManager(s);
                view.setAdapter(myAdapterComments);
            }

            @Override
            public void fail(String data) {

            }

            @Override
            public void notNetwork(View data) {

            }
        }).get(url3, map, null,"DetailsComment",true,true);
    }


}
