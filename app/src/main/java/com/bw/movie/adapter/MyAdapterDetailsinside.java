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
import com.bw.movie.bean.Detailsinsidebean;
import com.bw.movie.bean.Recommendedbean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
/**
 * 推荐影院适配器
 * 程丹妮
 */
public class MyAdapterDetailsinside extends RecyclerView.Adapter<MyAdapterDetailsinside.sMyAdapterDetailsinside> {
    private List<Detailsinsidebean.Resultbean> list = new ArrayList<>();
    private Context context;

    public MyAdapterDetailsinside(Context context, List<Detailsinsidebean.Resultbean> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public sMyAdapterDetailsinside onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_detailsinside, null);
        sMyAdapterDetailsinside vi = new sMyAdapterDetailsinside(view);
        return vi;
    }

    @Override
    public void onBindViewHolder(@NonNull sMyAdapterDetailsinside sMyAdapterDetailsinside1, final int i) {
        sMyAdapterDetailsinside1.lin.setText(list.get(i).getAddress());
        sMyAdapterDetailsinside1.phone.setText(list.get(i).getPhone());
        sMyAdapterDetailsinside1.details.setText(list.get(i).getVehicleRoute());
        sMyAdapterDetailsinside1.show_gong.setText(list.get(i).getName());
        sMyAdapterDetailsinside1.show_zijia.setText(list.get(i).getBusinessHoursContent());
        sMyAdapterDetailsinside1.itemView.setOnClickListener(new View.OnClickListener() {

            private String content;
            private String vehicleRoute;
            private String phone;
            private String address;
            private String name;

            @Override
            public void onClick(View view) {
                //传值
               // name = list.get(i).getName();
                address = list.get(i).getAddress();
                phone = list.get(i).getPhone();
                vehicleRoute = list.get(i).getVehicleRoute();
                content = list.get(i).getBusinessHoursContent();
                Intent intent = new Intent(context, DetailsActivity.class);
                //intent.putExtra("name",name);
                intent.putExtra("vehicleRoute",vehicleRoute);
                intent.putExtra("phone", phone);
                intent.putExtra("address",address);
                intent.putExtra("content",content);
                intent.putExtra("cinemasId",list.get(i).getId());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class sMyAdapterDetailsinside extends RecyclerView.ViewHolder {
        private final TextView show_zijia;
        private final TextView show_gong;

        private  TextView details;
        private  TextView phone;
        private  TextView lin;

        public sMyAdapterDetailsinside(@NonNull View itemView) {
            super(itemView);
            details = (TextView) itemView.findViewById(R.id.address_details);
            phone = (TextView) itemView.findViewById(R.id.phone_details);
            lin = (TextView) itemView.findViewById(R.id.line_details);

            show_gong = (TextView) itemView.findViewById(R.id.show_gong);
            show_zijia = (TextView) itemView.findViewById(R.id.show_zijia);
        }
    }
}
