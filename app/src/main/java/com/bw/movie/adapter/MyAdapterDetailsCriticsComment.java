package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.DetailsComment;
import com.bw.movie.bean.Focus;
import com.bw.movie.utils.DateFormat.DateFormatForYou;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 影片评论的列表
 * 赵瑜峰
 */

public class MyAdapterDetailsCriticsComment extends RecyclerView.Adapter<MyAdapterDetailsCriticsComment.MyViewHodlerDetailsCriticsComment> {
    private Context context;
    private List<DetailsComment.ResultBean> list = new ArrayList<>();
    private int isgreat;
    private int greatnum;
    private int id;
    public MyAdapterDetailsCriticsComment(Context context){
        this.context=context;
    }
    public  void setList(List<DetailsComment.ResultBean> list, int id, int isgreat, int greatnum ){
        this.list=list;
        this.isgreat=isgreat;
        this.greatnum=greatnum;
        this.id=id;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHodlerDetailsCriticsComment onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.details_reccyler_comment_item,null);
        MyViewHodlerDetailsCriticsComment myViewHodlerDetailsDe = new MyViewHodlerDetailsCriticsComment(view);
        return myViewHodlerDetailsDe;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodlerDetailsCriticsComment myViewHodler, int i) {
        myViewHodler.img.setImageURI(list.get(i).getReplyHeadPic());
        myViewHodler.comments.setText(list.get(i).getReplyContent());
        myViewHodler.name.setText(list.get(i).getReplyUserName());
        if(isgreat==0){
            myViewHodler.like.setImageResource(R.drawable.com_icon_praise_default);
        }else{
            myViewHodler.like.setImageResource(R.drawable.com_icon_praise_selected);
        }
        DateFormatForYou dateFormatForYou = new DateFormatForYou();
        try {
            String s = dateFormatForYou.longToString(list.get(i).getReplyTime(), "yyyy-MM-dd HH:mm:ss");
            myViewHodler.time.setText(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myViewHodler.likeNum.setText(greatnum+"");
        myViewHodler.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isgreat==0){
                    like(id);
                }else{
                    Toast.makeText(context, "不能重复点赞", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void like(int commentId) {
        if(commentId!=0){
            if(SharedPreferencesUtils.getBoolean(context,"isLogin")){
                int userId = SharedPreferencesUtils.getInt(context, "userId");
                String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                Map<String,String> map = new HashMap<>();
                Toast.makeText(context, commentId+"", Toast.LENGTH_SHORT).show();
                map.put("commentId",commentId+"");
                Map<String,String> mapHead = new HashMap<>();
                mapHead.put("userId",userId+"");
                mapHead.put("sessionId",sessionId);
                new HttpUtil().postHead("/movieApi/movie/v1/verify/movieCommentGreat",map,mapHead).result(new HttpUtil.HttpListener() {
                    @Override
                    public void success(String data) {
                        Toast.makeText(context, data+"", Toast.LENGTH_SHORT).show();
                        Gson gson = new Gson();
                        Focus focus = gson.fromJson(data, Focus.class);
                        if("0000".equals(focus.getStatus())){
                            Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show();
                            listener.criticsChange();
                        }else{
                            Toast.makeText(context, "点赞失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void fail(String data) {

                    }
                });
            }else{
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHodlerDetailsCriticsComment extends RecyclerView.ViewHolder{

        private SimpleDraweeView img;
        private TextView name,comments,time,likeNum;
        private ImageView like;
        public MyViewHodlerDetailsCriticsComment(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.details_comment_img);
            name = itemView.findViewById(R.id.details_comment_name);
            comments = itemView.findViewById(R.id.details_comment_comments);
            time = itemView.findViewById(R.id.details_comment_time);
            likeNum = itemView.findViewById(R.id.details_comment_likenum);
            like = itemView.findViewById(R.id.details_comment_like);
        }
    }
    private CriticsFouceListener listener;
    public void result(CriticsFouceListener listener){
        this.listener=listener;
    }
    public interface CriticsFouceListener{
        void criticsChange();
        void criticsComments();
    }
}
