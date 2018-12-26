package com.bw.movie.presenter_activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.DetailsFilmActivity;
import com.bw.movie.activity.DialogUtils;
import com.bw.movie.activity.FindCinemaActivity;
import com.bw.movie.adapter.MyAdapterDetailsCritics;
import com.bw.movie.adapter.MyAdapterDetailsCriticsComment;
import com.bw.movie.adapter.MyAdapterDetailsDe;
import com.bw.movie.adapter.MyAdapterDetailsStill;
import com.bw.movie.adapter.MyAdapterDetailsTrailer;
import com.bw.movie.bean.DetailsComment;
import com.bw.movie.bean.Critics;
import com.bw.movie.bean.Details;
import com.bw.movie.bean.Focus;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 * 影院详情
 * 2018年11月29日 10:18:30
 * 赵瑜峰
 * 创建了基本的这个presenter
 * */
public class DetailsFilmActivityPresenter extends AppDelage {

    private TextView mTitle,mBuy;
    private SimpleDraweeView mImg;
    private String id;
    private Details details;
    private ImageView mFocus;
    private boolean followMovie;
    private RelativeLayout mRelativeLayout;
    private MyAdapterDetailsCritics myAdapterDetailsCritics;
    private EditText text;
    private SimpleDraweeView img_bg;
    private MyAdapterDetailsCriticsComment myAdapterDetailsCriticsComment;
    private XRecyclerView recyclerView;
    private EditText text1;
    private PopupWindow popupWindow;
    private RelativeLayout relativeLayout;
    private View view1;
    private int count=5;
    private int count1=5;
    private XRecyclerView recyclerView1;
    @Override
    public int getLayoutId() {
        return R.layout.activity_detailsfilm;
    }
    private Context context;
    @Override
    public void getContext(Context context) {
        this.context=context;
    }

    @Override
    public void initData() {
        super.initData();
        //intent接收传过来的值
        Intent intent = ((DetailsFilmActivity) context).getIntent();
        id = intent.getStringExtra("id");
        mRelativeLayout = get(R.id.details_film_hide);
        img_bg = get(R.id.details_film_bg);
        mTitle = get(R.id.details_film_title);
        mImg = get(R.id.details_film_img1);
        relativeLayout = get(R.id.details_film_topBg);
        mBuy = get(R.id.details_film_buy);
        mFocus = get(R.id.details_film_focus);
        //点击事件
        Onclick();
        //影片详情
        doHttp();
    }

    @Override
    public void successnetwork() {
        super.successnetwork();
        doHttp();
    }

    private void Onclick() {
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SharedPreferencesUtils.getBoolean(context,"isLogin")){
                   //点赞
                    focus();
                }else{
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }

            }
        },R.id.details_film_focus);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                details();
            }
        },R.id.details_film_details);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //预告
                trailer();
            }
        },R.id.details_film_trailer);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //剧照
                still();
            }
        },R.id.details_film_still);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //影片
                critics(1000);
            }
        },R.id.details_film_critics);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //退出
                ((DetailsFilmActivity)context).finish();
            }
        },R.id.details_film_return);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //购买
                Intent intent = new Intent(context, FindCinemaActivity.class);
                intent.putExtra("movie_name",details.getResult().getName());
                intent.putExtra("movieId",details.getResult().getId());
                intent.putExtra("Director",details.getResult().getDirector());
                intent.putExtra("type",details.getResult().getMovieTypes());
                intent.putExtra("time",details.getResult().getDuration());
                intent.putExtra("country",details.getResult().getPlaceOrigin());
                intent.putExtra("logo",details.getResult().getImageUrl());
                context.startActivity(intent);
            }
        },R.id.details_film_buy);
    }
    //点赞
    private void focus() {
        //判断是否登录
        if( SharedPreferencesUtils.getBoolean(context, "isLogin")){
            //获取userId和sessionId
            int userId = SharedPreferencesUtils.getInt(context, "userId");
            String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
            //判断是否关注
            if(followMovie){
                //去关注
                doHpptFocus (userId,sessionId);
            }else{
                //取消关注
                doHttpCancel(userId,sessionId);
            }

        }
    }

    private void details() {
        mRelativeLayout.removeAllViews();
        //穿件一个view并传给alert
        View view = View.inflate(context,R.layout.details_film,null);
        mRelativeLayout.addView(view);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mRelativeLayout, "translationY", mRelativeLayout.getMeasuredHeight(), 0);
        translationY.setDuration(1000);
        translationY.start();
        view.findViewById(R.id.details_details_under).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator translationY = ObjectAnimator.ofFloat(mRelativeLayout, "translationY",0, mRelativeLayout.getMeasuredHeight() );
                translationY.setDuration(1000);
                translationY.start();
            }
        });
        //寻找控件
        SimpleDraweeView img = view.findViewById(R.id.details_details_img);
        TextView type = view.findViewById(R.id.details_details_type);
        TextView director = view.findViewById(R.id.details_details_director);
        TextView length = view.findViewById(R.id.details_details_length);
        TextView origin = view.findViewById(R.id.details_details_origin);
        TextView Introduction = view.findViewById(R.id.details_details_Introduction);
        RecyclerView recyclerView = view.findViewById(R.id.details_details_recyler);
