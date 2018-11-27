package com.bw.movie.presenter_activity;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.fragment.CinemaFragment;
import com.bw.movie.fragment.FilmFragment;
import com.bw.movie.fragment.MyFragment;
import com.bw.movie.mvp.view.AppDelage;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部Tab导航键
 * 姜谷蓄
 * 2018-11-27
 */

public class MainActivityPresenter extends AppDelage implements View.OnClickListener {
    private FrameLayout main_frame;
    private ImageView main_img_01, main_img_02, main_img_03;
    private FilmFragment filmFragment;
    private MyFragment myFragment;
    private CinemaFragment cinemaFragment;
    private List<ImageView> list = new ArrayList<>();
    private FragmentManager manager;
    private int width;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private Context context;

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();
        MainActivity activity = (MainActivity) this.context;
        main_frame = get(R.id.main_frame);
        main_img_01 = get(R.id.main_img_01);
        main_img_02 = get(R.id.main_img_02);
        main_img_03 = get(R.id.main_img_03);
        filmFragment = new FilmFragment();
        myFragment = new MyFragment();
        cinemaFragment = new CinemaFragment();
        addList();
        Display display = activity.getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        for (int i = 0; i < list.size(); i++) {
            setSizeMin(list.get(i));
        }
        setSizeMax(main_img_01);
        manager = activity.getSupportFragmentManager();
        manager.beginTransaction().add(R.id.main_frame, myFragment).commit();
        manager.beginTransaction().add(R.id.main_frame, cinemaFragment).commit();
        manager.beginTransaction().add(R.id.main_frame, filmFragment).commit();
        main_img_01.setOnClickListener(this);
        main_img_02.setOnClickListener(this);
        main_img_03.setOnClickListener(this);
    }

    private void addList() {
        list.add(main_img_01);
        list.add(main_img_02);
        list.add(main_img_03);
    }
    //设置图片变大时的宽高
    public void setSizeMax(ImageView imageView) {
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.height = 70 * (width / 2) / 160;
        params.width = 70 * (width / 2) / 160;
        imageView.setLayoutParams(params);
    }
    //设置图片变小时的宽高
    public void setSizeMin(ImageView imageView) {
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        //1dp*像素密度/160 = 实际像素数   dp转换px公式
        params.height = 55 * (width / 2) / 160;
        params.width = 55 * (width / 2) / 160;
        imageView.setLayoutParams(params);
    }
    //点击切换
    @Override
    public void onClick(View v) {
        for (int i = 0; i < list.size(); i++) {
            setSizeMin(list.get(i));
        }
        switch (v.getId()) {
            case R.id.main_img_01:
                setSizeMax(main_img_01);
                main_img_01.setImageResource(R.mipmap.com_icon_film_selected);
                main_img_02.setImageResource(R.mipmap.com_icon_cinema_default);
                main_img_03.setImageResource(R.mipmap.com_icon_my_default);
                manager.beginTransaction().show(filmFragment).commit();
                manager.beginTransaction().hide(myFragment).commit();
                manager.beginTransaction().hide(cinemaFragment).commit();
                break;
            case R.id.main_img_02:
                setSizeMax(main_img_02);
                main_img_01.setImageResource(R.mipmap.com_icon_film_fault);
                main_img_02.setImageResource(R.mipmap.com_icon_cinema_selected);
                main_img_03.setImageResource(R.mipmap.com_icon_my_default);
                manager.beginTransaction().show(cinemaFragment).commit();
                manager.beginTransaction().hide(myFragment).commit();
                manager.beginTransaction().hide(filmFragment).commit();
                break;
            case R.id.main_img_03:
                setSizeMax(main_img_03);
                main_img_01.setImageResource(R.mipmap.com_icon_film_fault);
                main_img_02.setImageResource(R.mipmap.com_icon_cinema_default);
                main_img_03.setImageResource(R.mipmap.com_icon_my_selected);
                manager.beginTransaction().show(myFragment).commit();
                manager.beginTransaction().hide(cinemaFragment).commit();
                manager.beginTransaction().hide(filmFragment).commit();
                break;
        }
    }
}

