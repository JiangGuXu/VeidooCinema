package com.bw.movie.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.UserSystemMessagesBean;
import com.bw.movie.utils.DateFormat.DateFormatForYou;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserSystemMessageAdapter extends RecyclerView.Adapter<UserSystemMessageAdapter.MyViewHolder> {

    private Context context;
    private String s;
    private List<UserSystemMessagesBean.ResultBean> list = new ArrayList<>();

    public UserSystemMessageAdapter(Context context) {
        this.context = context;
    }


    public void setList(List<UserSystemMessagesBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.user_system_messages_item_layout, null);
        return new MyViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myViewHolder.user_system_messages_item_title.setText(list.get(i).getTitle());
        myViewHolder.user_system_messages_item_content.setText(list.get(i).getContent());
        DateFormatForYou dateFormatForYou = new DateFormatForYou();
        try {
            s = dateFormatForYou.longToString(list.get(i).getPushTime(), "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myViewHolder.user_system_messages_item_date.setText(s);
        if (list.get(i).getStatus() == 0) {
            myViewHolder.user_system_messages_item_relativelayout.setVisibility(View.VISIBLE);
            myViewHolder.user_system_messages_item_message_count.setText("1");
        } else {
            myViewHolder.user_system_messages_item_relativelayout.setVisibility(View.GONE);
        }

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        myViewHolder.user_system_messages_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.setMessage(list.get(i).getContent());
                alertDialog.setTitle(list.get(i).getTitle());
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                if (list.get(i).getStatus() == 0) {
                    Log.i("test1", "onClick: ");
                    doHttpTochangeSysMsgStatus("/movieApi/tool/v1/verify/changeSysMsgStatus");
                }

            }

            private void doHttpTochangeSysMsgStatus(String s) {
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put("id", list.get(i).getId() + "");
                HashMap<String, String> headMap = new HashMap<>();
                String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                int userId = SharedPreferencesUtils.getInt(context, "userId");
                headMap.put("userId", userId + "");
                headMap.put("sessionId", sessionId);

                list.get(i).setStatus(1);
                new HttpUtil().get(s, paramsMap, headMap).result(new HttpUtil.HttpListener() {


                    @Override
                    public void success(String data) {
                        if (data.contains("成功")) {
                            myViewHolder.user_system_messages_item_relativelayout.setVisibility(View.GONE);
                            listener.changed(1);

                        } else {
                            Toast.makeText(context, "网络出了点问题~重新登录或者检查下网络~", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void fail(String data) {
                        Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                    }
                });

                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView user_system_messages_item_title;
        private TextView user_system_messages_item_content;
        private TextView user_system_messages_item_date;
        private TextView user_system_messages_item_message_count;
        private RelativeLayout user_system_messages_item_relativelayout;
        private RelativeLayout user_system_messages_item_layout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_system_messages_item_title = itemView.findViewById(R.id.user_system_messages_item_title);

            user_system_messages_item_content = itemView.findViewById(R.id.user_system_messages_item_content);
            user_system_messages_item_date = itemView.findViewById(R.id.user_system_messages_item_date);
            user_system_messages_item_message_count = itemView.findViewById(R.id.user_system_messages_item_message_count);
            user_system_messages_item_relativelayout = itemView.findViewById(R.id.user_system_messages_item_relativelayout);
            user_system_messages_item_layout = itemView.findViewById(R.id.user_system_messages_item_layout);

        }
    }

    private messageAapterStatusChangedListener listener;

    public void setListener(messageAapterStatusChangedListener listener) {
        this.listener = listener;
    }

    public interface messageAapterStatusChangedListener {
        void changed(int count);
    }

}
