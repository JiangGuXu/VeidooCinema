package com.bw.movie.presenter_activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.activity.ReSetpasswordActivity;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.encrypt.Base64EncryptUtil;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

public class ReSetpasswordActivityPresenter extends AppDelage implements View.OnClickListener {

    private Context context;
    private ImageView resetpassword_back;
    private Button resetpassword_btn;
    private EditText resetpassword_newpwd2;
    private EditText resetpassword_newpwd;
    private EditText resetpassword_oldpwd;

    @Override
    public int getLayoutId() {
        return R.layout.activity_resetpassword_layout;
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();

        //左下角返回图标
        resetpassword_back = get(R.id.resetpassword_back);
        resetpassword_back.setOnClickListener(this);

        //老密码输入框
        resetpassword_oldpwd = get(R.id.resetpassword_oldpwd);
        //新密码输入框
        resetpassword_newpwd = get(R.id.resetpassword_newpwd);
        //确认新密码输入框
        resetpassword_newpwd2 = get(R.id.resetpassword_newpwd2);
        //确认修改密码按钮
        resetpassword_btn = get(R.id.resetpassword_btn);
        resetpassword_btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resetpassword_back:
                ((ReSetpasswordActivity) context).finish();
                break;
            case R.id.resetpassword_btn:
                String oldpwd = resetpassword_oldpwd.getText().toString();
                String newpwd = resetpassword_newpwd.getText().toString();
                String newpwd2 = resetpassword_newpwd2.getText().toString();
                if (TextUtils.isEmpty(newpwd) || TextUtils.isEmpty(newpwd2) || TextUtils.isEmpty(oldpwd)) {
                    Toast.makeText(context, "内容不能为空或者不能包含空格", Toast.LENGTH_SHORT).show();
                } else {
                    if (newpwd.equals(newpwd2)) {
                        if (!newpwd.equals(oldpwd)) {


                            String oldpwd_encrypt = Base64EncryptUtil.encrypt(oldpwd);
                            String newpwd_encrypt = Base64EncryptUtil.encrypt(newpwd);
                            String newpwd2_encrypt = Base64EncryptUtil.encrypt(newpwd2);


                            Map<String, String> parameterMap = new HashMap<>();
                            parameterMap.put("oldPwd", oldpwd_encrypt);
                            parameterMap.put("newPwd", newpwd_encrypt);
                            parameterMap.put("newPwd2", newpwd2_encrypt);

                            Log.i("jhktest", "onClick: " + oldpwd_encrypt);
                            Log.i("jhktest", "onClick: " + newpwd_encrypt);
                            Log.i("jhktest", "onClick: " + newpwd2_encrypt);

                            int userId = SharedPreferencesUtils.getInt(context, "userId");
                            String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                            HashMap<String, String> headMap = new HashMap<>();
                            headMap.put("userId", userId + "");
                            headMap.put("sessionId", sessionId);
                            headMap.put("ak", "0110010010000");
                            headMap.put("Content-Type", "application/x-www-form-urlencoded");

                            HashMap<String, String> heanMap = new HashMap<>();


                            new HttpUtil().postHead("/movieApi/user/v1/verify/modifyUserPwd", parameterMap, headMap)
                                    .result(new HttpUtil.HttpListener() {
                                        @Override
                                        public void success(String data) {
                                            if (data.contains("成功")) {
                                                Toast.makeText(context, "密码修改成功", Toast.LENGTH_SHORT).show();
                                                SharedPreferencesUtils.putBoolean(context, "isLogin", false);
                                                SharedPreferencesUtils.putInt(context, "userId", 0);
                                                SharedPreferencesUtils.putString(context, "sessionId", "");


                                                Intent intent = new Intent(context, LoginActivity.class);
                                                ((ReSetpasswordActivity) context).startActivity(intent);
                                                ((ReSetpasswordActivity) context).finish();

                                            } else {
                                                Toast.makeText(context, "密码修改失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void fail(String data) {
                                            Toast.makeText(context, "密码修改失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        } else {
                            Toast.makeText(context, "新密码和旧密码不能相同", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "请确认密码是否相同", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
    }
}
