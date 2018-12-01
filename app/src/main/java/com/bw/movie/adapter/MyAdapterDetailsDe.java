package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterDetailsDe extends RecyclerView.Adapter<MyAdapterDetailsDe.MyViewHodlerDetailsDe> {
    private Context context;
    private String[] names;
    public MyAdapterDetailsDe( Context context){
        this.context=context;
    }
    public  void setList(String[] names){
        this.names=names;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHodlerDetailsDe onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.details_recyler_actor_item,null);
        MyViewHodlerDetailsDe myViewHodlerDetailsDe = new MyViewHodlerDetailsDe(view);
        return myViewHodlerDetailsDe;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodlerDetailsDe myViewHodler, int i) {
        myViewHodler.name.setText(names[i]);
        myViewHodler.actor.setText(names[i]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class MyViewHodlerDetailsDe extends RecyclerView.ViewHolder{

        private  TextView name;
        private TextView actor;

        public MyViewHodlerDetailsDe(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.details_recyler_name);
            actor = itemView.findViewById(R.id.details_recyler_actor);
        }
    }
}
