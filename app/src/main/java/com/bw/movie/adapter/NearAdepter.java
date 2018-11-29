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
import com.bw.movie.activity.NearActivity;
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
    public void onBindViewHolder(@NonNull snearAdepter snearAdepter, final int i) {
        snearAdepter.text.setText(list.get(i).getName());
        snearAdepter.address.setText(list.get(i).getAddress());
        // sRecommendedAdepter.distance.setText(list.get(i).getDistance());
        String images = list.get(i).getLogo();
        String[] split = images.split("\\|");
       snearAdepter.recommendimg.setImageURI(split[0]);
        snearAdepter.itemView.setOnClickListener(new View.OnClickListener() {

            private String address;
            private String name;

            @Override
            public void onClick(View view) {
                name = list.get(i).getName();
                address = list.get(i).getAddress();
                String images = list.get(i).getLogo();
                String[] split = images.split("\\|");
                Intent intent = new Intent(context, NearActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("address", address);
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
