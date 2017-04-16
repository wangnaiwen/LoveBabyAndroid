package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wnw.lovebaby.R;

/**
 * Created by wnw on 2017/4/10.
 */

public class MyShopActivity extends Activity implements View.OnClickListener{
    private ImageView backMyShop;
    private ImageView shareMyShop;
    private TextView allIncomeTv;
    private RelativeLayout shopOrdersRl;
    private TextView shopIncomeTv;
    private RelativeLayout myInviteShopOrdersRl;
    private TextView myInviteShopIncomeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        initView();
    }

    //init view
    private void initView(){
        backMyShop = (ImageView)findViewById(R.id.back_my_shop);
        shareMyShop = (ImageView)findViewById(R.id.img_share_my_shop);
        allIncomeTv = (TextView)findViewById(R.id.tv_my_shop_all_income);
        shopOrdersRl = (RelativeLayout)findViewById(R.id.rl_shop_orders);
        shopIncomeTv = (TextView)findViewById(R.id.tv_shop_income);
        myInviteShopOrdersRl = (RelativeLayout)findViewById(R.id.rl_invite_shop_orders);
        myInviteShopIncomeTv = (TextView)findViewById(R.id.tv_invite_shop_income);

        backMyShop.setOnClickListener(this);
        shareMyShop.setOnClickListener(this);
        shopOrdersRl.setOnClickListener(this);
        myInviteShopOrdersRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_my_shop:
                //返回键
                break;
            case R.id.img_share_my_shop:
                //分享键
                break;
            case R.id.rl_shop_orders:
                //商店订单
                break;
            case R.id.rl_invite_shop_orders:
                //邀请的商店的订单
                break;
            default:
                break;
        }
    }
}
