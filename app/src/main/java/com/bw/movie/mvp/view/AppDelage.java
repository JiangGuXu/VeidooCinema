package com.bw.movie.mvp.view;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * 赵瑜峰
 * 2018年11月27日 11:58:01
 */
public abstract class AppDelage implements IDelage {

    private View rootView;

    @Override
    public void initData() {

    }
    //寻找控件
    private SparseArray<View> views = new SparseArray<>();
    public <T extends View> T get(int id){
        T view = (T) views.get(id);
        if(view==null){
            view = rootView.findViewById(id);
            views.put(id,view);
        }
        return  view;
    }
    //点击事件
    public void setOnclick(View.OnClickListener listener,int...ids){
        if(ids==null){
            return;
        }
        for(int id:ids){
            get(id).setOnClickListener(listener);
        }
    };
    //创建视图
    @Override
    public void create(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        rootView = inflater.inflate(getLayoutId(), viewGroup, false);
    }

    @Override
    public View view() {
        return rootView;
    }

    public abstract int getLayoutId();

    public void destry(){
        rootView=null;
    }

    public  void resume(){

    }
    public  void restart(){

    }

}
