package com.bw.movie.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.UserAttentionCinemaAdapterBean;
import com.bw.movie.utils.DateFormat.DateFormatForYou;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 2018年11月30日 11:52:39
 * 焦浩康
 * 我的关注的影院
 */
public class UserAttentionCinemaAdapter extends RecyclerView.Adapter<UserAttentionCinemaAdapter.MyViewHolder> {

    private List<UserAttentionCinemaAdapterBean.ResultBean> list = new ArrayList<>();
    private Context context;


    public UserAttentionCinemaAdapter(Context context) {
        this.context = context;
    }


    public void setList(List<UserAttentionCinemaAdapterBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.user_attention_cinema_item_layout, null);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.my_attention_cinema_sdv.setImageURI(list.get(i).getLogo());
        myViewHolder.my_attention_cinema_text1.setText(list.get(i).getName());
        myViewHolder.my_attention_cinema_text2.setText(list.get(i).getAddress());


        myViewHolder.my_attention_cinema_item_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int id = list.get(i).getId();
                final int userId = SharedPreferencesUtils.getInt(context, "userId");
                final String sessionId = SharedPreferencesUtils.getString(context, "sessionId");

                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("取消关注");
                alertDialog.setMessage("是否取消关注?");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<String, String> headMap = new HashMap<>();
                        HashMap<String, String> parameterMap = new HashMap<>();

                        parameterMap.put("cinemaId", id + "");
                        headMap.put("userId", userId + "");
                        headMap.put("sessionId", sessionId);
                        new HttpUtil().get("/movieApi/cinema/v1/verify/cancelFollowCinema", parameterMap, headMap).result(new HttpUtil.HttpListener() {
                            @Override
                            public void success(String data) {
                                if (data.contains("取消关注成功")) {
                                    list.remove(i);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "取消关注成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "取消失败", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void fail(String data) {
                                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView my_attention_cinema_sdv;
        private TextView my_attention_cinema_text1;
        private TextView my_attention_cinema_text2;
        private RelativeLayout my_attention_cinema_item_layout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            my_attention_cinema_sdv = itemView.findViewById(R.id.my_attention_cinema_sdv);
            my_attention_cinema_item_layout = itemView.findViewById(R.id.my_attention_cinema_item_layout);
            my_attention_cinema_text1 = itemView.findViewById(R.id.my_attention_cinema_text1);
            my_attention_cinema_text2 = itemView.findViewById(R.id.my_attention_cinema_text2);

        }
    }
}
