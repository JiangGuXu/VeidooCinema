package com.bw.movie.presenter_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.DetailsFilmActivity;
import com.bw.movie.activity.DialogUtils;
import com.bw.movie.adapter.MyAdapterDetailsCritics;
import com.bw.movie.adapter.MyAdapterDetailsDe;
import com.bw.movie.adapter.MyAdapterDetailsStill;
import com.bw.movie.adapter.MyAdapterDetailsTrailer;
import com.bw.movie.model.Critics;
import com.bw.movie.model.Details;
import com.bw.movie.model.Focus;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsFilmActivityPresenter extends AppDelage {

    private TextView mTitle,mBuy;
    private SimpleDraweeView mImg;
    private String id;
    private Details details;
    private ImageView mFocus;
    private boolean followMovie;
    private RelativeLayout mRelativeLayout;
    private MyAdapterDetailsCritics myAdapterDetailsCritics;
    private EditText text;
    private SimpleDraweeView img_bg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_detailsfilm;
    }
    private Context context;
    @Override
    public void getContext(Context context) {
        this.context=context;
    }

    @Override
    public void initData() {
        super.initData();
        Intent intent = ((DetailsFilmActivity) context).getIntent();
        id = intent.getStringExtra("id");
        mRelativeLayout = get(R.id.details_film_hide);
        img_bg = get(R.id.details_film_bg);
        mTitle = get(R.id.details_film_title);
        mImg = get(R.id.details_film_img1);
        mBuy = get(R.id.details_film_buy);
        mFocus = get(R.id.details_film_focus);
        Onclick();
        doHttp();
    }

    private void Onclick() {
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SharedPreferencesUtils.getBoolean(context,"isLogin")){
                    focus();
                }else{
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }

            }
        },R.id.details_film_focus);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                details();
            }
        },R.id.details_film_details);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trailer();
            }
        },R.id.details_film_trailer);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                still();
            }
        },R.id.details_film_still);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                critics();
            }
        },R.id.details_film_critics);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DetailsFilmActivity)context).finish();
            }
        },R.id.details_film_return);
    }

    private void focus() {
        if( SharedPreferencesUtils.getBoolean(context, "isLogin")){
            int userId = SharedPreferencesUtils.getInt(context, "userId");
            String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
            if(followMovie){
                doHpptFocus (userId,sessionId);
            }else{
                doHttpCancel(userId,sessionId);
            }

        }
    }

    private void details() {
        View view = View.inflate(context,R.layout.details_film,null);
        final DialogUtils dialogUtils = new DialogUtils(context,view);
        dialogUtils.show();
        SimpleDraweeView img = view.findViewById(R.id.details_details_img);
        TextView type = view.findViewById(R.id.details_details_type);
        TextView director = view.findViewById(R.id.details_details_director);
        TextView length = view.findViewById(R.id.details_details_length);
        TextView origin = view.findViewById(R.id.details_details_origin);
        TextView Introduction = view.findViewById(R.id.details_details_Introduction);
        RecyclerView recyclerView = view.findViewById(R.id.details_details_recyler);
        view.findViewById(R.id.details_details_under).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUtils.dismiss();
            }
        });
        img.setImageURI(details.getResult().getImageUrl());
        type.setText(details.getResult().getMovieTypes());
        director.setText(details.getResult().getDirector());
        length.setText(details.getResult().getDuration());
        origin.setText(details.getResult().getPlaceOrigin());
        Introduction.setText(details.getResult().getSummary());
        String starring = details.getResult().getStarring();
        String[] split = starring.split(",");
        MyAdapterDetailsDe myAdapterDetailsDe = new MyAdapterDetailsDe(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapterDetailsDe);
        myAdapterDetailsDe.setList(split);

    }
    private void trailer() {
        View view = View.inflate(context,R.layout.details_trailer,null);
        final DialogUtils dialogUtils = new DialogUtils(context,view);
        dialogUtils.show();
        view.findViewById(R.id.details_trailer_under).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUtils.dismiss();
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.details_trailer_recyler);
        MyAdapterDetailsTrailer myAdapterDetailsTrailer = new MyAdapterDetailsTrailer(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapterDetailsTrailer);
        List<Details.ResultBean.ShortFilmListBean> shortFilmList = details.getResult().getShortFilmList();
        myAdapterDetailsTrailer.setList(shortFilmList);
    }
    private void still() {
        View view = View.inflate(context,R.layout.details_still,null);
        final DialogUtils dialogUtils = new DialogUtils(context,view);
        dialogUtils.show();
        view.findViewById(R.id.details_still_under).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUtils.dismiss();
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.details_still_recyler);
        MyAdapterDetailsStill myAdapterDetailsStill = new MyAdapterDetailsStill(context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(myAdapterDetailsStill);
        List<String> posterList = details.getResult().getPosterList();
        myAdapterDetailsStill.setList(posterList);
    }
    private void critics() {
        View view = View.inflate(context,R.layout.details_critics,null);
        final DialogUtils dialogUtils = new DialogUtils(context,view);
        dialogUtils.show();
        view.findViewById(R.id.details_critics_under).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUtils.dismiss();
            }
        });
        text = view.findViewById(R.id.details_critics_text);
        view.findViewById(R.id.details_critics_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doHttpcomments();
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.details_critics_recyler);
        myAdapterDetailsCritics = new MyAdapterDetailsCritics(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapterDetailsCritics);
        doHttpCritics();

    }

    private void doHttpcomments() {
        if(id!=null){
            if(SharedPreferencesUtils.getBoolean(context,"isLogin")){
                String trim = text.getText().toString().trim();
                Toast.makeText(context, trim+"", Toast.LENGTH_SHORT).show();
                if(TextUtils.isEmpty(trim)){
                    Toast.makeText(context, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                int userId = SharedPreferencesUtils.getInt(context, "userId");
                String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                Map<String,String> map = new HashMap<>();
                map.put("movieId",id);
                map.put("commentContent",trim);
                Map<String,String> mapHead = new HashMap<>();
                mapHead.put("userId",userId+"");
                mapHead.put("sessionId",sessionId);
                new HttpUtil().get("/movieApi/movie/v1/verify/movieComment",map,mapHead).result(new HttpUtil.HttpListener() {
                    @Override
                    public void success(String data) {
                        Toast.makeText(context, data+"", Toast.LENGTH_SHORT).show();
                        Gson gson = new Gson();
                        Focus focus = gson.fromJson(data, Focus.class);
                        if("0000".equals(focus.getStatus())){
                            Toast.makeText(context, "评论成功", Toast.LENGTH_SHORT).show();
                            doHttpCritics();
                            doHttp();
                        }else{
                            Toast.makeText(context, "评论失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void fail(String data) {

                    }
                });
            }else{
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void doHttpCritics() {
        if(id!=null){
            if(SharedPreferencesUtils.getBoolean(context,"isLogin")){
                int userId = SharedPreferencesUtils.getInt(context, "userId");
                String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                Map<String,String> map = new HashMap<>();
                map.put("movieId",id);
                map.put("page","1");
                map.put("count","5");
                Map<String,String> mapHead = new HashMap<>();
                mapHead.put("userId",userId+"");
                mapHead.put("sessionId",sessionId);
                new HttpUtil().get("/movieApi/movie/v1/findAllMovieComment",map,mapHead).result(new HttpUtil.HttpListener() {
                    @Override
                    public void success(String data) {
                        Gson gson = new Gson();
                        Critics critics = gson.fromJson(data, Critics.class);
                        List<Critics.ResultBean> result = critics.getResult();
                        myAdapterDetailsCritics.setList(result);
                    }

                    @Override
                    public void fail(String data) {

                    }
                });
            }else{
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void doHttp() {
        if(id!=null){
            if(SharedPreferencesUtils.getBoolean(context,"isLogin")){
                int userId = SharedPreferencesUtils.getInt(context, "userId");
                String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                Map<String,String> map = new HashMap<>();
                map.put("movieId",id);
                Map<String,String> mapHead = new HashMap<>();
                mapHead.put("userId",userId+"");
                mapHead.put("sessionId",sessionId);
                new HttpUtil().get("/movieApi/movie/v1/findMoviesDetail",map,mapHead).result(new HttpUtil.HttpListener() {
                    @Override
                    public void success(String data) {
                        Gson gson = new Gson();
                        details = gson.fromJson(data, Details.class);
                        mTitle.setText(details.getResult().getName());
                        mImg.setImageURI(details.getResult().getImageUrl());
                        img_bg.setImageURI(details.getResult().getImageUrl());
                        followMovie = details.getResult().isFollowMovie();
                        if(followMovie){
                            mFocus.setImageResource(R.drawable.com_icon_collection_default);
                        }else{
                            mFocus.setImageResource(R.drawable.com_icon_collection_selected);
                        }
                    }

                    @Override
                    public void fail(String data) {

                    }
                });
            }else{
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void doHttpCancel( int userId, String sessionId) {
        Map<String,String> map = new HashMap<>();
        map.put("movieId",details.getResult().getId()+"");
        Map<String,String> mapHead = new HashMap<>();
        mapHead.put("userId",userId+"");
        mapHead.put("sessionId",sessionId);
        new HttpUtil().get("/movieApi/movie/v1/verify/cancelFollowMovie",map,mapHead).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {

                Gson gson = new Gson();
                Focus focus = gson.fromJson(data, Focus.class);
                if("0000".equals(focus.getStatus())){
                    Toast.makeText(context, "取消关注成功", Toast.LENGTH_SHORT).show();
                    doHttp();
                }else{
                    Toast.makeText(context, "取消关注失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void fail(String data) {

            }
        });
    }

    private void doHpptFocus( int userId, String sessionId) {
        Map<String,String> map = new HashMap<>();
        map.put("movieId",details.getResult().getId()+"");
        Map<String,String> mapHead = new HashMap<>();
        mapHead.put("userId",userId+"");
        mapHead.put("sessionId",sessionId);
        new HttpUtil().get( "/movieApi/movie/v1/verify/followMovie",map,mapHead).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                Focus focus = gson.fromJson(data, Focus.class);
                if("0000".equals(focus.getStatus())){
                    Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
                    doHttp();
                }else{
                    Toast.makeText(context, "关注失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void fail(String data) {

            }
        });
    }
}
