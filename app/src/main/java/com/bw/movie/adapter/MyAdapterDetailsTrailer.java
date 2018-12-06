package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.Details;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
/**
 * 预告片的列表
 * 赵瑜峰
 */
public class MyAdapterDetailsTrailer extends RecyclerView.Adapter<MyAdapterDetailsTrailer.MyViewHodlerDetailsTrailer> {
    private Context context;
    private List<Details.ResultBean.ShortFilmListBean> list = new ArrayList<>();
    public MyAdapterDetailsTrailer(Context context){
        this.context=context;
    }
    public  void setList(List<Details.ResultBean.ShortFilmListBean > list ){
        this.list=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHodlerDetailsTrailer onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.details_reccyler_trailer_item,null);
        MyViewHodlerDetailsTrailer myViewHodlerDetailsDe = new MyViewHodlerDetailsTrailer(view);
        return myViewHodlerDetailsDe;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodlerDetailsTrailer myViewHodler, int i) {
        myViewHodler.jzvdStd.setUp(list.get(i).getVideoUrl() ,"", Jzvd.SCREEN_WINDOW_NORMAL);
        Glide.with(context).load(list.get(i).getImageUrl()).into(myViewHodler.jzvdStd.thumbImageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHodlerDetailsTrailer extends RecyclerView.ViewHolder{

        private JzvdStd jzvdStd;

        public MyViewHodlerDetailsTrailer(@NonNull View itemView) {
            super(itemView);
            jzvdStd = itemView.findViewById(R.id.details_trailer_videoplayer);
        }
    }

}
