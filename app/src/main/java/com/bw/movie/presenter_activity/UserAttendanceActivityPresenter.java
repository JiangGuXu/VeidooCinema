package com.bw.movie.presenter_activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.UserAttendanceActivity;
import com.bw.movie.bean.UserAttendanceBean1;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 2018年12月8日 09:02:54
 * 焦浩康
 * 用户签到activity
 */
public class UserAttendanceActivityPresenter extends AppDelage implements View.OnClickListener {

    private Context context;
    private ImageView user_attendance_back;
    private TextView user_attendance_geto_attendance;
    private TextView user_attendance_integral;
    private ImageView user_attendance_geto_attendance_img;
    private boolean flag = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_attendance;
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();


        //去签到图片
        user_attendance_geto_attendance_img = get(R.id.user_attendance_geto_attendance_img);
        user_attendance_geto_attendance_img.setOnClickListener(this);

        //签到否
        user_attendance_geto_attendance = get(R.id.user_attendance_geto_attendance);


        //积分
        user_attendance_integral = get(R.id.user_attendance_integral);


        //返回按钮
        user_attendance_back = get(R.id.user_attendance_back);
        user_attendance_back.setOnClickListener(this);


        HashMap<String, String> partMap = new HashMap<>();


        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        HashMap<String, String> headMap = new HashMap<>();
        headMap.put("userId", userId + "");
        headMap.put("sessionId", sessionId);

        getIntegral(partMap, headMap);


    }


    public void getIntegral(HashMap<String, String> partMap, HashMap<String, String> headMap) {
        new HttpUtil().get("/movieApi/user/v1/verify/findUserHomeInfo", partMap, headMap).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("成功")) {
                    Gson gson = new Gson();
                    UserAttendanceBean1 userAttendanceBean1 = gson.fromJson(data, UserAttendanceBean1.class);
                    int integral = userAttendanceBean1.getResult().getIntegral();
                    user_attendance_integral.setText("我的积分:" + integral);
                    boolean userSignStatus = userAttendanceBean1.getResult().isUserSignStatus();
                    if (userSignStatus) {
                        user_attendance_geto_attendance.setText("去签到");
                        flag = false;
                        user_attendance_geto_attendance_img.setVisibility(View.VISIBLE);

                    } else {
                        user_attendance_geto_attendance.setText("已签到");
                        flag = true;
                        user_attendance_geto_attendance_img.setVisibility(View.GONE);
                    }
                    Toast.makeText(context, "查询用户积分成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "查询用户积分失败", Toast.LENGTH_SHORT).show();
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

            //点击返回按钮关闭当前页面
            case R.id.user_attendance_back:
                ((UserAttendanceActivity) context).finish();
                break;

            //点击去签到
            case R.id.user_attendance_geto_attendance_img:
                if (!flag) {
                    HashMap<String, String> headMap = new HashMap<>();
                    int userId = SharedPreferencesUtils.getInt(context, "userId");
                    String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                    headMap.put("userId", userId + "");
                    headMap.put("sessionId", sessionId);
                    HashMap<String, String> partMap = new HashMap<>();
                    new HttpUtil().get("/movieApi/user/v1/verify/userSignIn", partMap, headMap).result(new HttpUtil.HttpListener() {
                        @Override
                        public void success(String data) {
                            if (data.contains("成功")) {
                                flag = true;
                                user_attendance_geto_attendance.setText("去签到");
                                user_attendance_geto_attendance_img.setVisibility(View.GONE);
                                Toast.makeText(context, "签到成功", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(context, "签到失败", Toast.LENGTH_SHORT).show();
                                user_attendance_geto_attendance_img.setVisibility(View.VISIBLE);
                            }
                            getIntegral(partMap, headMap);
                        }

                        @Override
                        public void fail(String data) {
                            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
        }
    }
}
