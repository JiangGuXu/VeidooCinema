package com.bw.movie.presenter_activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.activity.RegisterActivity;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.encrypt.Base64EncryptUtil;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.bw.movie.wxapi.WXEntryActivity;
import com.google.gson.Gson;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private ImageView login_in_weixin;

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

        //与微信连接
        regToWx();

        //微信登录图标
        login_in_weixin = get(R.id.login_in_weixin);
        login_in_weixin.setOnClickListener(this);


    }

    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }


    @Override
    public void resume() {
        super.resume();


        //如果微信登录成功 关闭当前页面
        Boolean isWXlogin = SharedPreferencesUtils.getBoolean(context, "isWXlogin");
        if (isWXlogin) {
            ((LoginActivity) context).finish();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击立即注册，跳转到注册页面
            case R.id.quick_register:
                Intent intent = new Intent(context, RegisterActivity.class);
                context.startActivity(intent);
                break;
            //点击登录按钮的事件
            case R.id.login_btn:
                //获取输入框内的内容
                String phone = login_phone.getText().toString().trim();
                final String pwd = login_pass.getText().toString().trim();
                //非空校验
                if (TextUtils.isEmpty(phone) && TextUtils.isEmpty(pwd)) {
                    Toast.makeText(context, "输入的内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    //使用非对称加密密码
                    String encrypt_pwd = Base64EncryptUtil.encrypt(pwd);
                    Log.i("password", encrypt_pwd);
                    Map<String, String> map = new HashMap<>();
                    //拼接参数
                    map.put("phone", phone);
                    map.put("pwd", encrypt_pwd);
                    //请求接口
                    new HttpUtil().post("/movieApi/user/v1/login", map).result(new HttpUtil.HttpListener() {
                        @Override
                        public void success(String data) {
                            //解析数据
                            Gson gson = new Gson();
                            LoginBean loginBean = gson.fromJson(data, LoginBean.class);
                            String message = loginBean.getMessage();
                            String status = loginBean.getStatus();
                            //判断是否登录成功
                            if (status.equals("0000")) {
                                //存储密码
                                SharedPreferencesUtils.putString(context, "pwd", pwd);
                                //获取到返回结果的集合
                                LoginBean.ResultBean resultBean = loginBean.getResult();
                                //储存userid
                                SharedPreferencesUtils.putInt(context, "userId", resultBean.getUserId());
                                //储存sessionId
                                SharedPreferencesUtils.putString(context, "sessionId", resultBean.getSessionId());
                                //获取到用户信息的集合
                                LoginBean.ResultBean.UserInfoBean userInfo = resultBean.getUserInfo();
                                //登录状态改为true
                                SharedPreferencesUtils.putBoolean(context, "isLogin", true);
                                //存储用户头像信息
                                SharedPreferencesUtils.putString(context, "headpic", userInfo.getHeadPic());
                                //存储昵称
                                SharedPreferencesUtils.putString(context, "nickname", userInfo.getNickName());
                                //存储性别
                                if (userInfo.getSex() == 1) {
                                    SharedPreferencesUtils.putString(context, "sex", "男");
                                } else {
                                    SharedPreferencesUtils.putString(context, "sex", "女");
                                }
                                //获取出生日期
                                long birthday = userInfo.getBirthday();
                                try {
                                    //转化类型
                                    String birth = longToString(birthday, "yyyy-MM-dd");
                                    //储存生日
                                    SharedPreferencesUtils.putString(context, "birthday", birth);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                //储存手机号
                                SharedPreferencesUtils.putString(context, "phone", userInfo.getPhone());
                                //开启debug日志数据
                                XGPushConfig.enableDebug(context,true);
                                //信鸽token注册
                                XGPushManager.registerPush(context, new XGIOperateCallback() {
                                    @Override
                                    public void onSuccess(Object data, int flag) {
                                    //token在设备卸载重装的时候有可能会变
                                        Log.d("TPush", "注册成功，设备token为：" + data);
                                        XGPushManager.bindAccount(context, "XINGE");
                                        XGPushManager.setTag(context,"XINGE");
                                        uploadXgInfo(data);
                                    }
                                    @Override
                                    public void onFail(Object data, int errCode, String msg) {
                                        Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
                                    }
                                });
                                ((LoginActivity) context).finish();
                            }
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void fail(String data) {

                        }
                    });
                }
                break;

            //点击微信登录图标
            case R.id.login_in_weixin:
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";//
//                req.scope = "snsapi_login";//提示 scope参数错误，或者没有scope权限
                req.state = "wechat_sdk_微信登录";
                api.sendReq(req);
                break;
        }
    }
    //将信鸽token上传到服务端
    private void uploadXgInfo(Object data) {
        Map<String,String> headMap = new HashMap<>();
        headMap.put("userId", String.valueOf(SharedPreferencesUtils.getInt(context,"userId")));
        headMap.put("sessionId",SharedPreferencesUtils.getString(context,"sessionId"));
        headMap.put("Content-Type","application/x-www-form-urlencoded");
        Map<String,String> bodyMap = new HashMap<>();
        bodyMap.put("token",String.valueOf(data));
        bodyMap.put("os","1");
        //请求接口
        new HttpUtil().postHead("/movieApi/tool/v1/verify/uploadPushToken",bodyMap,headMap).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                RegisterBean bean = new Gson().fromJson(data, RegisterBean.class);
                Toast.makeText(context,"配置"+bean.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void fail(String data) {
                RegisterBean bean = new Gson().fromJson(data, RegisterBean.class);
                Toast.makeText(context,"配置"+bean.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wxb3852e6a6b7d9516";

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);
    }


}
