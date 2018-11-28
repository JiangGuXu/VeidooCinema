package com.bw.movie.presenter_fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.mvp.view.AppDelage;
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

        my_head_icon = get(R.id.my_head_icon);
        my_head_icon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击头像未登录跳转到登录页面  登录怎么都不做
            case R.id.my_head_icon:
                Boolean isLogin = SharedPreferencesUtils.getBoolean(context, "isLogin");
                if(!isLogin){
                    Intent intent = new Intent(context,LoginActivity.class);
                    ((MainActivity)context).startActivity(intent);
                }
                break;
        }
    }
}
