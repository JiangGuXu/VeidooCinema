package com.bw.movie.presenter_fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.activity.UserAttentionActivity;
import com.bw.movie.activity.UserInfoActivity;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.encrypt.Base64;
import com.bw.movie.utils.encrypt.Base64EncryptUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;

/*
 * 我的页面presenter
 * 2018年11月27日 15:18:42
 * 焦浩康
 * 创建了基本的这个presenter
 *
 * 2018年11月28日 09:28:49
 * 焦浩康
 * 添加了点击头像判断的事件
 *
 *
 * */
public class MyFragmentPresenter extends AppDelage implements View.OnClickListener {

    private Context context;
    private ImageView my_head_icon;
    private RelativeLayout my_messiage_layout;
    private Boolean isLogin;
    private RelativeLayout my_attention_layout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();

        //头像图片
        my_head_icon = get(R.id.my_head_icon);
        my_head_icon.setOnClickListener(this);


        //我的信息布局 图片加文字
        my_messiage_layout = get(R.id.my_messiage_layout);
        my_messiage_layout.setOnClickListener(this);


        //我的关注布局 图片加文字
        my_attention_layout = get(R.id.my_attention_layout);
        my_attention_layout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击头像未登录跳转到登录页面  登录怎么都不做
            case R.id.my_head_icon:
                isLogin = SharedPreferencesUtils.getBoolean(context, "isLogin");
                if (!isLogin) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    ((MainActivity) context).startActivity(intent);
                }
                break;

            //点击我的信息未登录吐司 登录状态 跳转到个人信息页面
            case R.id.my_messiage_layout:
                isLogin = SharedPreferencesUtils.getBoolean(context, "isLogin");
                if (!isLogin) {
                    Toast.makeText(context, "您还没有登录哦~", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, UserInfoActivity.class);
                    ((MainActivity) context).startActivity(intent);
                }
                break;


            //点击我的关注跳转到关注页面
            case R.id.my_attention_layout:
                Boolean isLogin = SharedPreferencesUtils.getBoolean(context, "isLogin");

                if (isLogin) {
                    Intent intent = new Intent(context, UserAttentionActivity.class);
                    ((MainActivity) context).startActivity(intent);
                } else {
                    Toast.makeText(context, "您还没有登录哦~快去登录吧~", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
