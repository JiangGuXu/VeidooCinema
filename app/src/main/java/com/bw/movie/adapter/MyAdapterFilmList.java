package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.model.FilmList;
import com.bw.movie.model.FilmListData;
import com.bw.movie.utils.net.HttpUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapterFilmList extends RecyclerView.Adapter<MyAdapterFilmList.MyViewHodlerFilmList>{
    private Context context;
    private List<FilmList> list = new ArrayList();
    private MyAdapterFilmList_recyler myAdapterFilmList_recyler;

    public MyAdapterFilmList (Context context){
        this.context=context;
    }
    public void setList(List<FilmList> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHodlerFilmList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.film_list_item,null);
        MyViewHodlerFilmList myViewHodlerFilmList = new MyViewHodlerFilmList(view);
        return myViewHodlerFilmList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodlerFilmList myViewHodlerFilmList, int i) {
        myViewHodlerFilmList.title.setText(list.get(i).getTitle());
        myAdapterFilmList_recyler = new MyAdapterFilmList_recyler(context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        myViewHodlerFilmList.recyclerView.setLayoutManager(staggeredGridLayoutManager);
        myViewHodlerFilmList.recyclerView.setAdapter(myAdapterFilmList_recyler);
        dohttp(list.get(i).getUrl());
    }
    private void dohttp(final String url) {
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("count","10");
        new HttpUtil().get(url,map).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                FilmListData filmListData = gson.fromJson(data, FilmListData.class);
                List<FilmListData.ResultBean> result = filmListData.getResult();
                if(result.size()==0){
                    dohttp(url);
                }
                myAdapterFilmList_recyler.setList(result);
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

    public class MyViewHodlerFilmList extends RecyclerView.ViewHolder{

        private TextView title;
        private RecyclerView recyclerView;

        public MyViewHodlerFilmList(@NonNull View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.film_list_title);
            recyclerView = (RecyclerView)itemView.findViewById(R.id.fiml_list_recyler);
        }
    }



}





