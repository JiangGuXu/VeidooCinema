package com.bw.movie.presenter_activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.SelectedSetActivity;
import com.bw.movie.bean.Detailsbean;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.widget.SeatTable;

import java.io.Serializable;

/**
 *
 * 选座页面
 * 姜谷蓄
 */
public class SelectedSetActivityPresenter extends AppDelage{
    private SeatTable seatTableView;
    private TextView play_time;
    private TextView activity_name;
    private TextView activity_detailsname;
    private TextView movie_text;
    @Override
    public int getLayoutId() {
        return R.layout.activity_selectedset;
    }
    private Context context;
    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();
        seatTableView = get(R.id.seatView);
        play_time = get(R.id.play_time);
        activity_name = get(R.id.activity_name);
        activity_detailsname = get(R.id.activity_detailsname);
        movie_text = get(R.id.movie_name);
        //接收传值
        Detailsbean.Resultbean result = (Detailsbean.Resultbean) ((SelectedSetActivity)context).getIntent().getSerializableExtra("result");
        String name = ((SelectedSetActivity) context).getIntent().getStringExtra("name");
        String address = ((SelectedSetActivity) context).getIntent().getStringExtra("address");
        String movie_name = ((SelectedSetActivity) context).getIntent().getStringExtra("movie_name");
        //赋值
        play_time.setText(result.getBeginTime()+"-"+result.getEndTime());
        activity_name.setText(name);//影院的名字
        activity_detailsname.setText(address);//影院的地址
        seatTableView.setScreenName(result.getScreeningHall()+"荧幕");//设置屏幕名称
        movie_text.setText(movie_name);
        seatTableView.setMaxSelected(3);//设置最多选中
        //选座控件的点击监听
        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {
            @Override
            public boolean isValidSeat(int row, int column) {
                if(column==2) {
                    return false;
                }
                return true;
            }
            @Override
            public boolean isSold(int row, int column) {
                if(row==6&&column==6){
                    return true;
                }
                return false;
            }
            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }
            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10,15);
    }
}
