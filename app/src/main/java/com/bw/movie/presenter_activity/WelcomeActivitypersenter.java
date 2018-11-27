package com.bw.movie.presenter_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.net.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivitypersenter extends AppDelage {

    private static final String LOG = "GuideActivity";
    private ViewPager viewPager;
    private List<Integer> imageIDList=new ArrayList<>();
    private Button mExperience;

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initData() {
        super.initData();
        //初始化引导数据
        initGuideData();
        SharedPreferencesUtils.putBoolean(context,"isfrist",true);
        mExperience = get(R.id.welcome_experience);
        mExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, MainActivity.class));
            }
        });
        viewPager = (ViewPager) get(R.id.welcome_pager);
        viewPager.setAdapter(new GuideAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(imageIDList.size()-1==position){
                    mExperience.setVisibility(View.VISIBLE);
                }else{
                    mExperience.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void initGuideData() {
        imageIDList = new ArrayList();
        imageIDList.add(R.drawable.welcome1);
        imageIDList.add(R.drawable.welcome2);
        imageIDList.add(R.drawable.welcome3);
        imageIDList.add(R.drawable.welcome4);

    }


    private Context context;

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    private class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageIDList.size();
        }

        /**
         * 判断当前分页是不是view
         * 由于ViewPager里面的分页可以填入Fragment
         *
         * @param view
         * @param object
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 清理内存
         * 从第一页滑动到第二页，此时第一页的内存应该释放
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);//释放滑动过后的前一页
        }

        /**
         * 得到---->暂时是没有用的
         *
         * @param object
         * @return
         */
        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        /**
         * 初始化分页
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(imageIDList.get(position));
            container.addView(imageView);
            return imageView;
        }
    }
}

