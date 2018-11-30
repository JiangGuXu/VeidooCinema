package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.DetailsActivity;
import com.bw.movie.bean.Recommendedbean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
/*
 * 推荐影院的适配器
 * 2018年11月28日 15:18:30
 * 程丹妮
 * 创建了基本的这个Adapter
 * */
public class RecommendedAdepter extends RecyclerView.Adapter<RecommendedAdepter.sRecommendedAdepter> {
    private List<Recommendedbean.Resultbean> list = new ArrayList<>();
    private Context context;

    public RecommendedAdepter(Context context, List<Recommendedbean.Resultbean> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public sRecommendedAdepter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_recommended, null);
        sRecommendedAdepter vi = new sRecommendedAdepter(view);
        return vi;
    }

    @Override
    public void onBindViewHolder(@NonNull sRecommendedAdepter sRecommendedAdepter, final int i) {
        sRecommendedAdepter.text.setText(list.get(i).getName());
        sRecommendedAdepter.address.setText(list.get(i).getAddress());
    // sRecommendedAdepter.distance.setText(list.get(i).getDistance());
    String images = list.get(i).getLogo();
    String[] split = images.split("\\|");
        sRecommendedAdepter.recommendimg.setImageURI(split[0]);
        sRecommendedAdepter.itemView.setOnClickListener(new View.OnClickListener() {

        private String logo;
        private String address;
        private String name;

        @Override
        public void onClick(View view) {
            //传值
            name = list.get(i).getName();
            address = list.get(i).getAddress();
            String images = list.get(i).getLogo();
            String[] split = images.split("\\|");
            //跳转到推荐影院的排期
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("address",address);
            intent.putExtra("logo",split[0]);
            intent.putExtra("cinemasId",list.get(i).getId());
            context.startActivity(intent);

        }
    });
}

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class sRecommendedAdepter extends RecyclerView.ViewHolder {

        private SimpleDraweeView recommendimg;
        private TextView text;
        private TextView address;
         TextView distance;

        public sRecommendedAdepter(@NonNull View itemView) {
            super(itemView);

            recommendimg = (SimpleDraweeView) itemView.findViewById(R.id.activity_recommendimg);
            text = (TextView) itemView.findViewById(R.id.activity_text);
            address = (TextView) itemView.findViewById(R.id.activity_address);
            distance = (TextView) itemView.findViewById(R.id.activity_distance);

        }
    }

}
