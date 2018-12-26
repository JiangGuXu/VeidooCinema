package com.bw.movie.presenter_activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.UserReSertInfoActivity;
import com.bw.movie.bean.UserResetInfoBean;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.dateformat.DateFormatForYou;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.HashMap;


/**
 * 2018年12月1日 10:10:08
 * 焦浩康
 * 这是修改用户信息的方法
 */
public class UserReSertInfoActivityPresenter extends AppDelage implements View.OnClickListener {

    private Context context;
    private Button user_reset_info_updata;
    private EditText user_reset_info_name;
    private RelativeLayout user_reset_info_sex_layout;
    private EditText user_reset_info_birthday;
    private EditText user_reset_info_phone;
    private EditText user_reset_info_email;
    private TextView user_reset_info_sex;
    private PopupWindow popupWindow;
    private View inflate;
    private RadioButton user_reset_info_popupwindow_radiobutton1;
    private RadioButton user_reset_info_popupwindow_radiobutton2;
    private ImageView user_reset_info_back;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_reset_info;
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();

        //返回图片
        user_reset_info_back = get(R.id.user_reset_info_back);
        user_reset_info_back.setOnClickListener(this);


        //确认修改按钮
        user_reset_info_updata = get(R.id.user_reset_info_updata);
        user_reset_info_updata.setOnClickListener(this);


        //性别布局
        user_reset_info_sex_layout = get(R.id.user_reset_info_sex_layout);
        user_reset_info_sex_layout.setOnClickListener(this);


        //性别文字
        user_reset_info_sex = get(R.id.user_reset_info_sex);


        //name 输入框
        user_reset_info_name = get(R.id.user_reset_info_name);

        //出生日期  输入框
        user_reset_info_birthday = get(R.id.user_reset_info_birthday);
        //手机号码  输入框
        user_reset_info_phone = get(R.id.user_reset_info_phone);
        //邮箱  输入框
        user_reset_info_email = get(R.id.user_reset_info_email);


        //设置默认值
        String nickname = SharedPreferencesUtils.getString(context, "nickname");
        user_reset_info_name.setText(nickname);

        String birthday = SharedPreferencesUtils.getString(context, "birthday");
        user_reset_info_birthday.setText(birthday);

        String phone = SharedPreferencesUtils.getString(context, "phone");
        user_reset_info_phone.setText(phone);


        String sex = SharedPreferencesUtils.getString(context, "sex");
        user_reset_info_sex.setText(sex);


        inflate = View.inflate(context, R.layout.user_reset_info_popupwindow_layout, null);

        user_reset_info_popupwindow_radiobutton1 = inflate.findViewById(R.id.user_reset_info_popupwindow_radiobutton1);
        user_reset_info_popupwindow_radiobutton2 = inflate.findViewById(R.id.user_reset_info_popupwindow_radiobutton2);
        user_reset_info_popupwindow_radiobutton1.setOnClickListener(this);
        user_reset_info_popupwindow_radiobutton2.setOnClickListener(this);

        popupWindow = new PopupWindow(inflate, 240, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);


        /**
         * 2018年12月2日 21:10:55
         * 焦浩康
         * 这里后期有时间在继续完善
         */
        user_reset_info_email.setHint("邮箱必须输入如不想修改就输入原来注册的邮箱");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //确认修改按钮
            case R.id.user_reset_info_updata:

                //邮箱
                String email = user_reset_info_email.getText().toString();
                if (TextUtils.isEmpty(email.trim())) {
                    Toast.makeText(context, "邮箱必须输入如不想修改就输入原来注册时的邮箱", Toast.LENGTH_SHORT).show();
                    return;
                }

                //姓名
                String nickname = user_reset_info_name.getText().toString();
                //性别
                String sex = user_reset_info_sex.getText().toString();
                //手机号
                String phone = user_reset_info_phone.getText().toString();

                //出生日期
                String birthday = user_reset_info_birthday.getText().toString();

                HashMap<String, String> userinfoHashMap = new HashMap<>();
                if (!TextUtils.isEmpty(nickname.trim())) {
                    userinfoHashMap.put("nickName", nickname);
                }

                if (!TextUtils.isEmpty(sex.trim())) {
                    if (sex.trim().equals("男")) {
                        userinfoHashMap.put("sex", 1 + "");
                    }
                    if (sex.trim().equals("女")) {
                        userinfoHashMap.put("sex", 2 + "");
                    }
                }

                if (!TextUtils.isEmpty(phone.trim())) {
                    userinfoHashMap.put("phone", phone);
                }

                if (!TextUtils.isEmpty(email.trim())) {
                    userinfoHashMap.put("email", email);
                }

                if (!TextUtils.isEmpty(birthday.trim())) {
                    userinfoHashMap.put("birthday", birthday);
                }


                HashMap<String, String> headHashMap = new HashMap<>();
                String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                final int userId = SharedPreferencesUtils.getInt(context, "userId");
                headHashMap.put("userId", userId + "");
                headHashMap.put("sessionId", sessionId);
                headHashMap.put("Content-Type", "application/x-www-form-urlencoded");

                new HttpUtil(context).result(new HttpUtil.HttpListener() {
                    @Override
                    public void success(String data) {
                        if (data.contains("成功")) {
                            Gson gson = new Gson();
                            UserResetInfoBean userResetInfoBean = gson.fromJson(data, UserResetInfoBean.class);
                            UserResetInfoBean.ResultBean result = userResetInfoBean.getResult();
                            SharedPreferencesUtils.putInt(context, "userId", result.getId());
                            SharedPreferencesUtils.putString(context, "email", result.getEmail());
                            try {
                                String s = new DateFormatForYou().longToString(result.getBirthday(), "yyyy-MM-dd");
                                SharedPreferencesUtils.putString(context, "birthday", s);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            SharedPreferencesUtils.putString(context, "phone", result.getPhone());
                            if (result.getSex() == 1) {
                                SharedPreferencesUtils.putString(context, "sex", "男");
                            } else {
                                SharedPreferencesUtils.putString(context, "sex", "女");
                            }

                            SharedPreferencesUtils.putString(context, "nickname", result.getNickName());
                            Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void fail(String data) {
                        Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void notNetwork(View data) {

                    }
                }).postHead("/movieApi/user/v1/verify/modifyUserInfo", userinfoHashMap, headHashMap,"",true,false);


                break;


            //性别布局
            /**
             * 焦浩康
             * 2018年12月2日 20:57:29
             * 点击这个性别布局的时候  只是修改页面的值  当用户点击确认修改的时候才去真正的修改
             */
            case R.id.user_reset_info_sex_layout:
                // 弹出popupwindow

                popupWindow.showAtLocation(((UserReSertInfoActivity) context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;

            //popupwindow选中男
            case R.id.user_reset_info_popupwindow_radiobutton1:
                user_reset_info_sex.setText("男");
                popupWindow.dismiss();
                break;
            //popupwindow选中女
            case R.id.user_reset_info_popupwindow_radiobutton2:
                user_reset_info_sex.setText("女");
                popupWindow.dismiss();
                break;

            //点击返回图片的时候 关闭该activity
            case R.id.user_reset_info_back:
                ((UserReSertInfoActivity) context).finish();
                break;
        }
    }
}
