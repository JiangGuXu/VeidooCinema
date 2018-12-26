package com.bw.movie.utils.net;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bw.movie.R;


public class LodingDialog extends Dialog {

    public LodingDialog(Context context) {
        this(context, R.style.BaseDialog);
    }

    public LodingDialog(Context context, int theme) {
        super(context, theme);
        initView(context);
    }

    private void initView(Context context) {
        int w = getWinWidth(context);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = w - (w >> 3);
        this.getWindow().setAttributes(params);
    }

    public int getWinWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static class Builder {
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }


        public LodingDialog create() {
            return create(R.layout.load);
        }

        public LodingDialog create(int layoutId) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final LodingDialog dialog = new LodingDialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            View layout = inflater.inflate(layoutId, null);

            ImageView mImageView = (ImageView) layout.findViewById(R.id.image);
            final AnimationDrawable animationDrawable = (AnimationDrawable) mImageView.getBackground();
            mImageView.post(new Runnable() {
                @Override
                public void run() {
                    animationDrawable.start();
                }
            });
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            return dialog;
        }
    }
}

