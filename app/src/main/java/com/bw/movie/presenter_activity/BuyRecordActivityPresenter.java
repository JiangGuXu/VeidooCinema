package com.bw.movie.presenter_activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.BuyRecordActivity;
import com.bw.movie.adapter.MyAdapterBuyRecord;
import com.bw.movie.bean.BuyRecord;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 * 推荐影院的排期
 * 2018年12月4日 18:18:30
 * 赵瑜峰
 * 创建了基本的这个presenter
 * */
public class BuyRecordActivityPresenter extends AppDelage {

    private XRecyclerView recyclerView;
    private MyAdapterBuyRecord myAdapterBuyRecord;
    private int count=5;
    @Override
    public int getLayoutId() {
        return R.layout.activity_buyrecord;
    }
    private Context context;
    @Override
    public void getContext(Context context) {
        this.context=context;
    }

    @Override
    public void initData() {
        super.initData();
        recyclerView = get(R.id.buyrecord_recyler);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BuyRecordActivity)context).finish();
            }
        },R.id.buyrecord_return);
        myAdapterBuyRecord = new MyAdapterBuyRecord(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapterBuyRecord);
        doHttp();
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                doHttp();
            }

            @Override
            public void onLoadMore() {
                count+=5;
                doHttp();
            }
        });
    }

    @Override
    public void successnetwork() {
        super.successnetwork();
        doHttp();
    }

    private void doHttp() {
        if(SharedPreferencesUtils.getBoolean(context,"isLogin")){
            int userId = SharedPreferencesUtils.getInt(context, "userId");
            String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
            Map<String,String> map = new HashMap<>();
            map.put("page","1");
            map.put("count",count+"");
            Map<String,String> mapHead = new HashMap<>();
            mapHead.put("userId",userId+"");
            mapHead.put("sessionId",sessionId);
            new HttpUtil(context).result(new HttpUtil.HttpListener() {
                @Override
                public void success(String data) {
                    Log.i("aaaaa", "success: "+data);
                    Gson gson = new Gson();
                    BuyRecord buyRecord = gson.fromJson(data, BuyRecord.class);
                    if(buyRecord.getStatus().equals("0000")){
                        if(!buyRecord.getMessage().equals("无数据")){
                            List<BuyRecord.ResultBean> result = buyRecord.getResult();
                            myAdapterBuyRecord.setList(result);
                            recyclerView.refreshComplete();
                            recyclerView.loadMoreComplete();
                        }
                    }else{
                        Toast.makeText(context, "无数据", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void fail(String data) {

                }

                @Override
                public void notNetwork(View data) {

                }
            }).get("/movieApi/user/v1/verify/findUserBuyTicketRecordList",map,mapHead,"buyrecord",true,true);
        }else{
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
        }
    }

    public void onresume() {
        doHttp();
    }
}
