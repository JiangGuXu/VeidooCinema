package com.bw.movie.presenter_fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bw.movie.adapter.NearAdepter;
import com.bw.movie.bean.Nearbean;
import com.bw.movie.adapter.RecommendedAdepter;
import com.bw.movie.R;
import com.bw.movie.bean.Recommendedbean;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/*
 * 影院页面presenter
 * 2018年11月27日 15:18:30
 * 程丹妮
 * 创建了基本的这个presenter
 * */
public class CinemaFragmentPresenter extends AppDelage implements AMapLocationListener, View.OnClickListener {

    private Context context;
    private ImageView imageView;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    private String city;
    private RecyclerView recyclerView;
    private Button recommendimg;
    private Button near;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cinema;
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();
        get(R.id.activity_loca);
        imageView = (ImageView) get(R.id.activity_loca);
        recommendimg = (Button) get(R.id.activity_recommended);
        near = (Button) get(R.id.activity_near);
        recyclerView = (RecyclerView) get(R.id.activity_recyclerView);
        imageView.setOnClickListener(this);
        mlocationClient = new AMapLocationClient(context);
         //两个点击事件
        near.setOnClickListener(this);
        recommendimg.setOnClickListener(this);

       //推荐影院
        dohttp();
        //附近影院
        doHttp();
        recommendimg.callOnClick();
    }

    //附近影院解析
    private void doHttp() {
        String url1 = "/movieApi/cinema/v1/findRecommendCinemas";
        HashMap<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("count", "6");
        new HttpUtil().get(url1, map,null).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Log.i("chengtest", "success111: " + data);
                Nearbean json = new Gson().fromJson(data, Nearbean.class);
                List<Nearbean.Resultbean.NearbyCinemaListbean> list = json.getResult().getNearbyCinemaList();
                NearAdepter shangweiAdapter = new NearAdepter(context, list);
                LinearLayoutManager s = new LinearLayoutManager(context);
                s.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(s);
                recyclerView.setAdapter(shangweiAdapter);
            }

            @Override
            public void fail(String data) {

            }
        });
    }

    //推荐影院解析
    private void dohttp() {
        String url = "/movieApi/cinema/v1/findAllCinemas";
        HashMap<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("count", "21");
        new HttpUtil().get(url, map,null).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Log.i("chengtest", "success222: " + data);
                Recommendedbean json = new Gson().fromJson(data, Recommendedbean.class);
                List<Recommendedbean.Resultbean> bean = json.getResult();
                RecommendedAdepter shangweiAdapter = new RecommendedAdepter(context, bean);
                LinearLayoutManager s = new LinearLayoutManager(context);
                s.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(s);
                recyclerView.setAdapter(shangweiAdapter);
            }

            @Override
            public void fail(String data) {

            }
        });
    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                city = amapLocation.getCity();
                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_loca:
                //初始化定位参数
                mLocationOption = new AMapLocationClientOption();
                //设置返回地址信息，默认为true
                mLocationOption.setNeedAddress(true);
                //设置定位监听
                mlocationClient.setLocationListener(this);
                //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                //设置定位间隔,单位毫秒,默认为2000ms
                mLocationOption.setInterval(20000);
                //设置定位参数
                mlocationClient.setLocationOption(mLocationOption);
                //启动定位
                mlocationClient.startLocation();
                //进行地理位置获取
                imageView.setOnClickListener(this);
                Toast.makeText(context, city + "", Toast.LENGTH_SHORT).show();
                break;

            case R.id.activity_recommended:
                Log.i("test1", "onClick: ");
                 near.setBackgroundResource(R.drawable.my_attention_title_shape_false);
                 recommendimg.setBackgroundResource(R.drawable.my_attention_title_shape_true);
                dohttp();
                break;
            case R.id.activity_near:
                Log.i("test2", "onClick: ");
                recommendimg.setBackgroundResource(R.drawable.my_attention_title_shape_false);
                near.setBackgroundResource(R.drawable.my_attention_title_shape_true);
                doHttp();
                break;
        }
    }

}
