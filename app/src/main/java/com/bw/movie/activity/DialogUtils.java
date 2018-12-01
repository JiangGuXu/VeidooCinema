package com.bw.movie.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bw.movie.R;

public class DialogUtils extends AlertDialog{
    private View view;
    public DialogUtils(Context context, View view) {
        super(context);
        this.view=view;
        initView(context);
    }

    public DialogUtils(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    public DialogUtils(Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    private void initView(Context context) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
                Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.dialogAnimation);
    }
}
