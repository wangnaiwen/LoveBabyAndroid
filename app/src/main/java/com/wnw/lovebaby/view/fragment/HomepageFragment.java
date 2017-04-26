package com.wnw.lovebaby.view.fragment;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.bumptech.glide.Glide;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.DepthPageTransformer;
import com.wnw.lovebaby.adapter.ImagePageAdapter;
import com.wnw.lovebaby.adapter.ProductAdapter;
import com.wnw.lovebaby.domain.Product;
import com.wnw.lovebaby.domain.Shop;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.FindHotSalePresenter;
import com.wnw.lovebaby.presenter.FindNewProductPresenter;
import com.wnw.lovebaby.presenter.FindShopByUserIdPresenter;
import com.wnw.lovebaby.presenter.FindSpecialPricePresenter;
import com.wnw.lovebaby.view.activity.EditMyShopActivity;
import com.wnw.lovebaby.view.activity.InviteOpenShopActivity;
import com.wnw.lovebaby.view.activity.MyShopActivity;
import com.wnw.lovebaby.view.activity.OpenMyShopActivity;
import com.wnw.lovebaby.view.activity.ProductDetailActivity;
import com.wnw.lovebaby.view.activity.SearchGoodsActivity;
import com.wnw.lovebaby.view.activity.SortListActivity;
import com.wnw.lovebaby.view.activity.TodayNewGoodsActivity;
import com.wnw.lovebaby.view.costom.GoodsGridView;
import com.wnw.lovebaby.view.viewInterface.IFindHotSaleView;
import com.wnw.lovebaby.view.viewInterface.IFindNewProductView;
import com.wnw.lovebaby.view.viewInterface.IFindShopByUserIdView;
import com.wnw.lovebaby.view.viewInterface.IFindSpecialPriceView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wnw on 2016/12/1.
 */

