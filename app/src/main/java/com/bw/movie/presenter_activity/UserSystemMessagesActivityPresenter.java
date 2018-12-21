package com.bw.movie.presenter_activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.UserSystemMessagesActivity;
import com.bw.movie.adapter.UserSystemMessageAdapter;
import com.bw.movie.bean.UserSystemCountBean;
import com.bw.movie.bean.UserSystemMessagesBean;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;


/**
 * 2018年12月3日 21:11:00
 * 焦浩康
 * 系统消息activitypresenter
 * <p>
 * 2018年12月9日 19:46:27
 * 焦浩康
 * 增加了上拉和下拉的功能
 * xrecyclerview
 */
public class UserSystemMessagesActivityPresenter extends AppDelage implements View.OnClickListener {

    private Context context;
    private TextView user_system_messages_messages;
    private XRecyclerView user_system_messages_recyclerview;
    private ImageView user_system_messages_back;
    private UserSystemMessageAdapter userSystemMessageAdapter;
    private TextView user_system_messages_messages1;
    private int count = 10;
    private boolean isdoHttpForCount = false;
    private boolean isdoHttp = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_system_messages;
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();

        //未读消息数量
        user_system_messages_messages = get(R.id.user_system_messages_messages);


        //xrecyclerview
        user_system_messages_recyclerview = get(R.id.user_system_messages_recyclerview);
        user_system_messages_recyclerview.setLayoutManager(new LinearLayoutManager(context));

        //左下角的返回图标
        user_system_messages_back = get(R.id.user_system_messages_back);
        user_system_messages_back.setOnClickListener(this);

        doHttpForCount("/movieApi/tool/v1/verify/findUnreadMessageCount");
        userSystemMessageAdapter = new UserSystemMessageAdapter(context);
        user_system_messages_recyclerview.setAdapter(userSystemMessageAdapter);

        userSystemMessageAdapter.setListener(new UserSystemMessageAdapter.messageAapterStatusChangedListener() {
            @Override
            public void changed(int count) {
                String s = user_system_messages_messages1.getText().toString();
                String replace1 = s.replace("(", "");
                String replace2 = replace1.replace("条未读)", "");
                String trim = replace2.trim();
                int i = Integer.parseInt(trim);
                i--;
                user_system_messages_messages1.setText("(" + i + "条未读)");
            }
        });

        doHttp("/movieApi/tool/v1/verify/findAllSysMsgList");


        user_system_messages_messages1 = get(R.id.user_system_messages_messages);


        user_system_messages_recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                doHttpForCount("/movieApi/tool/v1/verify/findUnreadMessageCount");
                doHttp("/movieApi/tool/v1/verify/findAllSysMsgList");
            }

            @Override
            public void onLoadMore() {
                count += 10;
                doHttpForCount("/movieApi/tool/v1/verify/findUnreadMessageCount");
                doHttp("/movieApi/tool/v1/verify/findAllSysMsgList");
            }
        });
    }

    @Override
    public void successnetwork() {
        super.successnetwork();
        doHttpForCount("/movieApi/tool/v1/verify/findUnreadMessageCount");
        doHttp("/movieApi/tool/v1/verify/findAllSysMsgList");
    }

    private void doHttpForCount(String s) {
        HashMap<String, String> paramsMap = new HashMap<>();

        HashMap<String, String> headMap = new HashMap<>();
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        headMap.put("userId", userId + "");
        headMap.put("sessionId", sessionId);
        new HttpUtil().get(s, paramsMap, headMap).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {

                Log.i("jhktest", "successcount: " + data);
                if (data.contains("成功")) {
                    Gson gson = new Gson();
                    UserSystemCountBean userSystemCountBean = gson.fromJson(data, UserSystemCountBean.class);
                    int count = userSystemCountBean.getCount();
                    user_system_messages_messages1.setText("(" + count + "条未读)");
                } else {
                    Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void fail(String data) {
                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
            }

        });
        isdoHttpForCount = true;
        if (isdoHttp && isdoHttpForCount) {
            user_system_messages_recyclerview.refreshComplete();
            user_system_messages_recyclerview.loadMoreComplete();
            isdoHttpForCount = false;
            isdoHttp = false;
        }
    }

    private void doHttp(String s) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("page", 1 + "");
        paramsMap.put("count", count + "");

        HashMap<String, String> headMap = new HashMap<>();
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        headMap.put("userId", userId + "");
        headMap.put("sessionId", sessionId);
        new HttpUtil().get(s, paramsMap, headMap).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("成功")) {
                    Gson gson = new Gson();
                    UserSystemMessagesBean userSystemMessagesBean = gson.fromJson(data, UserSystemMessagesBean.class);
                    List<UserSystemMessagesBean.ResultBean> result = userSystemMessagesBean.getResult();
                    userSystemMessageAdapter.setList(result);

                } else {
                    Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
                }
                Log.i("jhktest", "success: " + data);
            }

            @Override
            public void fail(String data) {
                Log.i("jhktest", "fail: " + data);
                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
            }
        });
        isdoHttp = true;
        if (isdoHttp && isdoHttpForCount) {
            user_system_messages_recyclerview.refreshComplete();
            user_system_messages_recyclerview.loadMoreComplete();
            isdoHttpForCount = false;
            isdoHttp = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //点击页面左下角的返回图标  关闭当前页面
            case R.id.user_system_messages_back:
                ((UserSystemMessagesActivity) context).finish();
                break;


        }
    }
}