//        设置属性
        img.setImageURI(details.getResult().getImageUrl());
        type.setText(details.getResult().getMovieTypes());
        director.setText(details.getResult().getDirector());
        length.setText(details.getResult().getDuration());
        origin.setText(details.getResult().getPlaceOrigin());
        Introduction.setText(details.getResult().getSummary());
        String starring = details.getResult().getStarring();
        String[] split = starring.split(",");
        MyAdapterDetailsDe myAdapterDetailsDe = new MyAdapterDetailsDe(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapterDetailsDe);
        myAdapterDetailsDe.setList(split);

    }
    //预告片
    private void trailer() {
        mRelativeLayout.removeAllViews();
        View view = View.inflate(context,R.layout.details_trailer,null);
        mRelativeLayout.addView(view);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mRelativeLayout, "translationY", mRelativeLayout.getMeasuredHeight(), 0);
        translationY.setDuration(1000);
        translationY.start();
        view.findViewById(R.id.details_trailer_under).setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View view) {
                ObjectAnimator translationY = ObjectAnimator.ofFloat(mRelativeLayout, "translationY",0, mRelativeLayout.getMeasuredHeight() );
                translationY.setDuration(1000);
                translationY.start();
             }
        });
        RecyclerView recyclerView = view.findViewById(R.id.details_trailer_recyler);
        MyAdapterDetailsTrailer myAdapterDetailsTrailer = new MyAdapterDetailsTrailer(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapterDetailsTrailer);
        List<Details.ResultBean.ShortFilmListBean> shortFilmList = details.getResult().getShortFilmList();
        myAdapterDetailsTrailer.setList(shortFilmList);
    }
    //剧照
    private void still() {
        mRelativeLayout.removeAllViews();
        View view = View.inflate(context,R.layout.details_still,null);
        mRelativeLayout.addView(view);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mRelativeLayout, "translationY", mRelativeLayout.getMeasuredHeight(), 0);
        translationY.setDuration(1000);
        translationY.start();
        view.findViewById(R.id.details_still_under).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator translationY = ObjectAnimator.ofFloat(mRelativeLayout, "translationY",0, mRelativeLayout.getMeasuredHeight() );
                translationY.setDuration(1000);
                translationY.start();
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.details_still_recyler);
        MyAdapterDetailsStill myAdapterDetailsStill = new MyAdapterDetailsStill(context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        recyclerView.setAdapter(myAdapterDetailsStill);
        List<String> posterList = details.getResult().getPosterList();
        myAdapterDetailsStill.setList(posterList);
    }
    //影评
    private void critics(int time) {
        mRelativeLayout.removeAllViews();
        view1 = View.inflate(context, R.layout.details_critics,null);
        mRelativeLayout.addView(view1);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mRelativeLayout, "translationY", mRelativeLayout.getMeasuredHeight(), 0);
        translationY.setDuration(time);
        translationY.start();
        view1.findViewById(R.id.details_critics_relative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator translationY = ObjectAnimator.ofFloat(mRelativeLayout, "translationY",0, mRelativeLayout.getMeasuredHeight() );
                translationY.setDuration(1000);
                translationY.start();
            }
        });
