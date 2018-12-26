package com.bw.movie.presenter_activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.SelectedSetActivity;
import com.bw.movie.bean.Detailsbean;
import com.bw.movie.bean.OrderBean;
import com.bw.movie.bean.PayBean;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.encrypt.MD5EncryptUtil;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.bw.movie.widget.SeatTable;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 选座页面
 * 姜谷蓄
 */
public class SelectedSetActivityPresenter extends AppDelage {
    private SeatTable seatTableView;
    private TextView play_time;
    private TextView play_data;
    private TextView activity_name;
    private TextView activity_detailsname;
    private TextView movie_text;
    private TextView total_price;
    private ImageView pay_yes;
    private RelativeLayout layout;
    private ImageView img;
    private RadioButton radio_wechat;
    private RadioButton radio_alipay;
    private Button btn_pay;
    private double price;
    private int userId;
    private String sessionId;
    private OrderBean orderBean;
    private IWXAPI api;
    private int num=0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_selectedset;
    }

    private Context context;

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();
        seatTableView = get(R.id.seatView);
        play_time = get(R.id.play_time);
        activity_name = get(R.id.activity_name);
        activity_detailsname = get(R.id.activity_detailsname);
        movie_text = get(R.id.movie_name);
        total_price = get(R.id.total_price);
        play_data = get(R.id.play_data);
        pay_yes = get(R.id.pay_yes);
        btn_pay = get(R.id.btn_pay);

        //接收传值
        final Detailsbean.Resultbean result = (Detailsbean.Resultbean) ((SelectedSetActivity) context).getIntent().getSerializableExtra("result");
        String name = ((SelectedSetActivity) context).getIntent().getStringExtra("name");
        String address = ((SelectedSetActivity) context).getIntent().getStringExtra("address");
        String movie_name = ((SelectedSetActivity) context).getIntent().getStringExtra("movie_name");
        price = result.getPrice();
        //赋值
        play_time.setText(result.getBeginTime() + "-" + result.getEndTime());
        activity_name.setText(name);//影院的名字
        activity_detailsname.setText(address);//影院的地址
        seatTableView.setScreenName(result.getScreeningHall() + "荧幕");//设置屏幕名称
        movie_text.setText(movie_name);
        seatTableView.setMaxSelected(4);//设置最多选中
        api = WXAPIFactory.createWXAPI(context, "wx4c96b6b8da494224");
        //微信 支付宝控件
        radio_wechat = (RadioButton) get(R.id.radio_wechat);
        radio_alipay = (RadioButton) get(R.id.radio_alipay);
        img = (ImageView) get(R.id.activity_img);
        layout = (RelativeLayout) get(R.id.start_ctr);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintShopCar();
            }
        });
        //点击对号
        pay_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先判断用户是否登录
                if (SharedPreferencesUtils.getBoolean(context, "isLogin")) {
                    //如果登录的话获取用户的信息
                    userId = SharedPreferencesUtils.getInt(context, "userId");
                    sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                    //拼接头部接口
                    Map<String, String> headMap = new HashMap();
                    headMap.put("Content-Type", "application/x-www-form-urlencoded");
                    String suserId = String.valueOf(userId);//转化过的userId
                    headMap.put("userId", suserId);
                    headMap.put("sessionId", sessionId);
                    //拼接接口需要的参数
                    Map<String, String> map = new HashMap<>();
                    String scheduleId = String.valueOf(result.getId());//排期id
                    String amount = "1";//购买数量(目前有问题)
                    String all = suserId + scheduleId + amount + "movie";//拼接签名需要的的信息
                    //通过md5加密签名
                    String sign = MD5EncryptUtil.MD5(all);
                    map.put("scheduleId", scheduleId);
                    map.put("amount", amount);
                    map.put("sign", sign);
                    //请求下单的接口
                    new HttpUtil(context).result(new HttpUtil.HttpListener() {
                        @Override
                        public void success(String data) {
                            orderBean = new Gson().fromJson(data, OrderBean.class);
                            Toast.makeText(context, orderBean.getMessage(), Toast.LENGTH_SHORT).show();
                            showShopCar();
                        }

                        @Override
                        public void fail(String data) {

                        }

                        @Override
                        public void notNetwork(View data) {

                        }
                    }).postHead("/movieApi/movie/v1/verify/buyMovieTicket", map, headMap,"",true,false);
                } else {
                    Toast.makeText(context, "登录后才能购票哦~", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //连个RadioButton的点击事件
        //监听微信支付的状态
        radio_wechat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){//如果为true
                    radio_alipay.setChecked(false);
                    btn_pay.setText("微信支付"+price+"元");
                }
            }
        });
        //监听微信支付的状态
        radio_alipay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){//如果为true
                    radio_wechat.setChecked(false);
                    btn_pay.setText("支付宝支付"+price+"元");
                }
            }
        });
        //点击支付按钮的事件
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击是判断选中的是哪种支付方式
                if (radio_wechat.isChecked()){//微信选中
                    //请求微信支付的接口
                    Map<String,String> map = new HashMap<>();//拼接参数
                    map.put("payType","1");
                    map.put("orderId",orderBean.getOrderId());
                    //拼接头参
                    Map<String,String> headMap = new HashMap<>();
                    headMap.put("userId",String.valueOf(userId));
                    headMap.put("sessionId",sessionId);
                    new HttpUtil(context).result(new HttpUtil.HttpListener() {
                        @Override
                        public void success(String data) {
                            //解析成功之后的数据
                            PayBean payBean = new Gson().fromJson(data, PayBean.class);
                            //Toast.makeText(context,payBean.getMessage(),Toast.LENGTH_SHORT).show();
                            PayReq request = new PayReq();
                            request.appId = payBean.getAppId();
                            request.partnerId = payBean.getPartnerId();
                            request.prepayId= payBean.getPrepayId();
                            request.packageValue = payBean.getPackageValue();
                            request.nonceStr= payBean.getNonceStr();
                            request.timeStamp= payBean.getTimeStamp();
                            request.sign= payBean.getSign();
                            api.sendReq(request);
                        }

                        @Override
                        public void fail(String data) {

                        }

                        @Override
                        public void notNetwork(View data) {

                        }
                    }).postHead("/movieApi/movie/v1/verify/pay",map,headMap,"",true,true);
                }

            }
        });
        //选座控件的点击监听
        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {
            @Override
            public boolean isValidSeat(int row, int column) {
                if (column == 2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if (row == 6 && column == 6) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                num++;
                BigDecimal b1 = new BigDecimal(Double.toString(num));
                BigDecimal b2 = new BigDecimal(Double.toString(price));
                total_price.setText(b1.multiply(b2).doubleValue()+"");
            }

            @Override
            public void unCheck(int row, int column) {
                num--;
                BigDecimal b1 = new BigDecimal(Double.toString(num));
                BigDecimal b2 = new BigDecimal(Double.toString(price));
                total_price.setText(b1.multiply(b2).doubleValue()+"");
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10, 15);

    }

    //打开
    private void showShopCar() {
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layout, "translationY", heightPixels, 0);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        layout.setVisibility(View.VISIBLE);
    }

    //关闭
    private void hintShopCar() {
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layout, "translationY", 0, heightPixels);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.setVisibility(View.GONE);
            }
        }, 1000);
    }

}
