package com.wnw.lovebaby.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.TabAdapter;
import com.wnw.lovebaby.view.fragment.TabAllOrder;
import com.wnw.lovebaby.view.fragment.TabBeEvaluated;
import com.wnw.lovebaby.view.fragment.TabBePay;
import com.wnw.lovebaby.view.fragment.TabBeReceived;
import com.wnw.lovebaby.view.fragment.TabBeSent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/16.
 */

public class MyOrderActivity extends FragmentActivity implements View.OnClickListener{

    private TabLayout tabFragmentTitle ;           //定义TabLayout
    private ViewPager vpFragmentPager;             //定义viewPager
    private FragmentPagerAdapter fAdapter;         //定义adapter
    private ImageView back;                        //返回

    private List<Fragment> listFragment;           //定义要装fragment的列表
    private List<String> listTitle;                //tab名称列表

    private TabAllOrder tabAllOrder;               //所有订单
    private TabBePay tabBePay;                     //待支付
    private TabBeSent tabBeSent;                   //待发货
    private TabBeReceived tabBeReceived;           //待收货
    private TabBeEvaluated tabBeEvaluated;         //待评价

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        initView();
        getPosition();
    }

    private void initView(){
        back = (ImageView)findViewById(R.id.back_my_order);
        tabFragmentTitle = (TabLayout)findViewById(R.id.tl_title_my_order);
        vpFragmentPager = (ViewPager)findViewById(R.id.vp_my_order);

        back.setOnClickListener(this);

        //初始化各fragment
        tabAllOrder = new TabAllOrder();
        tabBePay = new TabBePay();
        tabBeSent = new TabBeSent();
        tabBeReceived = new TabBeReceived();
        tabBeEvaluated = new TabBeEvaluated();

        //将fragment装进列表中
        listFragment = new ArrayList<>();
        listFragment.add(tabAllOrder);
        listFragment.add(tabBePay);
        listFragment.add(tabBeSent);
        listFragment.add(tabBeReceived);
        listFragment.add(tabBeEvaluated);

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        listTitle = new ArrayList<>();
        listTitle.add("全部订单");
        listTitle.add("待支付");
        listTitle.add("代发货");
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
    }

    //选中的position
    private int position;
    private void getPosition(){
        Intent intent = getIntent();
        position = intent.getIntExtra("position", position);
        //选中某一个Tab
        vpFragmentPager.setCurrentItem(position);
        tabFragmentTitle.getTabAt(position).select();
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
