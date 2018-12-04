package com.bw.movie.presenter_fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.activity.UserAttentionActivity;
import com.bw.movie.activity.UserFeedBackActivity;
import com.bw.movie.activity.UserInfoActivity;
import com.bw.movie.activity.UserSystemMessagesActivity;
import com.bw.movie.bean.UserSystemCountBean;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.encrypt.Base64;
import com.bw.movie.utils.encrypt.Base64EncryptUtil;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.HashMap;

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
    private SimpleDraweeView my_head_icon;
    private RelativeLayout my_messiage_layout;
    private Boolean isLogin;
    private RelativeLayout my_attention_layout;
    private TextView my_nickname_text;
    private RelativeLayout my_feedback_layout;
    private ImageView my_remind;
    private TextView my_icon_remind_message_count;
    private RelativeLayout my_icon_remind_relativelayout;

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

        //系统消息
        my_remind = get(R.id.my_remind);
        my_remind.setOnClickListener(this);


        my_icon_remind_message_count = get(R.id.my_icon_remind_message_count);
        my_icon_remind_relativelayout = get(R.id.my_icon_remind_relativelayout);


        my_nickname_text = get(R.id.my_nickname_text);


        //头像图片
        my_head_icon = get(R.id.my_head_icon);
        my_head_icon.setOnClickListener(this);


        //我的信息布局 图片加文字
        my_messiage_layout = get(R.id.my_messiage_layout);
        my_messiage_layout.setOnClickListener(this);


        //我的关注布局 图片加文字
        my_attention_layout = get(R.id.my_attention_layout);
        my_attention_layout.setOnClickListener(this);


        //意见反馈布局 图片加文字
        my_feedback_layout = get(R.id.my_feedback_layout);
        my_feedback_layout.setOnClickListener(this);
    }

    @Override
    public void resume() {
        super.resume();
        setHeadAndNickname();
        doHttpForCount("/movieApi/tool/v1/verify/findUnreadMessageCount");
    }


    private void doHttpForCount(String s) {
        HashMap<String, String> paramsMap = new HashMap<>();

        HashMap<String, String> headMap = new HashMap<>();
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        headMap.put("userId", userId + "");
        headMap.put("sessionId", sessionId);
        new HttpUtil().get(s, paramsMap, headMap).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {

                Log.i("jhktest", "successcount: " + data);
                if (data.contains("成功")) {
                    Gson gson = new Gson();
                    UserSystemCountBean userSystemCountBean = gson.fromJson(data, UserSystemCountBean.class);
                    int count = userSystemCountBean.getCount();
                    if (count > 0) {
                        my_icon_remind_relativelayout.setVisibility(View.VISIBLE);
                        my_icon_remind_message_count.setText(count + "");
                    } else {
                        my_icon_remind_relativelayout.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void fail(String data) {
                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
            }
        });
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

            //点击意见反馈跳转到意见反馈页面
            case R.id.my_feedback_layout:
                if (SharedPreferencesUtils.getBoolean(context, "isLogin")) {
                    Intent intent = new Intent(context, UserFeedBackActivity.class);
                    ((MainActivity) context).startActivity(intent);
                } else {
                    Toast.makeText(context, "您还没有登录哦~", Toast.LENGTH_SHORT).show();
                }
                break;


            //点击消息图片 跳转到我的消息页面
            case R.id.my_remind:
                if (SharedPreferencesUtils.getBoolean(context, "isLogin")) {
                    Intent intent = new Intent(context, UserSystemMessagesActivity.class);
                    ((MainActivity) context).startActivity(intent);
                } else {
                    Toast.makeText(context, "您还没有登录哦~", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public void setHeadAndNickname() {
        //判断是否登录  更改头像和 姓名
        Boolean isLogin = SharedPreferencesUtils.getBoolean(context, "isLogin");
        if (isLogin) {
            String nickname = SharedPreferencesUtils.getString(context, "nickname");
            String headpic = SharedPreferencesUtils.getString(context, "headpic");
            my_nickname_text.setText(nickname);
            my_head_icon.setImageURI(headpic);
        } else {
            my_nickname_text.setText("请登录");
            my_head_icon.setImageURI("res://com.bw.movie/" + R.drawable.my_head_icon);
        }
    }


}
