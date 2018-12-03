package com.bw.movie.presenter_activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.ReSetpasswordActivity;
import com.bw.movie.activity.UserInfoActivity;
import com.bw.movie.activity.UserReSertInfoActivity;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;


/**
 * 2018年12月1日 09:35:22
 * 焦浩康
 * 个人信息activity
 * 注解加的有点晚  其实这个activity在几天前就已经创建了
 * <p>
 * 2018年12月1日 09:36:21
 * 焦浩康
 * 正在添加修改个人信息的功能
 */
public class UserInfoActivityPresenter extends AppDelage implements View.OnClickListener {

    private Context context;
    private ImageView user_info_back;
    private SimpleDraweeView user_info_head;
    private TextView user_info_sex;
    private TextView user_info_birthday;
    private TextView user_info_phone;
    private TextView user_info_emil;
    private TextView user_info_name;
    private Boolean isLogin;
    private Button user_info_logout;
    private ImageView user_info_reset_pwd;
    private ImageView user_info_reset_info;


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();


        //页面返回图片
        user_info_back = get(R.id.user_info_back);
        user_info_back.setOnClickListener(this);


        user_info_head = get(R.id.user_info_head);
        user_info_name = get(R.id.user_info_name);
        user_info_sex = get(R.id.user_info_sex);
        user_info_birthday = get(R.id.user_info_birthday);
        user_info_phone = get(R.id.user_info_phone);
        user_info_emil = get(R.id.user_info_emil);

        //重置密码
        user_info_reset_pwd = get(R.id.user_info_reset_pwd);
        user_info_reset_pwd.setOnClickListener(this);


        //退出登录
        user_info_logout = get(R.id.user_info_logout);
        user_info_logout.setOnClickListener(this);


        //修改个人信息
        user_info_reset_info = get(R.id.user_info_reset_info);
        user_info_reset_info.setOnClickListener(this);


        //如果登录状态 给页面控件复制用户信息   其实若没有登录是进不来这个页面的
        isLogin = SharedPreferencesUtils.getBoolean(context, "isLogin");
        if (isLogin) {
            String headpic = SharedPreferencesUtils.getString(context, "headpic");
            String nickname = SharedPreferencesUtils.getString(context, "nickname");
            String sex = SharedPreferencesUtils.getString(context, "sex");
            String birthday = SharedPreferencesUtils.getString(context, "birthday");
            String phone = SharedPreferencesUtils.getString(context, "phone");
            user_info_head.setImageURI(headpic.trim());
            user_info_name.setText(nickname);
            user_info_sex.setText(sex.trim());
            user_info_phone.setText(phone);
            user_info_birthday.setText(birthday);
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击返回按钮的时退出该activity返回到主activity
            case R.id.user_info_back:
                ((UserInfoActivity) context).finish();
                break;

            //点击退出登录
            case R.id.user_info_logout:
                SharedPreferencesUtils.putBoolean(context, "isLogin", false);
                Boolean isLogin = SharedPreferencesUtils.getBoolean(context, "isLogin");
                if (!isLogin) {
                    Toast.makeText(context, "退出登录成功", Toast.LENGTH_SHORT).show();
                    ((UserInfoActivity) context).finish();
                } else {
                    Toast.makeText(context, "退出成功失败", Toast.LENGTH_SHORT).show();
                }
                break;

            //点击重置密码
            case R.id.user_info_reset_pwd:
                Intent intent = new Intent(context, ReSetpasswordActivity.class);
                ((UserInfoActivity) context).startActivity(intent);
                ((UserInfoActivity) context).finish();
                break;


            //点击修改个人信息
            case R.id.user_info_reset_info:
                if (SharedPreferencesUtils.getBoolean(context, "isLogin")) {
                    Intent intent1 = new Intent(context, UserReSertInfoActivity.class);
                    ((UserInfoActivity) context).startActivity(intent1);
                } else {
                    Toast.makeText(context, "您还没有登录哦~", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }


    @Override
    public void resume() {
        super.resume();
        //如果登录状态 给页面控件复制用户信息   其实若没有登录是进不来这个页面的
        isLogin = SharedPreferencesUtils.getBoolean(context, "isLogin");
        if (isLogin) {
            String headpic = SharedPreferencesUtils.getString(context, "headpic");
            String nickname = SharedPreferencesUtils.getString(context, "nickname");
            String sex = SharedPreferencesUtils.getString(context, "sex");
            String birthday = SharedPreferencesUtils.getString(context, "birthday");
            String phone = SharedPreferencesUtils.getString(context, "phone");
            user_info_head.setImageURI(headpic.trim());
            user_info_name.setText(nickname);
            user_info_sex.setText(sex.trim());
            user_info_phone.setText(phone);
            user_info_birthday.setText(birthday);
        }
    }
}
