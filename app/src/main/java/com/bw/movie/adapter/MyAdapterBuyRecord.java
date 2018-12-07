package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.BuyRecord;
import com.bw.movie.utils.DateFormat.DateFormatForYou;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MyAdapterBuyRecord extends XRecyclerView.Adapter<MyAdapterBuyRecord.MyViewHodlerBuyRecord> {
    private Context context;
    private List<BuyRecord.ResultBean> list = new ArrayList();
    public MyAdapterBuyRecord(Context context){
        this.context=context;
    }
    public void setList( List<BuyRecord.ResultBean> list ){
        this.list=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHodlerBuyRecord onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.buyrecord_recyler_item,null);
        MyViewHodlerBuyRecord myViewHodlerFilmList_recyler = new MyViewHodlerBuyRecord(view);
        return myViewHodlerFilmList_recyler;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodlerBuyRecord myViewHodler, final int i) {
        long createTime = list.get(i).getCreateTime();
        DateFormatForYou dateFormatForYou = new DateFormatForYou();
        try {
            String s = dateFormatForYou.longToString(createTime, "yyyy-MM-dd HH:mm:ss");
            myViewHodler.time.setText(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myViewHodler.name.setText(list.get(i).getMovieName());
        myViewHodler.indent.setText(list.get(i).getOrderId());
        myViewHodler.cinema.setText(list.get(i).getCinemaName());
        myViewHodler.screens.setText(list.get(i).getScreeningHall());
        myViewHodler.time1.setText(list.get(i).getBeginTime()+"--"+list.get(i).getEndTime());
        myViewHodler.num.setText(list.get(i).getAmount()+"");
        myViewHodler.money.setText(list.get(i).getPrice()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHodlerBuyRecord extends XRecyclerView.ViewHolder{

        private TextView name,time,indent,cinema,screens,time1,num,money;

        public MyViewHodlerBuyRecord(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.buyrecord_time);
            name = itemView.findViewById(R.id.buyrecord_name);
            indent = itemView.findViewById(R.id.buyrecord_indent);
            cinema = itemView.findViewById(R.id.buyrecord_cinema);
            screens = itemView.findViewById(R.id.buyrecord_screens);
            time1 = itemView.findViewById(R.id.buyrecord_time1);
            num = itemView.findViewById(R.id.buyrecord_num);
            money = itemView.findViewById(R.id.buyrecord_money);

        }
    }
}
