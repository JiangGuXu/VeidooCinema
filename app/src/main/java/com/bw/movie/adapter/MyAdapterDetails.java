package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.Detailsbean;

import java.util.ArrayList;
import java.util.List;
/*
 * 推荐影院详情Adapter
 * 2018年11月27日 15:18:30
 * 程丹妮
 * 创建了基本的这个presenter
 * */
public class MyAdapterDetails extends RecyclerView.Adapter<MyAdapterDetails.sMyAdapterDetails> {
    private List<Detailsbean.Resultbean> list = new ArrayList<>();
    private Context context;

    public MyAdapterDetails(Context context) {
        this.context = context;
    }

    public void setList(List<Detailsbean.Resultbean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public sMyAdapterDetails onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_scheduling, null);
        sMyAdapterDetails vi = new sMyAdapterDetails(view);
        return vi;
    }

    @Override
    public void onBindViewHolder(@NonNull sMyAdapterDetails sMyAdapterDetails, final int i) {
        sMyAdapterDetails.name1.setText(list.get(i).getScreeningHall());
        sMyAdapterDetails.time.setText(list.get(i).getBeginTime());
        sMyAdapterDetails.te.setText(list.get(i).getDuration());
        sMyAdapterDetails.time1.setText(list.get(i).getEndTime() + "end");
        sMyAdapterDetails.price.setText("￥" + list.get(i).getSeatsTotal());
        sMyAdapterDetails.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class sMyAdapterDetails extends RecyclerView.ViewHolder {
        private final TextView name1;
        private final TextView time;
        private final TextView price;
        private final TextView time1;
        private final TextView te;
        private final LinearLayout lin;
        public sMyAdapterDetails(@NonNull View itemView) {
            super(itemView);
            te = (TextView) itemView.findViewById(R.id.activity_qian);
            time1 = (TextView) itemView.findViewById(R.id.activity_time1);
            name1 = (TextView) itemView.findViewById(R.id.activity_name1);
            time = (TextView) itemView.findViewById(R.id.activity_time);
            price = (TextView) itemView.findViewById(R.id.activity_price);
            lin = itemView.findViewById(R.id.details_lin);
        }
    }
    //声明接口
    private ItemClickListener listener;
    //set方法
    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }
    //定义接口
    public interface ItemClickListener{
        void onItemClick(int position);
    }

}
