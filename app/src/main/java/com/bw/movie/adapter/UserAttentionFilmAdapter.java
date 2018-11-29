package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class UserAttentionFilmAdapter extends RecyclerView.Adapter<UserAttentionFilmAdapter.MyViewHolder> {

    private List<String> list = new ArrayList<>();
    private Context context;


    public UserAttentionFilmAdapter(Context context) {
        this.context = context;
    }


    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.user_attention_cinema_item_layout, null);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView my_attention_film_sdv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            my_attention_film_sdv = itemView.findViewById(R.id.my_attention_film_sdv);

        }
    }
}
