package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.Critics;
import com.bw.movie.bean.Focus;
import com.bw.movie.utils.dateformat.DateFormatForYou;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 影片的评论列表
 * 赵瑜峰
 */
public class MyAdapterDetailsCritics extends XRecyclerView.Adapter<MyAdapterDetailsCritics.MyViewHodlerDetailsCritics> {
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
    public void onBindViewHolder(@NonNull MyViewHodlerDetailsCritics myViewHodler, final int i) {
        myViewHodler.img.setImageURI(list.get(i).getCommentHeadPic());
        myViewHodler.comments.setText(list.get(i).getCommentContent());
        myViewHodler.name.setText(list.get(i).getCommentUserName());
        if(list.get(i).getIsGreat()==0){
            myViewHodler.like.setImageResource(R.drawable.com_icon_praise_default);
        }else{
            myViewHodler.like.setImageResource(R.drawable.com_icon_praise_selected);
        }
        DateFormatForYou dateFormatForYou = new DateFormatForYou();
        try {
            String s = dateFormatForYou.longToString(list.get(i).getCommentTime(), "yyyy-MM-dd HH:mm:ss");
            myViewHodler.time.setText(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myViewHodler.likeNum.setText(list.get(i).getGreatNum()+"");
        myViewHodler.comNum.setText(list.get(i).getReplyNum()+"");
        myViewHodler.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(i).getIsGreat()==0){
                    like(list.get(i).getCommentId());
                }else{
                    Toast.makeText(context, "不能重复点赞", Toast.LENGTH_SHORT).show();
                }

            }
        });
        myViewHodler.commentsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.criticsComments(list.get(i).getIsGreat(),list.get(i).getGreatNum(),list.get(i).getCommentId());
            }
        });
    }

    private void like(int commentId) {
        if(commentId!=0){
            if(SharedPreferencesUtils.getBoolean(context,"isLogin")){
                int userId = SharedPreferencesUtils.getInt(context, "userId");
                String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                Map<String,String> map = new HashMap<>();
                map.put("commentId",commentId+"");
                Map<String,String> mapHead = new HashMap<>();
                mapHead.put("userId",userId+"");
                mapHead.put("sessionId",sessionId);
                new HttpUtil(context).result(new HttpUtil.HttpListener() {
                    @Override
                    public void success(String data) {
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

                    @Override
                    public void notNetwork(View data) {

                    }
                }).postHead("/movieApi/movie/v1/verify/movieCommentGreat",map,mapHead,"",true,false);
            }else{
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHodlerDetailsCritics extends XRecyclerView.ViewHolder{

        private SimpleDraweeView img;
        private TextView name,comments,time,comNum,likeNum;
        private ImageView like,commentsImg;
        public MyViewHodlerDetailsCritics(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.details_critics_img);
            name = itemView.findViewById(R.id.details_critics_name);
            comments = itemView.findViewById(R.id.details_critics_comments);
            time = itemView.findViewById(R.id.details_critics_time);
            comNum = itemView.findViewById(R.id.details_critics_commentsnum);
            likeNum = itemView.findViewById(R.id.details_critics_likenum);
            like = itemView.findViewById(R.id.details_critics_like);
            commentsImg = itemView.findViewById(R.id.details_critics_commentsimg);
        }
    }
    private CriticsFouceListener listener;
    public void result(CriticsFouceListener listener){
        this.listener=listener;
    }
    public interface CriticsFouceListener{
        void criticsChange();
        void criticsComments(int isgreat,int greatnum,int commentId);
    }
}
