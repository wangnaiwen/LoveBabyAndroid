package com.wnw.lovebaby.view.fragment;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.DepthPageTransformer;
import com.wnw.lovebaby.adapter.GoodsCoverAdapter;
import com.wnw.lovebaby.adapter.ImagePageAdapter;
import com.wnw.lovebaby.bean.GoodsCoverItem;
import com.wnw.lovebaby.view.activity.InviteOpenShopActivity;
import com.wnw.lovebaby.view.activity.SearchGoodsActivity;
import com.wnw.lovebaby.view.activity.SortListActivity;
import com.wnw.lovebaby.view.activity.TodayNewGoodsActivity;
import com.wnw.lovebaby.view.costom.GoodsGridView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wnw on 2016/12/1.
 */

public class HomepageFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    public static int[] images = {
            R.drawable.main_img1,
            R.drawable.main_img2,
            R.drawable.main_img3,
            R.drawable.main_img4};

    /**
     * search_bar
     * */
    private RelativeLayout searchBar;

    /**
     * 4个menu
     * */
    private LinearLayout todayNewGoods;
    private LinearLayout classify;
    private LinearLayout myShop;
    private LinearLayout inviteOpenShop;

    /**
     * 猜你喜欢的内容
     * */
    private int imagesIcon[] = {R.mipmap.n1, R.mipmap.m2, R.mipmap.m3, R.mipmap.m4};
    private String titles[] = {"奶粉1","奶粉2","奶粉3","奶粉4"};
    private int prices[] = {10100,10199,10388,10409};
    private GoodsGridView goodsGridView;
    private GoodsCoverAdapter goodsCoverAdapter;
    private List<GoodsCoverItem> goodsCoverItemList;

    /**
     * 限时抢购的内容
     * */
    private int imagesIcon2[] = {R.mipmap.b5,R.mipmap.b2, R.mipmap.b3,R.mipmap.b4};
    private String titles2[] = {"宝宝宝宝1","宝宝宝宝2","宝宝宝宝3","宝宝宝宝4"};
    private int price2[] = {10000000,10000000,10000000,10000000};
    private GoodsGridView deadLineGoodsGridView;
    private GoodsCoverAdapter deadLineGoodsCoverAdapter;
    private List<GoodsCoverItem> deadlineGoodsCoverItemList;

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

    /**
     * 点的布局
     * */
    LinearLayout dotLayout;

    /**
     * 下拉刷新
     * */
    SwipeRefreshLayout homepageSwipRefresh;

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


    private void buildGvData(){
        goodsCoverItemList = new ArrayList<>();
        deadlineGoodsCoverItemList = new ArrayList<>();
        int count = Math.min(Math.min(imagesIcon.length, titles.length), Math.min(imagesIcon.length, prices.length));
        for(int i = 0; i < count; i++){
            GoodsCoverItem item = new GoodsCoverItem();
            item.setImage(imagesIcon[i]);
            item.setTitle(titles[i]);
            item.setPrice(prices[i]);
            goodsCoverItemList.add(item);
        }

        int count2 = Math.min(Math.min(imagesIcon2.length, titles2.length), Math.min(imagesIcon2.length, price2.length));
        for(int i = 0; i < count2; i++){
            GoodsCoverItem item = new GoodsCoverItem();
            item.setImage(imagesIcon2[i]);
            item.setTitle(titles2[i]);
            item.setPrice(price2[i]);
            deadlineGoodsCoverItemList.add(item);
        }
    }

    public void initView(){
        homepageSwipRefresh = (SwipeRefreshLayout)view.findViewById(R.id.homepage_swiperefresh_layout);
        homepageSwipRefresh.setColorSchemeResources(R.color.colorIconSelected);
        homepageSwipRefresh.setOnRefreshListener(this);

        searchBar = (RelativeLayout)view.findViewById(R.id.btn_search_bar);
        searchBar.setOnClickListener(this);

        mviewPager = (ViewPager)view.findViewById(R.id.homepage_vp_image);
        goodsGridView = (GoodsGridView)view.findViewById(R.id.gv_guess_goods) ;
        deadLineGoodsGridView = (GoodsGridView)view.findViewById(R.id.gv_deadline_goods);
        buildGvData();
        goodsCoverAdapter = new GoodsCoverAdapter(context, goodsCoverItemList);
        goodsGridView.setAdapter(goodsCoverAdapter);
        goodsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, "item="+i, Toast.LENGTH_SHORT).show();
            }
        });

        deadLineGoodsCoverAdapter = new GoodsCoverAdapter(context, deadlineGoodsCoverItemList);
        deadLineGoodsGridView.setAdapter(deadLineGoodsCoverAdapter);
        deadLineGoodsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, "item="+i, Toast.LENGTH_SHORT).show();
            }
        });
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
                startAnotherAty(TodayNewGoodsActivity.class);
                break;
            case R.id.homepage_menu_classify:
                startAnotherAty(SortListActivity.class);
                break;
            case R.id.homepage_menu_myshop:
                Toast.makeText(context, "我的店铺", Toast.LENGTH_SHORT).show();
                break;
            case R.id.homepage_menu_invite:
                startAnotherAty(InviteOpenShopActivity.class);
                break;
            case R.id.btn_search_bar:
                Intent intent = new Intent(context, SearchGoodsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        refreshHomePage();
    }

    /**
     *  start another activity , need'nt return the data
     * */
    private void startAnotherAty(Class<?> aty){
        Intent intent = new Intent(context, aty );
        startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    private void setGuessYouLoveImages(List<GoodsCoverItem> guessYouLoveImages){
        this.goodsCoverItemList = guessYouLoveImages;
    }


    /**
     *
     * 下拉刷新后，在这里重新获取数据，然后再次加载
     * */
    private void refreshHomePage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                ((Activity)context).runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        //重新加载数据，设置为SwipeRefreshLayout为false
                        homepageSwipRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
