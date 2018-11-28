package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.Recommendedbean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

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
    public void onBindViewHolder(@NonNull sRecommendedAdepter sRecommendedAdepter, int i) {
        sRecommendedAdepter.text.setText(list.get(i).getName());
        sRecommendedAdepter.address.setText(list.get(i).getAddress());
    // sRecommendedAdepter.distance.setText(list.get(i).getDistance());
        String images = list.get(i).getLogo();
        String[] split = images.split("\\|");
        sRecommendedAdepter.recommendimg.setImageURI(split[0]);
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
