package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.DetailsFilmActivity;
import com.bw.movie.bean.FilmListData;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterFilmList_recyler extends RecyclerView.Adapter<MyAdapterFilmList_recyler.MyViewHodlerFilmList_recyler> {
    private Context context;
    private List<FilmListData.ResultBean> list = new ArrayList();
    public MyAdapterFilmList_recyler(Context context){
        this.context=context;
    }
    public void setList( List<FilmListData.ResultBean> list ){
        this.list=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHodlerFilmList_recyler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.film_list_recyler_item,null);
        MyViewHodlerFilmList_recyler myViewHodlerFilmList_recyler = new MyViewHodlerFilmList_recyler(view);
        return myViewHodlerFilmList_recyler;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodlerFilmList_recyler myViewHodlerFilmList_recyler, final int i) {
        myViewHodlerFilmList_recyler.img.setImageURI(list.get(i).getImageUrl());
        myViewHodlerFilmList_recyler.name.setText(list.get(i).getName());
        myViewHodlerFilmList_recyler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsFilmActivity.class);
                intent.putExtra("id",list.get(i).getId()+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHodlerFilmList_recyler extends RecyclerView.ViewHolder{

        private SimpleDraweeView img;
        private TextView name;

        public MyViewHodlerFilmList_recyler(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.film_list_recyler_img);
            name = itemView.findViewById(R.id.film_list_recyler_name);
        }
    }
}
