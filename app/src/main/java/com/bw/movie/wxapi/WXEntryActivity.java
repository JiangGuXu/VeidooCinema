package com.bw.movie.wxapi;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.bw.movie.bean.LoginBean;
import com.bw.movie.utils.dateformat.DateFormatForYou;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信登录相关
 */


public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {


    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wxb3852e6a6b7d9516";

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regToWx();
        api.handleIntent(getIntent(), this);
    }

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(APP_ID);
    }


    //sendReq是第三方app主动发送消息给微信，发送完成之后会切回到第三方app界面。
    @Override
    public void onReq(BaseReq baseReq) {

    }

    //sendResp是微信向第三方app请求数据，第三方app回应数据之后会切回到微信界面。
    @Override
    public void onResp(BaseResp baseResp) {
        String result = "";
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "发送成功";
                Log.i("show", "onResp: 1" + result);
                SendAuth.Resp sendResp = (SendAuth.Resp) baseResp;
                if (sendResp != null) {
                    String code = sendResp.code;
                    String state = sendResp.state;
                    Log.i("show", "onResp: " + code);
                    if (state.equals("wechat_sdk_微信登录")) {
                        doWX_login(code);
                    } else if (state.equals("wechat_sdk_微信绑定")) {
                        doWX_bind(code);
                    }
                }
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "发送取消";

                Log.i("show", "onResp: 2" + result);
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";

                Log.i("show", "onResp: 1" + result);
                finish();
                break;
            default:
                result = "发送返回";

                Log.i("show", "onResp: 0" + result);
                finish();
                break;
        }
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("支付结果");
            builder.setMessage(baseResp.errCode + "");
            builder.show();
        }
    }

    private void doWX_bind(String code) {
        int userId = SharedPreferencesUtils.getInt(WXEntryActivity.this, "userId");
        String sessionId = SharedPreferencesUtils.getString(WXEntryActivity.this, "sessionId");
        Map<String, String> map = new HashMap<>();
        //拼接参数
        map.put("code", code);
        HashMap<String, String> headMap = new HashMap<>();
        headMap.put("userId", userId + "");
        new HttpUtil().postHead("/movieApi/user/v1/verify/bindWeChat", map, headMap).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Log.i("show", "success: " + data);
                if (data.contains("成功")) {
                    wxEntryBindListener.onisSucceed(true);
                }
            }

            @Override
            public void fail(String data) {
                Log.i("show", "fail: " + "绑定失败");
                wxEntryBindListener.onisSucceed(false);
            }
        });

    }

    private void doWX_login(String code) {
        HashMap<String, String> headMap = new HashMap<>();
        headMap.put("Content-Type", "application/x-www-form-urlencoded");


        HashMap<String, String> partMap = new HashMap<>();
        partMap.put("code", code);
        new HttpUtil().postHead("/movieApi/user/v1/weChatBindingLogin", partMap, headMap).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("成功")) {
                    Toast.makeText(WXEntryActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //解析数据
                    Gson gson = new Gson();
                    LoginBean loginBean = gson.fromJson(data, LoginBean.class);
                    LoginBean.ResultBean resultBean = loginBean.getResult();

                    //储存userid
                    SharedPreferencesUtils.putInt(WXEntryActivity.this, "userId", resultBean.getUserId());
                    //储存sessionId
                    SharedPreferencesUtils.putString(WXEntryActivity.this, "sessionId", resultBean.getSessionId());
                    //获取到用户信息的集合
                    LoginBean.ResultBean.UserInfoBean userInfo = resultBean.getUserInfo();
                    //登录状态改为true
                    SharedPreferencesUtils.putBoolean(WXEntryActivity.this, "isLogin", true);
                    //存储用户头像信息
                    SharedPreferencesUtils.putString(WXEntryActivity.this, "headpic", userInfo.getHeadPic());
                    //存储昵称
                    SharedPreferencesUtils.putString(WXEntryActivity.this, "nickname", userInfo.getNickName());
                    //存储性别
                    if (userInfo.getSex() == 1) {
                        SharedPreferencesUtils.putString(WXEntryActivity.this, "sex", "男");
                    } else {
                        SharedPreferencesUtils.putString(WXEntryActivity.this, "sex", "女");
                    }
                    //获取出生日期
                    long birthday = userInfo.getBirthday();
                    DateFormatForYou dateFormatForYou = new DateFormatForYou();
                    try {
                        String s = dateFormatForYou.longToString(birthday, "yyyy-MM-dd");
                        SharedPreferencesUtils.putString(WXEntryActivity.this, "birthday", s);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    //储存手机号
                    SharedPreferencesUtils.putString(WXEntryActivity.this, "phone", userInfo.getPhone());
                }
            }

            @Override
            public void fail(String data) {
                Toast.makeText(WXEntryActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private WXEntryBindListener wxEntryBindListener;

    public void setWxEntryBindListener(WXEntryBindListener wxEntryBindListener) {
        this.wxEntryBindListener = wxEntryBindListener;
    }

    public interface WXEntryBindListener {
        void onisSucceed(boolean flag);
    }
}
