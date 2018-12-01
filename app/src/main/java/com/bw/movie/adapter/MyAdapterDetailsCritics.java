package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.model.Critics;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterDetailsCritics extends RecyclerView.Adapter<MyAdapterDetailsCritics.MyViewHodlerDetailsCritics> {
    private Context context;
    private List<Critics.ResultBean> list = new ArrayList<>();
    public MyAdapterDetailsCritics(Context context){
        this.context=context;
    }
    public  void setList(List<Critics.ResultBean > list ){
        this.list=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHodlerDetailsCritics onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.details_reccyler_critics_item,null);
        MyViewHodlerDetailsCritics myViewHodlerDetailsDe = new MyViewHodlerDetailsCritics(view);
        return myViewHodlerDetailsDe;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodlerDetailsCritics myViewHodler, int i) {
        myViewHodler.img.setImageURI(list.get(i).getCommentHeadPic());
        myViewHodler.comments.setText(list.get(i).getCommentContent());
        myViewHodler.name.setText(list.get(i).getCommentUserName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHodlerDetailsCritics extends RecyclerView.ViewHolder{

        private SimpleDraweeView img;
        private TextView name,comments;

        public MyViewHodlerDetailsCritics(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.details_critics_img);
            name = itemView.findViewById(R.id.details_critics_name);
            comments = itemView.findViewById(R.id.details_critics_comments);
        }
    }
}
