package com.liu.learning.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现可循环，可轮播的viewpager
 */
@SuppressLint("NewApi")
public class LoopViewPager<T> extends RelativeLayout implements OnPageChangeListener {

    private List<ImageView> imageViews = new ArrayList<ImageView>();
    private ImageView[] indicators;
    private LinearLayout indicatorLayout; // 指示器
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private int time = 2000; // 默认轮播时间
    private int currentPosition = 0; // 轮播当前位置
    private boolean isScrolling = false; // 滚动框是否滚动着
    private boolean isCycle = false; // 是否循环
    private boolean isWheel = false; // 是否轮播
    private long releaseTime = 0; // 手指松开、页面不滚动时间，防止手机松开后短时间进行切换
    private static final int WHEEL = 100; // 转动
    private static final int WHEEL_WAIT = 101; // 等待
    private ImageListener listener;
    private int selectRes = R.mipmap.bottom_index_blue;
    private int unselectRes = R.mipmap.bottom_index_white;
    private int gravity = 1;
    private int spacing = 10;
    private List<T> data;

    public LoopViewPager(Context context) {
        this(context, null);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoopViewPager);
        gravity = a.getInt(R.styleable.LoopViewPager_gravity, 1);
        spacing = a.getDimensionPixelSize(R.styleable.LoopViewPager_indicatorSpacing, 10);
        initView();
    }







    private void initView() {
        viewPager = new ViewPager(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        viewPager.setLayoutParams(params);
        this.addView(viewPager);
        indicatorLayout = new LinearLayout(getContext());
        indicatorLayout.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams params1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params1.bottomMargin = UtilPixelTransfrom.dip2px(getContext(),5);
        switch (gravity)
        {
            case 0:
                params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                break;
            case 1:
                params1.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                break;

            case 2:
                params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                break;
        }
        params1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        indicatorLayout.setLayoutParams(params1);
        this.addView(indicatorLayout);
    }



    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int action = msg.what;
            switch (action)
            {
                case WHEEL:
                {
                    if(imageViews.size() != 0)
                    {
                        if (!isScrolling) {
                            int position = (currentPosition + 1) % imageViews.size();
                            viewPager.setCurrentItem(position, true);
                            releaseTime = System.currentTimeMillis();
                        }
                        handler.postDelayed(runnable, time);
                    }
                }
                    break;

                case WHEEL_WAIT:
                {
                    if(imageViews.size() != 0)
                    {
                        handler.postDelayed(runnable, time);
                    }
                }
                    break;
            }

        }
    };


    /**
     * 初始化viewpager
     *
     * @param views        要显示的views
     * @param showPosition 默认显示位置
     */
    public void setData(List<T> list, ImageListener listener) {
        this.listener = listener;
        if(list == null || list.size() == 0)
        {
            return;
        }

        T tStart = list.get(0);
        T tEnd = list.get(list.size() - 1);
        if(isCycle) {
            list.add(0, tEnd);
            list.add(tStart);
        }
        this.data = list;
        imageViews.clear();
        for (int i = 0; i < list.size(); i++)
        {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            listener.initData(imageView, list.get(i));
            this.imageViews.add(imageView);
        }
        int ivSize = list.size();
        LinearLayout.LayoutParams paramsone = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        paramsone.leftMargin = spacing;//
        paramsone.rightMargin = spacing;

        // 设置指示器
        if (isCycle) {
            indicators = new ImageView[ivSize - 2];
        }
        else {
            indicators = new ImageView[ivSize];
        }

        indicatorLayout.removeAllViews();
        for (int i = 0; i < indicators.length; i++) {
            ImageView img = new ImageView(getContext());
            indicators[i] = img;
            indicatorLayout.addView(img, paramsone);
        }



        // 默认指向第一项，下方viewPager.setCurrentItem将触发重新计算指示器指向
        setIndicator(0);

        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(this);
        if(adapter == null)
        {

            adapter = new ViewPagerAdapter();
            viewPager.setAdapter(adapter);
        }
        else {
            adapter.notifyDataSetChanged();
        }
        viewPager.setCurrentItem(1);

    }

    /**
     * 设置指示器居中，默认指示器在右方
     */
    public void setIndicatorCenter() {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        indicatorLayout.setLayoutParams(params);
    }

    /**
     * 是否循环，默认不开启，开启前，请将views的最前面与最后面各加入一个视图，用于循环
     *
     * @param isCycle 是否循环
     */
    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }

    /**
     * 是否处于循环状态
     *
     * @return
     */
    public boolean isCycle() {
        return isCycle;
    }

    /**
     * 设置是否轮播，默认不轮播,轮播一定是循环的
     *
     * @param isWheel
     */
    public void setWheel(boolean isWheel) {
        this.isWheel = isWheel;
        isCycle = true;
        if (isWheel) {
            handler.postDelayed(runnable, time);
        }
    }

    /**
     * 是否处于轮播状态
     *
     * @return
     */
    public boolean isWheel() {
        return isWheel;
    }

    final Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if (getContext() != null
                    && isWheel) {
                long now = System.currentTimeMillis();
                // 检测上一次滑动时间与本次之间是否有触击(手滑动)操作，有的话等待下次轮播
                if (now - releaseTime > time - 500) {
                    handler.sendEmptyMessage(WHEEL);
                } else {
                    handler.sendEmptyMessage(WHEEL_WAIT);
                }
            }
        }
    };



    /**
     * 设置轮播暂停时间，即没多少秒切换到下一张视图.默认5000ms
     *
     * @param time 毫秒为单位
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * 刷新数据，当外部视图更新后，通知刷新数据
     */
    public void refreshData() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }




    /**
     * 页面适配器 返回对应的view
     *
     * @author Yuedong Li
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            ImageView v = imageViews.get(position);
                v.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if(listener != null)
                        {
                            listener.onImageClick(data.get(position));
                        }
                    }
                });
            container.addView(v);
            return v;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (arg0 == 1) { // viewPager在滚动
            isScrolling = true;
            return;
        } else if (arg0 == 0) { // viewPager滚动结束
            releaseTime = System.currentTimeMillis();
            viewPager.setCurrentItem(currentPosition, false);
        }
        isScrolling = false;
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {
        int max = imageViews.size() - 1;
        int position = arg0;
        currentPosition = arg0;
        if (isCycle) {
            if (arg0 == 0) {
                currentPosition = max - 1;
            } else if (arg0 == max) {
                currentPosition = 1;
            }
            position = currentPosition - 1;
        }
        setIndicator(position);
    }



    /**
     * 设置指示器
     *
     * @param selectedPosition 默认指示器位置
     */
    private void setIndicator(int selectedPosition) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i]
                    .setBackgroundResource(unselectRes);
        }
        if (indicators.length > selectedPosition)
            indicators[selectedPosition]
                    .setBackgroundResource(selectRes);
    }

    public void setIndicatorRes(int unselectRes, int selectRes)
    {
        this.unselectRes = unselectRes;
        this.selectRes = selectRes;
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 开始滑动
     */
    public void start()
    {
        handler.postDelayed(runnable, time);
    }

    /**
     * 停止滑动
     */
    public void stop()
    {
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 轮播控件的监听事件
     *
     * @author minking
     */
    public interface ImageListener<T> {

        /**
         * 单击图片事件
         *
         * @param
         * @param imageView
         */
        public void onImageClick(T data);
        public void initData(ImageView view, T t);
    }
}