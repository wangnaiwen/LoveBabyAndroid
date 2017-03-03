package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.GoodsCoverAdapter;
import com.wnw.lovebaby.bean.GoodsCoverItem;
import com.wnw.lovebaby.view.costom.GoodsGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2016/12/13.
 */

public class TodayNewGoodsActivity extends Activity implements View.OnClickListener{

    private ImageView backTodayNewGoods;
    private GridView todayNewGoodsGv;

    /**
     * 今天上新内容
     * */
    private int imagesIcon[] = {R.mipmap.n1, R.mipmap.m2, R.mipmap.m3, R.mipmap.m4};
    private String titles[] = {"奶粉1","奶粉2","奶粉3","奶粉4"};
    private int prices[] = {10100,10199,10388,10409};
    private GoodsCoverAdapter goodsCoverAdapter;
    private List<GoodsCoverItem> goodsCoverItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_new_goods);
        initView();
    }

    private void initView(){
        backTodayNewGoods = (ImageView)findViewById(R.id.back_today_new_goods);
        todayNewGoodsGv = (GridView)findViewById(R.id.gv_today_new_goods);

        backTodayNewGoods.setOnClickListener(this);

        buildGvData();
        goodsCoverAdapter = new GoodsCoverAdapter(this, goodsCoverItemList);
        todayNewGoodsGv.setAdapter(goodsCoverAdapter);
        todayNewGoodsGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(TodayNewGoodsActivity.this, "item="+i, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void buildGvData(){
        goodsCoverItemList = new ArrayList<>();
        int count = Math.min(Math.min(imagesIcon.length, titles.length), Math.min(imagesIcon.length, prices.length));
        for(int i = 0; i < count; i++){
            GoodsCoverItem item = new GoodsCoverItem();
            item.setImage(imagesIcon[i]);
            item.setTitle(titles[i]);
            item.setPrice(prices[i]);
            goodsCoverItemList.add(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_today_new_goods:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            default:

                break;
        }
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

}
