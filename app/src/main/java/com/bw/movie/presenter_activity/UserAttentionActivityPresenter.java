package com.bw.movie.presenter_activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.activity.UserAttentionActivity;
import com.bw.movie.adapter.UserAttentionCinemaAdapter;
import com.bw.movie.adapter.UserAttentionFilmAdapter;
import com.bw.movie.bean.UserAttentionCinemaAdapterBean;
import com.bw.movie.bean.UserAttentionFilmAdapterBean;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.HttpUtils;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.http.HeaderMap;


/**
 * 2018年11月28日 18:16:37
 * 焦浩康
 * 这是我的关注  activity
 */
public class UserAttentionActivityPresenter extends AppDelage implements View.OnClickListener {

    private Context context;
    private RadioGroup my_attention_radiogroup;
    private RecyclerView my_attention_recyclerview;
    private ImageView my_attention_back;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private UserAttentionFilmAdapter userAttentionFilmAdapter;
    private UserAttentionCinemaAdapter userAttentionCinemaAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_attention;
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();
        my_attention_radiogroup = get(R.id.my_attention_radiogroup);
        my_attention_recyclerview = get(R.id.my_attention_recyclerview);
        my_attention_back = get(R.id.my_attention_back);

        radioButton1 = get(R.id.my_attention_radiobutton1);
        radioButton2 = get(R.id.my_attention_radiobutton2);

        my_attention_back.setOnClickListener(this);

        my_attention_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        userAttentionFilmAdapter = new UserAttentionFilmAdapter(context);
        userAttentionCinemaAdapter = new UserAttentionCinemaAdapter(context);

        my_attention_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int userId = SharedPreferencesUtils.getInt(context, "userId");
                String sessionId = SharedPreferencesUtils.getString(context, "sessionId");

                Boolean isLogin = SharedPreferencesUtils.getBoolean(context, "isLogin");
                if (isLogin) {
                    switch (checkedId) {
                        case R.id.my_attention_radiobutton1:
                            radioButton1.setTextColor(Color.parseColor("#FFFFFF"));
                            radioButton2.setTextColor(Color.parseColor("#333333"));

                            my_attention_recyclerview.setAdapter(userAttentionFilmAdapter);

                            Map<String, String> parameterMap1 = new HashMap<>();
                            parameterMap1.put("page", 1 + "");
                            parameterMap1.put("count", 10 + "");

                            HashMap<String, String> headMap1 = new HashMap<>();
                            headMap1.put("userId", userId + "");
                            headMap1.put("sessionId", sessionId);

                            new HttpUtil(context).result(new HttpUtil.HttpListener() {
                                @Override
                                public void success(String data) {
                                    Gson gson = new Gson();
                                    UserAttentionFilmAdapterBean userAttentionFilmAdapterBean = gson.fromJson(data, UserAttentionFilmAdapterBean.class);
                                    List<UserAttentionFilmAdapterBean.ResultBean> result = userAttentionFilmAdapterBean.getResult();
                                    userAttentionFilmAdapter.setList(result);
                                }

                                @Override
                                public void fail(String data) {
                                    Toast.makeText(context, "电影请求失败", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void notNetwork(View data) {

                                }
                            }).get("/movieApi/movie/v1/verify/findMoviePageList", parameterMap1, headMap1,"UserAttentionUserAttentionFilm",true,true);



                            break;
                        case R.id.my_attention_radiobutton2:
                            radioButton1.setTextColor(Color.parseColor("#333333"));
                            radioButton2.setTextColor(Color.parseColor("#FFFFFF"));

                            my_attention_recyclerview.setAdapter(userAttentionCinemaAdapter);

                            Map<String, String> parameterMap2 = new HashMap<>();
                            parameterMap2.put("page", 1 + "");
                            parameterMap2.put("count", 10 + "");

                            HashMap<String, String> headMap2 = new HashMap<>();
                            headMap2.put("userId", userId + "");
                            headMap2.put("sessionId", sessionId);
                            HashMap<String, String> heanMap = new HashMap<>();

                            new HttpUtil(context).result(new HttpUtil.HttpListener() {
                                @Override
                                public void success(String data) {
                                    Log.i("jhktest", "success: " + data);
                                    Gson gson = new Gson();
                                    UserAttentionCinemaAdapterBean userAttentionCinemaAdapterBean = gson.fromJson(data, UserAttentionCinemaAdapterBean.class);
                                    List<UserAttentionCinemaAdapterBean.ResultBean> result = userAttentionCinemaAdapterBean.getResult();
                                    userAttentionCinemaAdapter.setList(result);
                                }
                                @Override
                                public void fail(String data) {
                                    Log.i("jhktest", "fail: " + data);
                                }

                                @Override
                                public void notNetwork(View data) {

                                }
                            }).get("/movieApi/cinema/v1/verify/findCinemaPageList", parameterMap2, headMap2,"UserAttentionUserAttentionCinema",true,true);
                            break;
                    }
                } else {
                    Toast.makeText(context, "您还没有登陆哦~你怎麽進來的？", Toast.LENGTH_SHORT).show();
                }
            }
        });
        radioButton1.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_attention_back:
                ((UserAttentionActivity) context).finish();
                break;
        }
    }
}
