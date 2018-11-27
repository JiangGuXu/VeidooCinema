package com.bw.movie.mvp.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * 赵瑜峰
 * 2018年11月27日 11:58:01
 */
public interface IDelage {
    //初始化方法
    void initData();
    //初始化布局
    void create(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle);
    //获取上下文
    void getContext(Context context);
    //获取view
    View view();
}
