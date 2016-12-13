package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.OrderLvAdapter;
import com.wnw.lovebaby.bean.ShoppingCarItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2016/12/11.
 */

public class OrderConfirmationActivity extends Activity implements View.OnClickListener{
    private ListView orderListView;
    private LinearLayout changeAddress;
    private LinearLayout pickAddress;
    private EditText remarkEd;
    private ImageView backOrder;
    private TextView orderSumPrice;
    private TextView orderPay;

    private OrderLvAdapter orderLvAdapter;
    private List<ShoppingCarItem> shoppingCarItemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        initView();
    }

    private void initView(){
        orderListView = (ListView)findViewById(R.id.order_lv);
        pickAddress = (LinearLayout)findViewById(R.id.pick_address_normal);
        changeAddress = (LinearLayout)findViewById(R.id.change_address);
        remarkEd = (EditText)findViewById(R.id.order_remark);
        backOrder = (ImageView)findViewById(R.id.back_order);
        orderSumPrice = (TextView)findViewById(R.id.order_sum_price);
        orderPay = (TextView)findViewById(R.id.order_to_pay);

        pickAddress.setOnClickListener(this);
        changeAddress.setOnClickListener(this);
        backOrder.setOnClickListener(this);
        orderPay.setOnClickListener(this);

        setAdapter();
    }

    private void setAdapter(){
        shoppingCarItemList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            ShoppingCarItem shoppingCarItem = new ShoppingCarItem();
            shoppingCarItem.setId(i);
            shoppingCarItem.setChecked(true);
            shoppingCarItem.setGoodsImg(R.mipmap.b1);
            shoppingCarItem.setGoodsNum(1);
            shoppingCarItem.setGoodsPrice(400000);
            shoppingCarItem.setGoodsTitle("特价宝宝，冬天免费出租，免费暖被窝，免费倒洗脚水");
            shoppingCarItemList.add(shoppingCarItem);
        }
        orderLvAdapter = new OrderLvAdapter(this, shoppingCarItemList);
        orderListView.setAdapter(orderLvAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pick_address_normal:
                Intent intent = new Intent(this, AddressListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.change_address:
                Intent intent1 = new Intent(this, AddressListActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.back_order:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.order_to_pay:

                break;
            default:
                break;
        }
    }
}
