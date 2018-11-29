package com.bw.movie.presenter_activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.RegisterActivity;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.encrypt.Base64EncryptUtil;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录页面
 * 姜谷蓄
 */

public class LoginActivityPresenter extends AppDelage implements View.OnClickListener {
    private TextView quick_register;
    private TextView login_phone;
    private TextView login_pass;
    private Button btn_login;
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    private Context context;
    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();
        //初始化控件
        quick_register = get(R.id.quick_register);
        login_phone = get(R.id.login_phone);
        login_pass = get(R.id.login_pass);
        btn_login = get(R.id.login_btn);
        //设置点击事件
        quick_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击立即注册，跳转到注册页面
            case R.id.quick_register:
                Intent intent = new Intent(context, RegisterActivity.class);
                context.startActivity(intent);
                break;
            //点击登录按钮的事件
            case R.id.login_btn:
                //获取输入框内的内容
                String phone = login_phone.getText().toString().trim();
                String pwd = login_pass.getText().toString().trim();
                //非空校验
                if (TextUtils.isEmpty(phone)&&TextUtils.isEmpty(pwd)){
                    Toast.makeText(context,"输入的内容不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    //使用非对称加密密码
                    String encrypt_pwd = Base64EncryptUtil.encrypt(pwd);
                    Log.i("password",encrypt_pwd);
                    Map<String, String> map = new HashMap<>();
                    //拼接参数
                    map.put("phone",phone);
                    map.put("pwd",encrypt_pwd);
                    //请求接口
                    new HttpUtil().post("/movieApi/user/v1/login",map).result(new HttpUtil.HttpListener() {
                        @Override
                        public void success(String data) {
                            //解析数据
                            Gson gson = new Gson();
                            LoginBean loginBean = gson.fromJson(data, LoginBean.class);
                            String message = loginBean.getMessage();
                            String status = loginBean.getStatus();
                            //判断是否登录成功
                            if (status.equals("0000")){
                                //获取到返回结果的集合
                                LoginBean.ResultBean resultBean = loginBean.getResult();
                                //获取到用户信息的集合
                                LoginBean.ResultBean.UserInfoBean userInfo = resultBean.getUserInfo();
                                //登录状态改为true
                                SharedPreferencesUtils.putBoolean(context,"isLogin",true);
                                //存储用户头像信息
                                SharedPreferencesUtils.putString(context,"headpic",userInfo.getHeadPic());
                                //存储昵称
                                SharedPreferencesUtils.putString(context,"nickname",userInfo.getNickName());
                                //存储性别
                                if (userInfo.getSex()==1){
                                    SharedPreferencesUtils.putString(context,"sex","男");
                                }else {
                                    SharedPreferencesUtils.putString(context,"sex","女");
                                }
                                //存储出生日期
                                //SharedPreferencesUtils.putString(context,"birthday",userInfo.getBirthday());
                                //储存手机号
                                SharedPreferencesUtils.putString(context,"phone",userInfo.getPhone());
                                Toast.makeText(context,"成功",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void fail(String data) {

                        }
                    });
                }
                break;
        }
    }
}
