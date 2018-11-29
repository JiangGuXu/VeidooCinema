package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.model.FilmListData;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterFilmBanner extends RecyclerView.Adapter<MyAdapterFilmBanner.MyViewHodlerFilmBanner> {
    private Context context;

    public MyAdapterFilmBanner(Context context) {
        this.context = context;
    }

    private List<FilmListData.ResultBean> list = new ArrayList();

    public void setList(List<FilmListData.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHodlerFilmBanner onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.film_recyler_banner, null);
        MyViewHodlerFilmBanner myViewHodlerFilmBanner = new MyViewHodlerFilmBanner(view);
        return myViewHodlerFilmBanner;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodlerFilmBanner myViewHodlerFilmBanner, final int i) {
        myViewHodlerFilmBanner.img.setImageURI(list.get(i % list.size()).getImageUrl());
        myViewHodlerFilmBanner.textView.setText(list.get(i % list.size()).getName());
        myViewHodlerFilmBanner.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHodlerFilmBanner extends RecyclerView.ViewHolder {

        private SimpleDraweeView img;
        private TextView textView;

        public MyViewHodlerFilmBanner(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.film_banner_img);
            textView = itemView.findViewById(R.id.film_banner_name);
        }
    }

    private RecyclerItemListener listener;

    public interface RecyclerItemListener {
        void onClick(int position);
    }

    public void setListener(RecyclerItemListener listener) {
        this.listener = listener;
    }

}
