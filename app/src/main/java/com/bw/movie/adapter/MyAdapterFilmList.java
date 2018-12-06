package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.activity.SearchActivity;
import com.bw.movie.bean.FilmListData;

import java.util.ArrayList;
import java.util.List;
/**
 * 首页电影类型列表
 * 赵瑜峰
 */
public class MyAdapterFilmList extends RecyclerView.Adapter<MyAdapterFilmList.MyViewHodlerFilmList>{
    private Context context;
    private List<FilmListData> list = new ArrayList();
    private List<String> titles=new ArrayList<>();
    private MyAdapterFilmListrecyler myAdapterFilmList_recyler;


    public MyAdapterFilmList (Context context){
        this.context=context;
    }
    public void setList(List<FilmListData> list,List<String> titles){
        this.list=list;
        this.titles=titles;
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
    public void onBindViewHolder(@NonNull MyViewHodlerFilmList myViewHodlerFilmList,final int i) {
        myViewHodlerFilmList.title.setText(titles.get(i));
        myAdapterFilmList_recyler = new MyAdapterFilmListrecyler(context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        myViewHodlerFilmList.recyclerView.setLayoutManager(staggeredGridLayoutManager);
        myViewHodlerFilmList.recyclerView.setAdapter(myAdapterFilmList_recyler);
        myAdapterFilmList_recyler.setList(list.get(i).getResult());
        myViewHodlerFilmList.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SearchActivity.class);
                intent.putExtra("position",i);
                ((MainActivity)context).startActivity(intent);
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
        private ImageView img;

        public MyViewHodlerFilmList(@NonNull View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.film_list_title);
            recyclerView = (RecyclerView)itemView.findViewById(R.id.fiml_list_recyler);
            img = itemView.findViewById(R.id.film_list_img);
        }
    }



}





