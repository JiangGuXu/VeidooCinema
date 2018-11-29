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

import com.bw.movie.R;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.activity.UserAttentionActivity;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.HttpUtils;

import java.util.HashMap;
import java.util.Map;


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


        my_attention_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.my_attention_radiobutton1:
                        radioButton1.setTextColor(Color.parseColor("#FFFFFF"));
                        radioButton2.setTextColor(Color.parseColor("#333333"));

                        Map<String, String> map = new HashMap<>();
                        map.put("page", 1 + "");
                        map.put("count", 10 + "");
                        new HttpUtil().get("/movieApi/movie/v1/verify/findMoviePageList", map).result(new HttpUtil.HttpListener() {
                            @Override
                            public void success(String data) {
                                Log.i("jhktest", "success: " + data);
                            }

                            @Override
                            public void fail(String data) {
                                Log.i("jhktest", "fail: " + data);
                            }
                        });


                        Log.i("jhktest", "onCheckedChanged: " + 111);

                        break;
                    case R.id.my_attention_radiobutton2:
                        radioButton1.setTextColor(Color.parseColor("#333333"));
                        radioButton2.setTextColor(Color.parseColor("#FFFFFF"));
                        Log.i("jhktest", "onCheckedChanged: " + 222);
                        break;
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
