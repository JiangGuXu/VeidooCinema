package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.Nearbean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class NearAdepter extends RecyclerView.Adapter<NearAdepter.snearAdepter> {
    private List<Nearbean.Resultbean.NearbyCinemaListbean> list = new ArrayList<>();
    private Context context;

    public NearAdepter(Context context, List<Nearbean.Resultbean.NearbyCinemaListbean> list) {
        this.context = context;
        this.list = list;
        //notifyDataSetChanged();
    }

    @NonNull
    @Override
    public snearAdepter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_near, null);
        snearAdepter vi = new snearAdepter(view);
        return vi;
    }

    @Override
    public void onBindViewHolder(@NonNull snearAdepter snearAdepter, int i) {
        snearAdepter.text.setText(list.get(i).getName());
        snearAdepter.address.setText(list.get(i).getAddress());
        // sRecommendedAdepter.distance.setText(list.get(i).getDistance());
        String images = list.get(i).getLogo();
        String[] split = images.split("\\|");
       snearAdepter.recommendimg.setImageURI(split[0]);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class snearAdepter extends RecyclerView.ViewHolder {
        private SimpleDraweeView recommendimg;
        private TextView text;
        private TextView address;
        TextView distance;
        public snearAdepter(@NonNull View itemView) {
            super(itemView);
            recommendimg = (SimpleDraweeView) itemView.findViewById(R.id.activity_recommendimg);
            text = (TextView) itemView.findViewById(R.id.activity_text);
            address = (TextView) itemView.findViewById(R.id.activity_address);
            distance = (TextView) itemView.findViewById(R.id.activity_distance);


        }
    }
}
