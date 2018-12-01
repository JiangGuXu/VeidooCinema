package com.bw.movie.presenter_activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.UserFeedBackActivity;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;

import java.util.HashMap;

/**
 * 2018年11月30日 19:14:33
 * 焦浩康
 * 意见反馈页面
 */

public class UserFeedBackActivityPresenter extends AppDelage implements View.OnClickListener {

    private Context context;
    private ImageView user_feedback_back;
    private Button user_feedback_commit;
    private EditText user_feedback_edittext;
    private RelativeLayout user_feedback_editlayout;
    private RelativeLayout user_feedback_winlayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_feedback;
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();

        user_feedback_back = get(R.id.user_feedback_back);
        user_feedback_back.setOnClickListener(this);

        user_feedback_commit = get(R.id.user_feedback_commit);
        user_feedback_commit.setOnClickListener(this);

        user_feedback_editlayout = get(R.id.user_feedback_editlayout);
        user_feedback_winlayout = get(R.id.user_feedback_winlayout);

        user_feedback_editlayout.setVisibility(View.VISIBLE);
        user_feedback_winlayout.setVisibility(View.GONE);

        user_feedback_edittext = get(R.id.user_feedback_edittext);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_feedback_back:
                ((UserFeedBackActivity) context).finish();
                break;
            case R.id.user_feedback_commit:
                String feedbackText = user_feedback_edittext.getText().toString();
                if (!TextUtils.isEmpty(feedbackText)) {
                    HashMap<String, String> headMap = new HashMap<>();
                    HashMap<String, String> parameterMap = new HashMap<>();


                    int userId = SharedPreferencesUtils.getInt(context, "userId");
                    String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
                    headMap.put("userId", userId + "");
                    headMap.put("sessionId", sessionId);
                    headMap.put("Content-Type", "application/x-www-form-urlencoded");
                    headMap.put("ak", "0110010010000");

                    parameterMap.put("content", feedbackText);

                    new HttpUtil().postHead("/movieApi/tool/v1/verify/recordFeedBack", parameterMap, headMap)
                            .result(new HttpUtil.HttpListener() {
                                @Override
                                public void success(String data) {
                                    if (data.contains("成功")) {
                                        Toast.makeText(context, "反馈成功", Toast.LENGTH_SHORT).show();
                                        user_feedback_editlayout.setVisibility(View.GONE);
                                        user_feedback_winlayout.setVisibility(View.VISIBLE);
                                    } else {
                                        Toast.makeText(context, "反馈失败", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void fail(String data) {
                                    Toast.makeText(context, "反馈失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(context, "写点东西吧!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
