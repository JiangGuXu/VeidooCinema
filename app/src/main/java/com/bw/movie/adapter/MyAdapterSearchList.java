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
import com.bw.movie.activity.DetailsFilmActivity;
import com.bw.movie.bean.FilmListData;
import com.bw.movie.bean.Focus;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * search页面电影列表
 * 赵瑜峰
 */
public class MyAdapterSearchList extends RecyclerView.Adapter<MyAdapterSearchList.MyViewHodlerSearchList> {
    private Context context;
    private List<FilmListData.ResultBean> list = new ArrayList();
    private String title;
    public MyAdapterSearchList(Context context){
        this.context=context;
    }
    public void setList( List<FilmListData.ResultBean> list ,String title){
        this.list=list;
        this.title=title;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHodlerSearchList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.search_recyler_item,null);
        MyViewHodlerSearchList myViewHodlerFilmList_recyler = new MyViewHodlerSearchList(view);
        return myViewHodlerFilmList_recyler;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodlerSearchList myViewHodlerFilmList_recyler, final int i) {
        myViewHodlerFilmList_recyler.img.setImageURI(list.get(i).getImageUrl());
        myViewHodlerFilmList_recyler.name.setText(list.get(i).getName());
        myViewHodlerFilmList_recyler.introduction.setText(list.get(i).getSummary());
        myViewHodlerFilmList_recyler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsFilmActivity.class);
                intent.putExtra("id",list.get(i).getId()+"");
                context.startActivity(intent);
            }
        });
        if(list.get(i).isFollowMovie()){
            myViewHodlerFilmList_recyler.focus.setImageResource(R.drawable.com_icon_collection_default);
        }else{
            myViewHodlerFilmList_recyler.focus.setImageResource(R.drawable.com_icon_collection_selected);
        }
        myViewHodlerFilmList_recyler.focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( SharedPreferencesUtils.getBoolean(context, "isLogin")){
                    int userId = SharedPreferencesUtils.getInt(context, "userId");
                    String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                    if(list.get(i).isFollowMovie()){
                        doHpptFocus (i,userId,sessionId);
                    }else{
                        doHttpCancel(i,userId,sessionId);
                    }

                }else{
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void doHttpCancel(int i, int userId, String sessionId) {
        Map<String,String> map = new HashMap<>();
        map.put("movieId",list.get(i).getId()+"");
        Map<String,String> mapHead = new HashMap<>();
        mapHead.put("userId",userId+"");
        mapHead.put("sessionId",sessionId);
        new HttpUtil().get("/movieApi/movie/v1/verify/cancelFollowMovie",map,mapHead).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                Focus focus = gson.fromJson(data, Focus.class);
                if("0000".equals(focus.getStatus())){
                    Toast.makeText(context, "取消关注成功", Toast.LENGTH_SHORT).show();
                    listener.fouceChange(title);
                }else{
                    Toast.makeText(context, "取消关注失败", Toast.LENGTH_SHORT).show();
                }
                listener.fouceChange(title);
            }

            @Override
            public void fail(String data) {

            }
        });
    }

    private void doHpptFocus(int i, int userId, String sessionId) {
        Map<String,String> map = new HashMap<>();
        map.put("movieId",list.get(i).getId()+"");
        Map<String,String> mapHead = new HashMap<>();
        mapHead.put("userId",userId+"");
        mapHead.put("sessionId",sessionId);
        new HttpUtil().get( "/movieApi/movie/v1/verify/followMovie",map,mapHead).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                Focus focus = gson.fromJson(data, Focus.class);
                if("0000".equals(focus.getStatus())){
                    Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
                    listener.fouceChange(title);
                }else{
                    Toast.makeText(context, "关注失败", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void fail(String data) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHodlerSearchList extends RecyclerView.ViewHolder{

        private SimpleDraweeView img;
        private TextView name,introduction;
        private ImageView focus;

        public MyViewHodlerSearchList(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.search_recyler_img);
            name = itemView.findViewById(R.id.search_recyler_name);
            introduction = itemView.findViewById(R.id.search_recyler_Introduction);
            focus = itemView.findViewById(R.id.search_recyler_focus);
        }
    }
    private SearchFouceListener listener;
    public void result(SearchFouceListener listener){
        this.listener=listener;
    }
    public interface SearchFouceListener{
        void fouceChange(String data);
    }
}