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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.CollegeLvAdapter;
import com.wnw.lovebaby.adapter.DepthPageTransformer;
import com.wnw.lovebaby.adapter.ImagePageAdapter;
import com.wnw.lovebaby.bean.ArticleCoverItem;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wnw on 2016/12/1.
 */

public class CollegeFragment extends Fragment implements AdapterView.OnItemClickListener{
    public static int[] images = {
            R.drawable.main_img1,
            R.drawable.main_img2,
            R.drawable.main_img3,
            R.drawable.main_img4};

    private View view;
    private Context context;
    private LayoutInflater mInflater;

    private ViewPager viewPager;

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

    /**
     * listview
     * */
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_college, container, false);
        this.context = inflater.getContext();
        this.mInflater = inflater;
        initView();
        return view;
    }

    private void initView(){
        viewPager = (ViewPager) view.findViewById(R.id.college_vp_image);
        dotLayout = (LinearLayout)view.findViewById(R.id.college_dotLayout);
        dotLayout.removeAllViews();

        dotViewList = new ArrayList<ImageView>();
        list = new ArrayList<ImageView>();

        setImageList(images);
        setImageViewList();
        setDotView();
        setViewPageAtr();

        listView = (ListView)view.findViewById(R.id.college_lv);
        listView.setOnItemClickListener(this);
        setListView();
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
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        if(isAutoPlay){
            startPlay();
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 100){
                viewPager.setCurrentItem(currentItem);
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
            synchronized (viewPager) {
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
                    Log.d("wnw", viewPager.getAdapter().getCount()+" "+viewPager.getCurrentItem());
                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        viewPager.setCurrentItem(0);
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
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

    private void setListView(){
        buildListView();
        CollegeLvAdapter collegeLvAdapter = new CollegeLvAdapter(context,articleCoverItemList);
        listView.setAdapter(collegeLvAdapter);
        listView.setDivider(null);
    }

    /**
     *
     * */
    private List<ArticleCoverItem> articleCoverItemList;
    private void buildListView(){
        articleCoverItemList = new ArrayList<>();
        for(int i = 0; i < 10;i++){
            ArticleCoverItem articleCoverItem = new ArticleCoverItem();
            articleCoverItem.setImage(R.mipmap.a);
            articleCoverItem.setTitle("孩子，我愿你更够坚强独立，但是我愿你逞强孤独" +i);
            articleCoverItem.setReadNum(i+100);
            articleCoverItem.setPublishTime("2016-12-08 14:08");
            articleCoverItem.setPraiseNum(i+50);
            articleCoverItemList.add(articleCoverItem);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, i +"", Toast.LENGTH_SHORT).show();
    }
}
