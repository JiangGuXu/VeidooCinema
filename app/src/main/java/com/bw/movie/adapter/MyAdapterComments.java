package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.Commentsben;
import com.bw.movie.bean.Detailsinsidebean;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapterComments extends RecyclerView.Adapter<MyAdapterComments.sMyAdapterComments> {
    private List<Commentsben.Resultbean> list = new ArrayList<>();
    private Context context;

    public MyAdapterComments(Context context, List<Commentsben.Resultbean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public sMyAdapterComments onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_comments, null);
        sMyAdapterComments vi = new sMyAdapterComments(view);
        return vi;
    }

    @Override
    public void onBindViewHolder(@NonNull final sMyAdapterComments sMyAdapterComments, final int i) {
        sMyAdapterComments.activity_hean.setImageURI(list.get(i).getCommentHeadPic());
        sMyAdapterComments.activity_ming.setText(list.get(i).getCommentUserName());
        sMyAdapterComments.activity_comment.setText(list.get(i).getCommentContent());
        sMyAdapterComments.activity_like.setText(list.get(i).getGreatNum() + "");
        if(list.get(i).getIsGreat()==0){
            sMyAdapterComments.activity_give.setImageResource(R.mipmap.com_icon_praise_default);
        }else{
            sMyAdapterComments.activity_give.setImageResource(R.mipmap.com_icon_praise_selected);
        }
        sMyAdapterComments.activity_give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url3 = "/movieApi/cinema/v1/verify/cinemaCommentGreat";
                Map<String, String> map = new HashMap<>();
                map.put("commentId", list.get(i).getCommentId() + "");
                HashMap<String, String> map1 = new HashMap<>();
                int userId = SharedPreferencesUtils.getInt(context, "userId");
                String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                map1.put("userId", userId + "");
                map1.put("sessionId", sessionId);
                map1.put("Content-Type", "application/x-www-form-urlencoded");
                new HttpUtil().postHead(url3, map, map1).result(new HttpUtil.HttpListener() {
                    @Override
                    public void success(String data) {
                        if(data.contains("成功")){
                            sMyAdapterComments.activity_give.setImageResource(R.mipmap.com_icon_praise_selected);
                            sMyAdapterComments.activity_like.setText(list.get(i).getGreatNum() + 1 + "");
                        }else if(data.contains("不能重复点赞")){
                            Toast.makeText(context,"不能重复点赞!",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void fail(String data) {
                        Log.i("cc", "fail" + data);
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class sMyAdapterComments extends RecyclerView.ViewHolder {

        private final TextView activity_like;
        private final TextView activity_data;
        private final TextView activity_comment;
        private final TextView activity_ming;
        private final SimpleDraweeView activity_hean;
        private final SimpleDraweeView activity_give;

        public sMyAdapterComments(@NonNull View itemView) {
            super(itemView);
            activity_hean = (SimpleDraweeView) itemView.findViewById(R.id.activity_hean);
            activity_ming = (TextView) itemView.findViewById(R.id.activity_ming);
            activity_comment = (TextView) itemView.findViewById(R.id.activity_comment);
            activity_data = (TextView) itemView.findViewById(R.id.activity_data);
            activity_like = (TextView) itemView.findViewById(R.id.activity_like);
            activity_give = (SimpleDraweeView) itemView.findViewById(R.id.activity_give);

        }
    }
}
