package com.bw.movie.presenter_activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.DetailsFilmActivity;
import com.bw.movie.model.Details;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class DetailsFilmActivityPresenter extends AppDelage {

    private TextView mTitle,mBuy;
    private SimpleDraweeView mImg;
    private String id;
    private Details details;
    private ImageView mFocus;
    private boolean followMovie;

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
            doHttp();
        }
    }

    private void details() {
    }
    private void trailer() {
    }
    private void still() {
    }
    private void critics() {
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
                Toast.makeText(context, "取消关注成功", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void fail(String data) {

            }
        });
    }
}
