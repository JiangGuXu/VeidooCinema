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
import com.bw.movie.activity.NearActivity;
import com.bw.movie.bean.Nearbean;
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
 * 附近影院
 * 程丹妮
 * 创建了基本的这个NearAdepter
 * */
public class NearAdepter extends RecyclerView.Adapter<NearAdepter.snearAdepter> {
    private List<Nearbean.Resultbean.NearbyCinemaListbean> list = new ArrayList<>();
    private Context context;

    public NearAdepter(Context context, List<Nearbean.Resultbean.NearbyCinemaListbean> list) {
        this.context = context;
        this.list = list;
        //notifyDataSetChanged();
    }

    @NonNull
    @Override
    public snearAdepter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_near, null);
        snearAdepter vi = new snearAdepter(view);
        return vi;
    }

    @Override
    public void onBindViewHolder(@NonNull snearAdepter snearAdepter, final int i) {
        snearAdepter.text.setText(list.get(i).getName());
        snearAdepter.address.setText(list.get(i).getAddress());
       // snearAdepter.distance.setText(list.get(i).getDistance());
        String images = list.get(i).getLogo();
        String[] split = images.split("\\|");
        snearAdepter.recommendimg.setImageURI(split[0]);
        snearAdepter.itemView.setOnClickListener(new View.OnClickListener() {

            private String address;
            private String name;

            @Override
            public void onClick(View view) {
                name = list.get(i).getName();
                address = list.get(i).getAddress();
                String images = list.get(i).getLogo();
                String[] split = images.split("\\|");
                // 跳转附近影院的排期
                Intent intent = new Intent(context, NearActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("address", address);
                intent.putExtra("logo", split[0]);
                intent.putExtra("cinemasId", list.get(i).getId());
                context.startActivity(intent);

            }
        });
        snearAdepter.image_like.setOnClickListener(new View.OnClickListener() {
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
                    new HttpUtil(context).result(new HttpUtil.HttpListener() {
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

                        @Override
                        public void notNetwork(View data) {

                        }
                    }).get("/movieApi/cinema/v1/verify/followCinema",map,headMap,"",true,false);
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

    public class snearAdepter extends RecyclerView.ViewHolder {
        private SimpleDraweeView recommendimg;
        private TextView text;
        private TextView address;
        TextView distance;
        private ImageView image_like;

        public snearAdepter(@NonNull View itemView) {
            super(itemView);
            recommendimg = (SimpleDraweeView) itemView.findViewById(R.id.activity_recommendimg);
            text = (TextView) itemView.findViewById(R.id.activity_text);
            address = (TextView) itemView.findViewById(R.id.activity_address);
            distance = (TextView) itemView.findViewById(R.id.activity_distance);
            image_like = itemView.findViewById(R.id.image_like);
        }
    }
}
