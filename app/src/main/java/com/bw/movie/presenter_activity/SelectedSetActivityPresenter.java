package com.bw.movie.presenter_activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.SelectedSetActivity;
import com.bw.movie.bean.Detailsbean;
import com.bw.movie.bean.OrderBean;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.encrypt.MD5EncryptUtil;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.bw.movie.widget.SeatTable;
import com.google.gson.Gson;

import java.io.Serializable;
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
    private RadioButton avtivity_treasure;
    private RadioButton avtivity_micro;

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

        //接收传值
        final Detailsbean.Resultbean result = (Detailsbean.Resultbean) ((SelectedSetActivity) context).getIntent().getSerializableExtra("result");
        String name = ((SelectedSetActivity) context).getIntent().getStringExtra("name");
        String address = ((SelectedSetActivity) context).getIntent().getStringExtra("address");
        String movie_name = ((SelectedSetActivity) context).getIntent().getStringExtra("movie_name");
        //赋值
        play_time.setText(result.getBeginTime() + "-" + result.getEndTime());
        activity_name.setText(name);//影院的名字
        activity_detailsname.setText(address);//影院的地址
        seatTableView.setScreenName(result.getScreeningHall() + "荧幕");//设置屏幕名称
        movie_text.setText(movie_name);
        seatTableView.setMaxSelected(4);//设置最多选中
        //微信 支付宝控件
        avtivity_treasure = (RadioButton) get(R.id.avtivity_treasure);
        avtivity_micro = (RadioButton) get(R.id.avtivity_micro);
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
                    int userId = SharedPreferencesUtils.getInt(context, "userId");
                    String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
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
                    new HttpUtil().postHead("/movieApi/movie/v1/verify/buyMovieTicket", map, headMap).result(new HttpUtil.HttpListener() {
                        @Override
                        public void success(String data) {
                            OrderBean orderBean = new Gson().fromJson(data, OrderBean.class);
                            Toast.makeText(context, orderBean.getMessage(), Toast.LENGTH_SHORT).show();
                            showShopCar();
                        }

                        @Override
                        public void fail(String data) {

                        }
                    });
                } else {
                    Toast.makeText(context, "登录后才能购票哦~", Toast.LENGTH_SHORT).show();
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
                double price = result.getPrice();
                total_price.setText(price * column + "");
            }

            @Override
            public void unCheck(int row, int column) {

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
