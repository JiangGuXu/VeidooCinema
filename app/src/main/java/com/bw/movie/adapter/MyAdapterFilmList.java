package com.bw.movie.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class MyAdapterFilmList extends BaseAdapter {
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
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHodlerFilmList myViewHodlerFilmList;
        if(view==null){
            myViewHodlerFilmList=new MyViewHodlerFilmList();
            view = View.inflate(context, R.layout.film_list_item,null);
            myViewHodlerFilmList.title = (TextView)view.findViewById(R.id.film_list_title);
            myViewHodlerFilmList.recyclerView = (RecyclerView)view.findViewById(R.id.fiml_list_recyler);
            view.setTag(myViewHodlerFilmList);
        }else{
            myViewHodlerFilmList = (MyViewHodlerFilmList) view.getTag();
        }
            myViewHodlerFilmList.title.setText(list.get(i).getTitle());
        myAdapterFilmList_recyler = new MyAdapterFilmList_recyler(context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        myViewHodlerFilmList.recyclerView.setLayoutManager(staggeredGridLayoutManager);
        myViewHodlerFilmList.recyclerView.setAdapter(myAdapterFilmList_recyler);
        dohttp(list.get(i).getUrl());
        return view;
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

    private class MyViewHodlerFilmList{
        private TextView title;
        private RecyclerView recyclerView;
    }
}
