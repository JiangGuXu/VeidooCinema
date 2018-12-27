package com.bw.movie.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.activity.SearchActivity;
import com.bw.movie.bean.FilmListData;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class MyAdapterFilm extends XRecyclerView.Adapter<MyAdapterFilm.MyViewHodlerFilm> {
    private Context context;
    private MyAdapterFilmBanner myAdapterFilmBanner;
    private int WinthLeft=0;
    private int WinthRight=0;
    private MyAdapterFilmList myAdapterFilmList;
    private List<String> titles =new ArrayList<>();
    private List<FilmListData> list =new ArrayList<>();
    private List<String> urls =new ArrayList<>();
    private List<FilmListData.ResultBean> result=new ArrayList<>();

    public MyAdapterFilm(Context context) {
        this.context=context;
    }
    public void setList(List<String> titles, List<String> urls, List<FilmListData.ResultBean> result){
        this.titles=titles;
        this.urls=urls;
        this.result=result;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHodlerFilm onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.xrecyler_film,null);
        MyViewHodlerFilm myViewHodlerFilm = new MyViewHodlerFilm(view);
        return myViewHodlerFilm;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodlerFilm myViewHodlerFilm, int i) {
        myViewHodlerFilm.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).startActivity(new Intent(context, SearchActivity.class));
            }
        });
        myAdapterFilmBanner = new MyAdapterFilmBanner(context);
        myViewHodlerFilm.mRecyclerCoverFlow.setAdapter(myAdapterFilmBanner);
        myAdapterFilmBanner.setList(result);
        myViewHodlerFilm.mRecyclerCoverFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                WinthRight=position*(myViewHodlerFilm.view.getMeasuredWidth()/result.size());
                ObjectAnimator translationX = ObjectAnimator.ofFloat(myViewHodlerFilm.view1, "translationX", WinthLeft,WinthRight);
                translationX.setDuration(50);
                translationX.start();
                WinthLeft=WinthRight;
            }
        });
        myAdapterFilmBanner.setListener(new MyAdapterFilmBanner.RecyclerItemListener() {
            @Override
            public void onClick(int position)  {
                myViewHodlerFilm.mRecyclerCoverFlow.scrollToPosition(position);
            }

            @Override
            public void getmovieId(int movieId) {

            }
        });
        //请求轮播数据
        //电影展示
        myAdapterFilmList = new MyAdapterFilmList(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myViewHodlerFilm.mListView.setLayoutManager(linearLayoutManager);
        myViewHodlerFilm.mListView.setAdapter(myAdapterFilmList);
        myViewHodlerFilm.mRecyclerCoverFlow.scrollToPosition(result.size()/2);
        //请求影片数据
        list.clear();
        for (int b = 0; b <urls.size(); b++) {
            doHttp(urls.get(b),b);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHodlerFilm extends XRecyclerView.ViewHolder{

        private View view,view1;
        private RelativeLayout mRelativeLayout,relativeLayout;
        private RecyclerCoverFlow mRecyclerCoverFlow;
        private RecyclerView mListView;

        public MyViewHodlerFilm(@NonNull View itemView) {
            super(itemView);
            mRelativeLayout = itemView.findViewById(R.id.film_search_relative);
            view = itemView.findViewById(R.id.film_divider_view);
            view1 = itemView.findViewById(R.id.film_divider_view1);
            relativeLayout = itemView.findViewById(R.id.film_divider_relative);
            mRecyclerCoverFlow = (RecyclerCoverFlow) itemView.findViewById(R.id.film_list_recyler);
            mListView =(RecyclerView)  itemView.findViewById(R.id.film_list_view);
        }
    }


    //请求数据并放入list集合中
    private void doHttp(String url, int b) {
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("count","10");
        Map<String,String> mapHead = new HashMap<>();
        if(SharedPreferencesUtils.getBoolean(context,"isLogin")){
            int userId = SharedPreferencesUtils.getInt(context, "userId");
            String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
            mapHead.put("userId",userId+"");
            mapHead.put("sessionId",sessionId);
        }
        new HttpUtil(context).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                FilmListData filmListData = gson.fromJson(data, FilmListData.class);
                list.add(filmListData);
                if(urls.size()-1==b){
                    myAdapterFilmList.setList(list,titles);
                }

            }

            @Override
            public void fail(String data) {

            }

            @Override
            public void notNetwork(View data) {

            }
        }).get(url,map,mapHead,"MyFilmFilmListData"+b,true,true);
    }

}