//        dialogUtils.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        text = view1.findViewById(R.id.details_critics_text);
        view1.findViewById(R.id.details_critics_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击发送 提交评论
                doHttpcomments();
            }
        });
        recyclerView = view1.findViewById(R.id.details_critics_recyler);
        myAdapterDetailsCritics = new MyAdapterDetailsCritics(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapterDetailsCritics);
        doHttpCritics();
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                doHttpCritics();

            }

            @Override
            public void onLoadMore() {
                count+=5;
                doHttpCritics();
            }
        });

        myAdapterDetailsCritics.result(new MyAdapterDetailsCritics.CriticsFouceListener() {



            @Override
            public void criticsChange() {
                //点击查看评论显示评论列表
                doHttpCritics();
            }

            @Override
            public void criticsComments(int isgreat, int greatnum,int CommentId) {
                mRelativeLayout.removeAllViews();
                View view = View.inflate(context,R.layout.activity_details_comment,null);
                mRelativeLayout.addView(view);
                view.findViewById(R.id.details_comment_under).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ObjectAnimator translationY = ObjectAnimator.ofFloat(mRelativeLayout, "translationY",0, mRelativeLayout.getMeasuredHeight() );
                        translationY.setDuration(1000);
                        translationY.start();
                    }
                });
//                dialogUtils.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                text1 = view.findViewById(R.id.details_comment_text);
                view.findViewById(R.id.details_comment_send).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        doHttpcomment(isgreat,greatnum,CommentId);
                    }
                });
                view.findViewById(R.id.details_comment_return).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        critics(0);
                    }
                });
                recyclerView1 = view.findViewById(R.id.details_comment_recyler);
                myAdapterDetailsCriticsComment = new MyAdapterDetailsCriticsComment(context);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
                linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView1.setLayoutManager(linearLayoutManager1);
                recyclerView1.setAdapter(myAdapterDetailsCriticsComment);
                doHttpCriticsComments(isgreat,greatnum,CommentId);
                recyclerView1.setLoadingListener(new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {
                        doHttpCriticsComments(isgreat,greatnum,CommentId);
                    }

                    @Override
                    public void onLoadMore() {
                        count1+=5;
                        doHttpCriticsComments(isgreat,greatnum,CommentId);
                    }
                });
            }


        });

    }
    //添加用户对评论的回复
    private void doHttpcomment(int isgreat, int greatnum, int commentId) {
        if(id!=null){
            if(SharedPreferencesUtils.getBoolean(context,"isLogin")){
                String trim = text1.getText().toString().trim();
                if(TextUtils.isEmpty(trim)){
                    Toast.makeText(context, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                int userId = SharedPreferencesUtils.getInt(context, "userId");
                String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                Map<String,String> map = new HashMap<>();
                map.put("commentId",commentId+"");
                map.put("replyContent",trim);
                Map<String,String> mapHead = new HashMap<>();
                mapHead.put("userId",userId+"");
                mapHead.put("sessionId",sessionId);
                mapHead.put("Content-Type","application/x-www-form-urlencoded");
                new HttpUtil(context).result(new HttpUtil.HttpListener() {
                    @Override
                    public void success(String data) {
                        Gson gson = new Gson();
                        Focus focus = gson.fromJson(data, Focus.class);
                        if("0000".equals(focus.getStatus())){
                            Toast.makeText(context, "评论成功", Toast.LENGTH_SHORT).show();
                            doHttpCriticsComments(isgreat,greatnum,commentId);
                            text1.setText("");
                        }else{
                            Toast.makeText(context, "评论失败", Toast.LENGTH_SHORT).show();
                        }
                        text.setText("");
                    }

                    @Override
                    public void fail(String data) {

                    }

                    @Override
                    public void notNetwork(View data) {

                    }
                }).postHead("/movieApi/movie/v1/verify/commentReply",map,mapHead,"DetailsFilmFocus",true,true);
            }else{
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
            }

        }
    }
        //查看影评评论回复
    private void doHttpCriticsComments( int isgreat, int greatnum,int CommentId) {
        if(id!=null){
            if(SharedPreferencesUtils.getBoolean(context,"isLogin")){
                int userId = SharedPreferencesUtils.getInt(context, "userId");
                String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                Map<String,String> map = new HashMap<>();
                map.put("commentId",CommentId+"");
                map.put("page","1");
                map.put("count",count1+"");
                Map<String,String> mapHead = new HashMap<>();
                mapHead.put("userId",userId+"");
                mapHead.put("sessionId",sessionId);
                new HttpUtil(context).result(new HttpUtil.HttpListener() {
                    @Override
                    public void success(String data) {
                        Gson gson = new Gson();
                        DetailsComment detailsComment = gson.fromJson(data, DetailsComment.class);
                        List<DetailsComment.ResultBean> result = detailsComment.getResult();
                        if("0000".equals(detailsComment.getStatus())){
                            if(!detailsComment.getMessage().equals("无数据")){
                                myAdapterDetailsCriticsComment.setList(result,isgreat,greatnum,CommentId);
                                recyclerView1.refreshComplete();
                                recyclerView1.loadMoreComplete();
                            }

                        }

                    }

                    @Override
                    public void fail(String data) {

                    }

                    @Override
                    public void notNetwork(View data) {

                    }
                }).get("/movieApi/movie/v1/findCommentReply",map,mapHead,"DetailsFilmDetailsComment",true,true);
            }else{
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
            }

        }
    }
    //添加用户对影片的评论
    private void doHttpcomments() {
        if(id!=null){
            if(SharedPreferencesUtils.getBoolean(context,"isLogin")){
                String trim = text.getText().toString().trim();
                Toast.makeText(context, trim+"", Toast.LENGTH_SHORT).show();
                if(TextUtils.isEmpty(trim)){
                    Toast.makeText(context, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                int userId = SharedPreferencesUtils.getInt(context, "userId");
                String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                Map<String,String> map = new HashMap<>();
                map.put("movieId",id);
                map.put("commentContent",trim);
                Map<String,String> mapHead = new HashMap<>();
                mapHead.put("userId",userId+"");
                mapHead.put("sessionId",sessionId);
                mapHead.put("Content-Type","application/x-www-form-urlencoded");
                new HttpUtil(context).result(new HttpUtil.HttpListener() {
                    @Override
                    public void success(String data) {
                        Gson gson = new Gson();
                        Focus focus = gson.fromJson(data, Focus.class);
                        if("0000".equals(focus.getStatus())){
                            Toast.makeText(context, "评论成功", Toast.LENGTH_SHORT).show();
                            doHttpCritics();
                            doHttp();
                        }else{
                            Toast.makeText(context, "评论失败", Toast.LENGTH_SHORT).show();
                        }
                        text.setText("");
                    }

                    @Override
                    public void fail(String data) {

                    }

                    @Override
                    public void notNetwork(View data) {

                    }
                }).postHead("/movieApi/movie/v1/verify/movieComment",map,mapHead,"DetailsFilmFocusmy",true,true);
            }else{
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
            }

        }
    }
    //评论列表
    private void doHttpCritics() {
        if(id!=null){
            if(SharedPreferencesUtils.getBoolean(context,"isLogin")){
                int userId = SharedPreferencesUtils.getInt(context, "userId");
                String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                Map<String,String> map = new HashMap<>();
                map.put("movieId",id);
                map.put("page","1");
                map.put("count",count+"");
                Map<String,String> mapHead = new HashMap<>();
                mapHead.put("userId",userId+"");
                mapHead.put("sessionId",sessionId);
                new HttpUtil(context).result(new HttpUtil.HttpListener() {
                    @Override
                    public void success(String data) {
                        Gson gson = new Gson();
                        Critics critics = gson.fromJson(data, Critics.class);
                        List<Critics.ResultBean> result = critics.getResult();
                        myAdapterDetailsCritics.setList(result);
                        recyclerView.refreshComplete();
                        recyclerView.loadMoreComplete();
                    }

                    @Override
                    public void fail(String data) {

                    }

                    @Override
                    public void notNetwork(View data) {

                    }
                }).get("/movieApi/movie/v1/findAllMovieComment",map,mapHead,"DetailsFilmCritics",true,true);
            }else{
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                Map<String,String> map = new HashMap<>();
                map.put("movieId",id);
                map.put("page","1");
                map.put("count","5");
                new HttpUtil(context).result(new HttpUtil.HttpListener() {
                    @Override
                    public void success(String data) {
                        Gson gson = new Gson();
                        Critics critics = gson.fromJson(data, Critics.class);
                        List<Critics.ResultBean> result = critics.getResult();
                        myAdapterDetailsCritics.setList(result);
                    }

                    @Override
                    public void fail(String data) {

                    }

                    @Override
                    public void notNetwork(View data) {

                    }
                }).get("/movieApi/movie/v1/findAllMovieComment",map,null,"DetailsFilmCritics",true,true);
            }

        }
    }
    //影片详情
    private void doHttp() {
        if(id!=null){
            if(SharedPreferencesUtils.getBoolean(context,"isLogin")){
                int userId = SharedPreferencesUtils.getInt(context, "userId");
                String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                Map<String,String> map = new HashMap<>();
                map.put("movieId",id);
                Map<String,String> mapHead = new HashMap<>();
                mapHead.put("userId",userId+"");
                mapHead.put("sessionId",sessionId);
                new HttpUtil(context).result(new HttpUtil.HttpListener() {
                    @Override
                    public void success(String data) {
                        Gson gson = new Gson();
                        details = gson.fromJson(data, Details.class);
                        mTitle.setText(details.getResult().getName());
                        mImg.setImageURI(details.getResult().getImageUrl());
                        img_bg.setImageURI(details.getResult().getImageUrl());
                        followMovie = details.getResult().isFollowMovie();
                        if(followMovie){
                            mFocus.setImageResource(R.drawable.com_icon_collection_default);
                        }else{
                            mFocus.setImageResource(R.drawable.com_icon_collection_selected);
                        }
                    }

                    @Override
                    public void fail(String data) {

                    }

                    @Override
                    public void notNetwork(View data) {

                    }
                }).get("/movieApi/movie/v1/findMoviesDetail",map,mapHead,"DetailsFilmDetails",true,true);
            }else{
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                Map<String,String> map = new HashMap<>();
                map.put("movieId",id);
                new HttpUtil(context).result(new HttpUtil.HttpListener() {
                    @Override
                    public void success(String data) {
                        Gson gson = new Gson();
                        details = gson.fromJson(data, Details.class);
                        mTitle.setText(details.getResult().getName());
                        mImg.setImageURI(details.getResult().getImageUrl());
                        img_bg.setImageURI(details.getResult().getImageUrl());
                        followMovie = details.getResult().isFollowMovie();
                        if(followMovie){
                            mFocus.setImageResource(R.drawable.com_icon_collection_default);
                        }else{
                            mFocus.setImageResource(R.drawable.com_icon_collection_selected);
                        }
                    }

                    @Override
                    public void fail(String data) {

                    }

                    @Override
                    public void notNetwork(View data) {

                    }
                }).get("/movieApi/movie/v1/findMoviesDetail",map,null,"DetailsFilmDetails",true,true);
            }

        }
    }
    //取消关注
    private void doHttpCancel( int userId, String sessionId) {
        Map<String,String> map = new HashMap<>();
        map.put("movieId",details.getResult().getId()+"");
        Map<String,String> mapHead = new HashMap<>();
        mapHead.put("userId",userId+"");
        mapHead.put("sessionId",sessionId);
        new HttpUtil(context).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {

                Gson gson = new Gson();
                Focus focus = gson.fromJson(data, Focus.class);
                if("0000".equals(focus.getStatus())){
                    Toast.makeText(context, "取消关注成功", Toast.LENGTH_SHORT).show();
                    doHttp();
                }else{
                    Toast.makeText(context, "取消关注失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void fail(String data) {

            }

            @Override
            public void notNetwork(View data) {

            }
        }).get("/movieApi/movie/v1/verify/cancelFollowMovie",map,mapHead,"",true,false);
    }
    //关注
    private void doHpptFocus( int userId, String sessionId) {
        Map<String,String> map = new HashMap<>();
        map.put("movieId",details.getResult().getId()+"");
        Map<String,String> mapHead = new HashMap<>();
        mapHead.put("userId",userId+"");
        mapHead.put("sessionId",sessionId);
        new HttpUtil(context).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                Focus focus = gson.fromJson(data, Focus.class);
                if("0000".equals(focus.getStatus())){
                    Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
                    doHttp();
                }else{
                    Toast.makeText(context, "关注失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void fail(String data) {

            }

            @Override
            public void notNetwork(View data) {

            }
        }).get( "/movieApi/movie/v1/verify/followMovie",map,mapHead,"",true,false);
    }
}
