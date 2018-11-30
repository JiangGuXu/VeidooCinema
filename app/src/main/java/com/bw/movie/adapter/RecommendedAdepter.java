package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.DetailsActivity;
import com.bw.movie.bean.Recommendedbean;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 推荐影院的适配器
 * 2018年11月28日 15:18:30
 * 程丹妮
 * 创建了基本的这个Adapter
 * */
public class RecommendedAdepter extends RecyclerView.Adapter<RecommendedAdepter.sRecommendedAdepter> {
    private List<Recommendedbean.Resultbean> list = new ArrayList<>();
    private Context context;

    public RecommendedAdepter(Context context, List<Recommendedbean.Resultbean> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public sRecommendedAdepter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_recommended, null);
        sRecommendedAdepter vi = new sRecommendedAdepter(view);
        return vi;
    }

    @Override
    public void onBindViewHolder(@NonNull sRecommendedAdepter sRecommendedAdepter, final int i) {
        sRecommendedAdepter.text.setText(list.get(i).getName());
        sRecommendedAdepter.address.setText(list.get(i).getAddress());
        String images = list.get(i).getLogo();
        String[] split = images.split("\\|");
        sRecommendedAdepter.recommendimg.setImageURI(split[0]);
        //判断是否喜欢该影院
        boolean followCinema = list.get(i).isFollowCinema();
        if (followCinema){
            sRecommendedAdepter.image_like.setImageResource(R.drawable.cinema_like_selected);
        }else {
            sRecommendedAdepter.image_like.setImageResource(R.drawable.cinema_like_defult);
        }
        //布局的点击事件
        sRecommendedAdepter.itemView.setOnClickListener(new View.OnClickListener() {
            private String logo;
            private String address;
            private String name;
            @Override
            public void onClick(View view) {
                //传值
                name = list.get(i).getName();
                address = list.get(i).getAddress();
                String images = list.get(i).getLogo();
                String[] split = images.split("\\|");
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("address",address);
                intent.putExtra("logo",split[0]);
                intent.putExtra("cinemasId",list.get(i).getId());
                context.startActivity(intent);
            }
        });
        sRecommendedAdepter.image_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否登录
                if (SharedPreferencesUtils.getBoolean(context,"isLogin")){
                    //如果登录的话请求关注的接口关注该影院
                    Map<String,String> map = new HashMap();//添加参数
                    map.put("cinemaId",String.valueOf(list.get(i).getId()));//传入影院id
                    //添加头部信息
                    Map<String,String> headMap = new HashMap<>();
                    headMap.put("userId",String.valueOf(SharedPreferencesUtils.getInt(context,"userId")));
                    headMap.put("sessionId",SharedPreferencesUtils.getString(context,"sessionId"));
                    headMap.put("Content-Type","application/x-www-form-urlencoded");
                    //请求接口
                    new HttpUtil().get("/movieApi/cinema/v1/verify/followCinema",map,headMap).result(new HttpUtil.HttpListener() {
                        @Override
                        public void success(String data) {
                            RegisterBean followBean = new Gson().fromJson(data, RegisterBean.class);
                            if (followBean.getStatus().equals("0000")){
                                Toast.makeText(context,followBean.getMessage(),Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context,followBean.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void fail(String data) {

                        }
                    });
                }else {
                    Toast.makeText(context,"您还未登陆",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class sRecommendedAdepter extends RecyclerView.ViewHolder {

        private SimpleDraweeView recommendimg;
        private ImageView image_like;
        private TextView text;
        private TextView address;
         TextView distance;

        public sRecommendedAdepter(@NonNull View itemView) {
            super(itemView);

            recommendimg = (SimpleDraweeView) itemView.findViewById(R.id.activity_recommendimg);
            text = (TextView) itemView.findViewById(R.id.activity_text);
            address = (TextView) itemView.findViewById(R.id.activity_address);
            distance = (TextView) itemView.findViewById(R.id.activity_distance);
            image_like = itemView.findViewById(R.id.image_like);
        }
    }

}
