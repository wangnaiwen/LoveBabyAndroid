package com.wnw.lovebaby.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.TabAdapter;
import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.FindOrderByUserIdPresenter;
import com.wnw.lovebaby.view.fragment.TabOrder;
import com.wnw.lovebaby.view.viewInterface.IFindOrderByUserIdView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/16.
 */

public class MyOrderActivity extends FragmentActivity implements View.OnClickListener,
        IFindOrderByUserIdView{

    private TabLayout tabFragmentTitle ;           //定义TabLayout
    private ViewPager vpFragmentPager;             //定义viewPager
    private FragmentPagerAdapter fAdapter;         //定义adapter
    private ImageView back;                        //返回

    private List<Fragment> listFragment;           //定义要装fragment的列表
    private List<String> listTitle;                //tab名称列表

    private TabOrder tabOrder;               //所有订单
    private TabOrder tabBePay;                     //待支付
    private TabOrder tabBeSent;                   //待发货
    private TabOrder tabBeReceived;           //待收货
    private TabOrder tabBeEvaluated;         //待评价

    private int userId;                            //用户Id
    private FindOrderByUserIdPresenter findOrderByUserIdPresenter;

    private List<Order> orderList = new ArrayList<>();         //所有的订单
    private List<Order> bePayOrderList = new ArrayList<>();    //待付款
    private List<Order> beSentOrderList = new ArrayList<>();   //待发货
    private List<Order> beReceivedList = new ArrayList<>();    //待收货
    private List<Order> beEvaluatedList = new ArrayList<>();   //待评价

    private List<String> nameList = new ArrayList<>();
    private List<String> bePayNameList = new ArrayList<>();
    private List<String> beSentNameList = new ArrayList<>();
    private List<String> beReceivedNameList = new ArrayList<>();
    private List<String> beEvaluatedNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        getPosition();
        getData();
        initTab();
        initPresenter();
    }

    private void getData(){
        SharedPreferences sharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("id", 0);
    }

    private void initTab(){
        //初始化各fragment
        tabOrder = new TabOrder();
        tabBePay = new TabOrder();
        tabBeSent = new TabOrder();
        tabBeReceived = new TabOrder();
        tabBeEvaluated = new TabOrder();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPresenter();
    }

    private void initView(){
        back = (ImageView)findViewById(R.id.back_my_order);
        tabFragmentTitle = (TabLayout)findViewById(R.id.tl_title_my_order);
        vpFragmentPager = (ViewPager)findViewById(R.id.vp_my_order);

        back.setOnClickListener(this);

        //将fragment装进列表中
        listFragment = new ArrayList<>();
        listFragment.add(tabOrder);
        listFragment.add(tabBePay);
        listFragment.add(tabBeSent);
        listFragment.add(tabBeReceived);
        listFragment.add(tabBeEvaluated);

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        listTitle = new ArrayList<>();
        listTitle.add("全部订单");
        listTitle.add("待支付");
        listTitle.add("待发货");
        listTitle.add("待收货");
        listTitle.add("待评价");

        //设置TabLayout的模式
        tabFragmentTitle.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tabFragmentTitle.addTab(tabFragmentTitle.newTab().setText(listTitle.get(0)));
        tabFragmentTitle.addTab(tabFragmentTitle.newTab().setText(listTitle.get(1)));
        tabFragmentTitle.addTab(tabFragmentTitle.newTab().setText(listTitle.get(2)));
        tabFragmentTitle.addTab(tabFragmentTitle.newTab().setText(listTitle.get(3)));
        tabFragmentTitle.addTab(tabFragmentTitle.newTab().setText(listTitle.get(4)));

        fAdapter = new TabAdapter(getSupportFragmentManager(), listFragment, listTitle);

        //viewpager加载adapter
        vpFragmentPager.setAdapter(fAdapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tabFragmentTitle.setupWithViewPager(vpFragmentPager);
        //tab_FindFragment_title.set
        vpFragmentPager.setOffscreenPageLimit(0);

        //选中某一个Tab
        vpFragmentPager.setCurrentItem(position);
        tabFragmentTitle.getTabAt(position).select();
    }

    //选中的position
    private int position;
    private void getPosition(){
        Intent intent = getIntent();
        position = intent.getIntExtra("position", position);
    }

    private void initPresenter(){
        findOrderByUserIdPresenter = new FindOrderByUserIdPresenter(this,this);
    }

    //start presenter
    private void startPresenter(){
        if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "暂无网络",Toast.LENGTH_SHORT).show();
        }else{
            findOrderByUserIdPresenter.findOrderByUserId(userId);
        }
    }

    @Override
    public void showDialog() {
        showDialogs();
    }

    ProgressDialog dialog = null;
    private void showDialogs(){
        if(dialog == null){
            dialog = new ProgressDialog(this);
            dialog.setMessage("正在努力中...");
        }
        dialog.show();
    }

    private void dismissDialogs(){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void showOrdersByUserId(List<Order> orders, List<String> names) {

        if(orders != null){
            this.orderList = orders;
            this.nameList = names;
            bePayOrderList.clear();
            beSentOrderList.clear();
            beReceivedList.clear();
            beEvaluatedList.clear();

            bePayNameList.clear();
            beSentNameList.clear();
            beReceivedNameList.clear();
            beEvaluatedNameList.clear();
        }

        //对订单分类，并且加载到各个Tab中
        int length = orderList.size();
        for (int i = 0; i < length; i++){
            int type = orderList.get(i).getOrderType();
            if(type == 1){          //待付款
                bePayOrderList.add(orderList.get(i));
                bePayNameList.add(nameList.get(i));
            }else if(type == 2){    //待发货
                beSentOrderList.add(orderList.get(i));
                beSentNameList.add(nameList.get(i));
            }else if(type == 3){    //待收货
                beReceivedList.add(orderList.get(i));
                beReceivedNameList.add(nameList.get(i));
            }else if(type == 4){     //待评价
                beEvaluatedList.add(orderList.get(i));
                beEvaluatedNameList.add(nameList.get(i));
            }
        }

        //加载到各个Tab中
        tabOrder.setOrderList(orderList, nameList);
        tabBePay.setOrderList(bePayOrderList, bePayNameList);
        tabBeSent.setOrderList(beSentOrderList, beSentNameList);
        tabBeReceived.setOrderList(beReceivedList, beReceivedNameList);
        tabBeEvaluated.setOrderList(beEvaluatedList, beEvaluatedNameList);
        initView();
        dismissDialogs();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_my_order:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
