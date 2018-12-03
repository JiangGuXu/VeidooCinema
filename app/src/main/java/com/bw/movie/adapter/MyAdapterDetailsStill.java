package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterDetailsStill extends RecyclerView.Adapter<MyAdapterDetailsStill.MyViewHodlerDetailsStill> {
    private Context context;
    private List<String > list = new ArrayList<>();
    public MyAdapterDetailsStill(Context context){
        this.context=context;
    }
    public  void setList(List<String > list ){
        this.list=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHodlerDetailsStill onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.details_reccyler_still_item,null);
        MyViewHodlerDetailsStill myViewHodlerDetailsDe = new MyViewHodlerDetailsStill(view);
        return myViewHodlerDetailsDe;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodlerDetailsStill myViewHodler, int i) {
        Glide.with(context).load(list.get(i)).into(myViewHodler.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHodlerDetailsStill extends RecyclerView.ViewHolder{

        private ImageView img;

        public MyViewHodlerDetailsStill(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.details_still_img);
        }
    }
}
