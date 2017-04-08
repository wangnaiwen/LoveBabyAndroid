package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.OrderLvAdapter;
import com.wnw.lovebaby.domain.ReceAddress;
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
    private TextView orderReceiver;
    private TextView orderRecePhone;
    private TextView orderReceAddress;

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
        orderReceiver = (TextView)findViewById(R.id.order_tv_receiver);
        orderRecePhone = (TextView)findViewById(R.id.order_tv_phone);
        orderReceAddress = (TextView)findViewById(R.id.order_tv_address);

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
            shoppingCarItem.setGoodsImg("http://119.29.182.235:8080/ssh_contacts/images/join_us.jpg");
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
                startAddressListActivity();
                break;
            case R.id.change_address:
                startAddressListActivity();
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

    /**
     * start the address list activity
     * */
    public static int REQUEST_CODE = 1;
    private void startAddressListActivity(){
        Intent intent = new Intent(this, AddressListActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private ReceAddress receAddress ;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == AddressListActivity.RESULT_CODE){
            Intent intent = data;

            receAddress = (ReceAddress)intent.getSerializableExtra("receAddress_data");
            /*receAddress.setId(intent.getIntExtra("id", Integer.MIN_VALUE));
            receAddress.setUserId(intent.getIntExtra("userId", Integer.MIN_VALUE));
            receAddress.setReceiver(intent.getStringExtra("receiver"));
            receAddress.setPhone(intent.getStringExtra("phone"));
            receAddress.setProvince(intent.getStringExtra("province"));
            receAddress.setCity(intent.getStringExtra("city"));
            receAddress.setDistrict(intent.getStringExtra("district"));
            receAddress.setDetailAddress(intent.getStringExtra("detailAddress"));
            receAddress.setPostcode(intent.getIntExtra("postcode", Integer.MIN_VALUE));*/
            if(receAddress != null){
                pickAddress.setVisibility(View.GONE);
                changeAddress.setVisibility(View.VISIBLE);
                orderReceiver.setText(receAddress.getReceiver());
                orderRecePhone.setText(receAddress.getPhone());
                orderReceAddress.setText(receAddress.getProvince()+" " +receAddress.getCity() + " "
                        + receAddress.getCounty() + " " + receAddress.getDetailAddress());
            }
        }
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