public class HomepageFragment extends Fragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener,
        IFindHotSaleView, IFindSpecialPriceView,IFindNewProductView,IFindShopByUserIdView{

    public static int IMAGE_PAGER = 1;   //图片轮播的Handler发送的消息
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

    private int userId;
    private Shop shop;
    private FindShopByUserIdPresenter findShopByUserIdPresenter;

    /**
     * 猜你喜欢的内容
     * */
    private GoodsGridView youLoveProductView = null;        //猜你喜欢商品区
    private ProductAdapter youLoveProductAdapter = null;    //猜你喜欢商品的Adapter
    List<Product> youLoveProductList = null;                //猜你喜欢商品
    FindNewProductPresenter youLovePricePresenter = null;  //猜你喜欢商品Presenter

    /**
     * 限时抢购的内容
     * */
    private GoodsGridView specialPriceProductView = null;        //特价商品区
    private ProductAdapter specialPriceProductAdapter = null;    //特价商品的Adapter
    List<Product> specialPriceProductList = null;                //特价商品
    FindSpecialPricePresenter findSpecialPricePresenter = null;  //特价商品Presenter

    private View view;
    private LayoutInflater mInflater;
    private Context context;
    private ViewPager mviewPager;

    //private List<Integer> imageList;
    private List<ImageView> dotViewList;      //用于小圆点图片
    private List<ImageView> list;            //用于存放轮播效果图片
    LinearLayout dotLayout;                  //点布局
    SwipeRefreshLayout homepageSwipRefresh;  //下拉刷新

    private int currentItem  = 0;            //当前页面
    boolean isAutoPlay = true;               //是否自动轮播
    private ScheduledExecutorService scheduledExecutorService;
    private ImagePageAdapter imagePageAdapter = null; //热卖商品图片轮播的Adapter
    List<Product> hotSaleProductList = null;          //热卖商品，在轮播中查看
    FindHotSalePresenter findHotSalePresenter = null; //热卖商品Presenter

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        context = inflater.getContext();
        view = inflater.inflate(R.layout.fragment_homepage, container, false);

        //初始化界面，初始化Presenter，用Presenter加载数据
        initView();
        getUserId();
        initPresenter();
        loadData();
        return view;
    }

    private void getUserId(){
        SharedPreferences sharedPreferences = ((Activity)context).getSharedPreferences("account", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("id", 0);
    }

    public void initView(){
        homepageSwipRefresh = (SwipeRefreshLayout)view.findViewById(R.id.homepage_swiperefresh_layout);
        homepageSwipRefresh.setColorSchemeResources(R.color.colorIconSelected);
        homepageSwipRefresh.setOnRefreshListener(this);

        searchBar = (RelativeLayout)view.findViewById(R.id.btn_search_bar);
        searchBar.setOnClickListener(this);

        mviewPager = (ViewPager)view.findViewById(R.id.homepage_vp_image);
        youLoveProductView = (GoodsGridView)view.findViewById(R.id.gv_guess_goods) ;
        specialPriceProductView = (GoodsGridView)view.findViewById(R.id.gv_deadline_goods);

        dotLayout = (LinearLayout)view.findViewById(R.id.dotLayout);
        dotLayout.removeAllViews();

        todayNewGoods = (LinearLayout)view.findViewById(R.id.homepage_menu_today_new_goods);
        classify = (LinearLayout)view.findViewById(R.id.homepage_menu_classify);
        myShop = (LinearLayout)view.findViewById(R.id.homepage_menu_myshop);
        inviteOpenShop = (LinearLayout)view.findViewById(R.id.homepage_menu_invite);

        todayNewGoods.setOnClickListener(this);
        classify.setOnClickListener(this);
        myShop.setOnClickListener(this);
        inviteOpenShop.setOnClickListener(this);

        specialPriceProductView.setOnItemClickListener(this);
        youLoveProductView.setOnItemClickListener(this);

    }

    /**
     * 初始化Presenter
     * */
    private void initPresenter(){
        findHotSalePresenter = new FindHotSalePresenter(context,this);
        findSpecialPricePresenter = new FindSpecialPricePresenter(context,this);
        youLovePricePresenter = new FindNewProductPresenter(context,this);
        findShopByUserIdPresenter = new FindShopByUserIdPresenter(context ,this);
    }

    /**
     * 加载数据
     * */
    private void loadData(){
        if(NetUtil.getNetworkState(context) == NetUtil.NETWORN_NONE){
            Toast.makeText(context, "网络不可用",Toast.LENGTH_SHORT).show();
        }else{
            findHotSalePresenter.load();
            findSpecialPricePresenter.load();
            youLovePricePresenter.load();
        }
    }

    @Override
    public void showLoading() {
        showDialogs();
    }

    ProgressDialog dialog = null;
    private void showDialogs(){
        if(dialog == null){
            dialog = new ProgressDialog(context);
            dialog.setMessage("正在努力中...");
        }
        if(!dialog.isShowing()){
            dialog.show();
        }
    }

    private void dismissDialogs(){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void showHotSale(List<Product> products) {
        this.hotSaleProductList = products;
        if(products == null){
            Toast.makeText(context,"暂无法加载更多", Toast.LENGTH_SHORT).show();
        }else{
            setImagePagerList();   //设置图片轮播开始
        }
    }

    @Override
    public void showSpecialPrice(List<Product> products) {
        this.specialPriceProductList = products;
        if(products == null){
            Toast.makeText(context,"暂无法加载更多", Toast.LENGTH_SHORT).show();
        }else {
            setSpecialPriceProductAdapter();
        }
    }

    @Override
    public void showNewProduct(List<Product> products) {
        dismissDialogs();
        if(homepageSwipRefresh.isRefreshing()){
            homepageSwipRefresh.setRefreshing(false);
        }
        this.youLoveProductList = products;
        if(products == null){
            Toast.makeText(context,"暂无法加载更多", Toast.LENGTH_SHORT).show();
        }else {
            setYouLoveProductAdapter();
        }
    }

    private void startFindShop(){
        if(NetUtil.getNetworkState(context) == NetUtil.NETWORN_NONE){
            Toast.makeText(context, "暂无网络",Toast.LENGTH_SHORT).show();
        }else {
            findShopByUserIdPresenter.findShopByUserId(userId);
        }
    }

    @Override
    public void showDialog() {
        showDialogs();
    }

    @Override
    public void showShopsByUserId(Shop shop) {
        dismissDialogs();
        this.shop = shop;
        if(findPosition == 0){
            openShop();
        }else if(findPosition == 1) {
            openInviteeShop();
        }
    }

    /**
     * 得到热卖商品的图片后开始装载ImageView 和DotView
     * */
    private void setImagePagerList(){
        dotViewList = new ArrayList<ImageView>();
        list = new ArrayList<ImageView>();
        int count = hotSaleProductList.size();
        for(int i = 0; i < count; i++){
            ImageView img = (ImageView) mInflater.inflate(R.layout.vp_image_item, null);
            Glide.with(context).load(hotSaleProductList.get(i).getCoverImg()).into(img);
            list.add(img);
        }
        //设置小圆点
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

        //图片和点都准备好了，开始加载Viewpager的属性并且设置它
        if(imagePageAdapter == null) {
            imagePageAdapter = new ImagePageAdapter(context,handler,(ArrayList) list);
            mviewPager.setAdapter(imagePageAdapter);
            mviewPager.setCurrentItem(0);
            mviewPager.setOnPageChangeListener(new MyPageChangeListener());
            mviewPager.setPageTransformer(true, new DepthPageTransformer());
        }else{
            imagePageAdapter.setImageViewList(list);
            imagePageAdapter.notifyDataSetChanged();
        }
        if(isAutoPlay){
            startPlay();
        }
    }

    //设置特价商品区的Adapter
    private void setSpecialPriceProductAdapter(){
        if(specialPriceProductAdapter == null){
            specialPriceProductAdapter = new ProductAdapter(context, specialPriceProductList);
            specialPriceProductView.setAdapter(specialPriceProductAdapter);
        }else {
            specialPriceProductAdapter.setDatas(specialPriceProductList);
            specialPriceProductAdapter.notifyDataSetChanged();
        }
    }

    //设置猜你喜欢商品区的Adapter
    private void setYouLoveProductAdapter(){
        if(youLoveProductAdapter == null){
            youLoveProductAdapter = new ProductAdapter(context, youLoveProductList);
            youLoveProductView.setAdapter(youLoveProductAdapter);
        }else {
            youLoveProductAdapter.setDatas(youLoveProductList);
            youLoveProductAdapter.notifyDataSetChanged();
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {super.handleMessage(msg);
            if(msg.what == 100){
                mviewPager.setCurrentItem(currentItem);
            }else if(msg.what == IMAGE_PAGER){ //图片轮播里的点击事件传递出来
                int position = msg.arg1;
                Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("product",hotSaleProductList.get(position));
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

    private int findPosition = 0; // 0的时候，说明是点击了我的店铺，1是说明点击了邀请开店
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
                findPosition = 0;
                startFindShop();
                break;
            case R.id.homepage_menu_invite:
                findPosition = 1;
                startFindShop();
                break;
            case R.id.btn_search_bar:
                Intent intent = new Intent(context, SearchGoodsActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    //根据获取到用户本身的Shop状态来跳转
    private void openShop(){
        if(shop == null) {  //没有开店
            Intent intent1 = new Intent(context, OpenMyShopActivity.class);
            startActivityForResult(intent1, 1);
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }else {
            //已经申请开店，但是没有通过审核
            if(shop.getReviewType() == 1){
                Toast.makeText(context, "你的店铺正在紧急审核中", Toast.LENGTH_SHORT).show();
            }else if(shop.getReviewType() == 3){  //未通过审核
                Toast.makeText(context, "你的店铺未通过审核", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(context, EditMyShopActivity.class);
                intent1.putExtra("shop", shop);
                startActivity(intent1);
                ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {  //已经通过审核
                Intent intent1 = new Intent(context, MyShopActivity.class);
                startActivity(intent1);
                ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    }

    //根据获取到用户本身的Shop状态来跳转
    private void openInviteeShop(){
        if(shop == null) {  //没有开店
            Toast.makeText(context, "自己先开店才能邀请", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(context, OpenMyShopActivity.class);
            startActivity(intent1);
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }else {
            //已经申请开店，但是没有通过审核
            if(shop.getReviewType() == 1){
                Toast.makeText(context, "你的店铺正在紧急审核中，暂时不能邀请", Toast.LENGTH_SHORT).show();
            }else if(shop.getReviewType() == 3){  //未通过审核
                Toast.makeText(context, "你的店铺未通过审核", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(context, EditMyShopActivity.class);
                intent1.putExtra("shop", shop);
                startActivity(intent1);
                ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {  //已经通过审核
                startAnotherAty(InviteOpenShopActivity.class);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            shop = (Shop)data.getSerializableExtra("shop");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.gv_deadline_goods:
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("product", specialPriceProductList.get(i));
                startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.gv_guess_goods:
                Intent intent1 = new Intent(context, ProductDetailActivity.class);
                intent1.putExtra("product", youLoveProductList.get(i));
                startActivity(intent1);
                ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
        Intent intent = new Intent(context, aty);
        startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    /**
     *
     * 下拉刷新后，在这里重新获取数据，然后再次加载
     * */
    private void refreshHomePage(){
        loadData();
    }
}
