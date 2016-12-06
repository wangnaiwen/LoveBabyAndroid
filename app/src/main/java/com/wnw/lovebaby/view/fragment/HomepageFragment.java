package com.wnw.lovebaby.view.fragment;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.DepthPageTransformer;
import com.wnw.lovebaby.adapter.ImagePageAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wnw on 2016/12/1.
 */

public class HomepageFragment extends Fragment implements View.OnClickListener{

    public static int[] images = {
            R.drawable.main_img1,
            R.drawable.main_img2,
            R.drawable.main_img3,
            R.drawable.main_img4};

    /**
     * 4个menu
     * */
    private LinearLayout todayNewGoods;
    private LinearLayout classify;
    private LinearLayout myShop;
    private LinearLayout inviteOpenShop;

    private View view;
    private LayoutInflater mInflater;
    private Context context;
    private ViewPager mviewPager;


    /**存放加载出来的图片*/
    private List<Integer> imageList;

    /**用于小圆点图片*/
    private List<ImageView> dotViewList;

    /**用于存放轮播效果图片*/
    private List<ImageView> list;

    LinearLayout dotLayout;

    private int currentItem  = 0;//当前页面

    boolean isAutoPlay = true;//是否自动轮播

    private ScheduledExecutorService scheduledExecutorService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        context = inflater.getContext();
        view = inflater.inflate(R.layout.fragment_homepage, container, false);
        initView();
        return view;
    }

    public void initView(){
        mviewPager = (ViewPager)view.findViewById(R.id.homepage_vp_image);

        dotLayout = (LinearLayout)view.findViewById(R.id.dotLayout);
        dotLayout.removeAllViews();

        dotViewList = new ArrayList<ImageView>();
        list = new ArrayList<ImageView>();

        setImageList(images);
        setImageViewList();
        setDotView();
        setViewPageAtr();

        todayNewGoods = (LinearLayout)view.findViewById(R.id.homepage_menu_today_new_goods);
        classify = (LinearLayout)view.findViewById(R.id.homepage_menu_classify);
        myShop = (LinearLayout)view.findViewById(R.id.homepage_menu_myshop);
        inviteOpenShop = (LinearLayout)view.findViewById(R.id.homepage_menu_invite);

        todayNewGoods.setOnClickListener(this);
        classify.setOnClickListener(this);
        myShop.setOnClickListener(this);
        inviteOpenShop.setOnClickListener(this);
    }

    /**
     *  加载完成后，设置这个ImageList
     * */
    private void setImageList(List<Integer> imageList){
        this.imageList = imageList;
    }

    private void setImageList(int[] images){
        imageList = new ArrayList<>();
        int count = images.length;
        for(int i = 0; i < count; i++){
            imageList.add(images[i]);
        }
    }
    /**
     * 得到图片后开始装载ImageView 和DotView
     * */
    private void setImageViewList(){
        int count = imageList.size();
        for(int i = 0; i < count; i++){
            ImageView img = (ImageView) mInflater.inflate(R.layout.vp_image_item, null);
            img.setBackgroundResource(imageList.get(i));
            list.add(img);
        }
    }
    /**
     * setDotView
     * */
    private void setDotView(){
        int count = list.size();
        for (int i = 0; i < count; i++) {
            ImageView dotView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

            params.leftMargin = 15;//设置小圆点的外边距
            params.rightMargin = 15;

            params.height = 20;//设置小圆点的大小
            params.width = 20;

            if(i == 0){
                dotView.setImageResource(R.drawable.ic_page_indicator_focused);
            }else{
                dotView.setImageResource(R.drawable.ic_page_indicator);
            }
            dotLayout.addView(dotView, params);
            dotViewList.add(dotView);
        }
    }

    /**
     * 图片和点都准备好了，开始加载Viewpager的属性并且设置它
     *
     * */
    private void setViewPageAtr(){
        ImagePageAdapter adapter = new ImagePageAdapter((ArrayList)list);
        mviewPager.setAdapter(adapter);
        mviewPager.setCurrentItem(0);
        mviewPager.setOnPageChangeListener(new MyPageChangeListener());
        mviewPager.setPageTransformer(true, new DepthPageTransformer());
        if(isAutoPlay){
            startPlay();
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 100){
                mviewPager.setCurrentItem(currentItem);
            }
        }
    };

    /**
     * 开始轮播图切换
     */
    private void startPlay(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);
        //根据他的参数说明，第一个参数是执行的任务，第二个参数是第一次执行的间隔，第三个参数是执行任务的周期；
    }

    /**
     *执行轮播图切换任务
     *
     */
    private class SlideShowTask implements Runnable{
        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (mviewPager) {
                currentItem = (currentItem+1)%list.size();
                handler.sendEmptyMessage(100);
            }
        }
    }

    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     *
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        boolean isAutoPlay = false;
        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    Log.d("wnw", mviewPager.getAdapter().getCount()+" "+mviewPager.getCurrentItem());
                    if (mviewPager.getCurrentItem() == mviewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        mviewPager.setCurrentItem(0);
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (mviewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        mviewPager.setCurrentItem(mviewPager.getAdapter().getCount() - 1);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageSelected(int pos) {
            // TODO Auto-generated method stub
            //这里面动态改变小圆点的被背景，来实现效果
            currentItem = pos;
            for(int i=0;i < dotViewList.size();i++){
                if(i == pos){
                    ((ImageView)dotViewList.get(pos)).setImageResource(R.drawable.ic_page_indicator_focused);
                }else {
                    ((ImageView)dotViewList.get(i)).setImageResource(R.drawable.ic_page_indicator);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.homepage_menu_today_new_goods:
                Toast.makeText(context, "今日上新", Toast.LENGTH_SHORT).show();
                break;
            case R.id.homepage_menu_classify:
                Toast.makeText(context, "商品类别", Toast.LENGTH_SHORT).show();
                break;
            case R.id.homepage_menu_myshop:
                Toast.makeText(context, "我的店铺", Toast.LENGTH_SHORT).show();
                break;
            case R.id.homepage_menu_invite:
                Toast.makeText(context, "邀请开店", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
