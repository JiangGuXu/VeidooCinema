package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
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

/**
 * 显示该播放该电影的影院
 * 适配器
 * 姜谷蓄
 */

public class FindCinemaAdapter extends RecyclerView.Adapter<FindCinemaAdapter.MyViewHolder>{
    private List<Recommendedbean.Resultbean> list = new ArrayList<>();
    private Context context;

    public FindCinemaAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<Recommendedbean.Resultbean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public FindCinemaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_recommended, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FindCinemaAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.text.setText(list.get(i).getName());
        myViewHolder.address.setText(list.get(i).getAddress());
        String images = list.get(i).getLogo();
        String[] split = images.split("\\|");
        myViewHolder.recommendimg.setImageURI(split[0]);
        //条目的点击事件
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(i);
            }
        });
        //点击关注影院
        myViewHolder.image_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView recommendimg;
        private TextView text;
        private TextView address;
        private ImageView image_like;
        TextView distance;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recommendimg = (SimpleDraweeView) itemView.findViewById(R.id.activity_recommendimg);
            text = (TextView) itemView.findViewById(R.id.activity_text);
            address = (TextView) itemView.findViewById(R.id.activity_address);
            distance = (TextView) itemView.findViewById(R.id.activity_distance);
            image_like = itemView.findViewById(R.id.image_like);
        }
    }
    //声明接口
    private ItemClickListener listener;
    //set方法
    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }
    //定义接口
    public interface ItemClickListener{
        //实现点击的方法，传递条目下标
        void onItemClick(int position);
    }
}
