package com.bw.movie.presenter_activity;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.encrypt.Base64EncryptUtil;
import com.bw.movie.utils.net.HttpUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivityPresenter extends AppDelage implements View.OnClickListener {
    private EditText register_name;
    private EditText register_sex;
    private EditText register_data;
    private EditText register_phone;
    private EditText register_email;
    private EditText register_pwd;
    private Button btn_register;
    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }
    private Context context;
    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();
        register_name = get(R.id.register_name);
        register_sex = get(R.id.register_sex);
        register_data = get(R.id.register_data);
        register_phone = get(R.id.register_phone);
        register_email = get(R.id.register_email);
        register_pwd = get(R.id.register_pwd);
        btn_register = get(R.id.register_btn);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
                //获取输入框的内容
                String name = register_name.getText().toString().trim();
                String sex = register_sex.getText().toString().trim();
                String birthday = register_data.getText().toString().trim();
                String phone = register_phone.getText().toString().trim();
                String email = register_email.getText().toString().trim();
                String pwd = register_pwd.getText().toString().trim();
                //非空判断
                if (TextUtils.isEmpty(name)||TextUtils.isEmpty(sex)||TextUtils.isEmpty(birthday)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(email)||TextUtils.isEmpty(pwd)){
                    Toast.makeText(context,"输入内容不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    //加密
                    String password = Base64EncryptUtil.encrypt(pwd);
                    Log.i("password",password);
                    //传参
                    Map<String, String> map = new HashMap<>();
                    map.put("nickName",name);
                    map.put("phone",phone);
                    map.put("pwd",password);
                    map.put("pwd2",password);
                    //判断性别
                    if (sex.equals("男")){
                        map.put("sex","1");
                    }else {
                        map.put("sex","2");
                    }
                    map.put("birthday",birthday);
                    map.put("email",email);
                    //请求接口
                    new HttpUtil().post("/movieApi/user/v1/registerUser",map).result(new HttpUtil.HttpListener() {
                        @Override
                        public void success(String data) {
                            //解析数据
                            Gson gson = new Gson();
                            RegisterBean registerBean = gson.fromJson(data, RegisterBean.class);
                            //获取解析到的数据
                            String message = registerBean.getMessage();
                            String status = registerBean.getStatus();
                            if (status.equals("0000")){
                                Toast.makeText(context,"注册成功",Toast.LENGTH_SHORT).show();
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
