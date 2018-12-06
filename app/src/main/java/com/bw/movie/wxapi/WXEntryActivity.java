package com.bw.movie.wxapi;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

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

    }
}
