package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DisplayContext;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.domain.Shop;
import com.wnw.lovebaby.presenter.FindIncomeByInviteePresenter;
import com.wnw.lovebaby.presenter.FindIncomeByShopIdPresenter;
import com.wnw.lovebaby.presenter.FindShopByUserIdPresenter;
import com.wnw.lovebaby.util.TypeConverters;
import com.wnw.lovebaby.view.viewInterface.IFindIncomeByInviteeView;
import com.wnw.lovebaby.view.viewInterface.IFindIncomeByShopIdView;
import com.wnw.lovebaby.view.viewInterface.IFindShopByUserIdView;

/**
 * Created by wnw on 2017/4/10.
 */

public class MyShopActivity extends Activity implements View.OnClickListener ,
        IFindIncomeByShopIdView, IFindIncomeByInviteeView, IFindShopByUserIdView{
    private ImageView backMyShop;
    private ImageView shareMyShop;
    private TextView allIncomeTv;
    private RelativeLayout shopOrdersRl;
    private TextView shopIncomeTv;
    private RelativeLayout myInviteShopOrdersRl;
    private TextView myInviteShopIncomeTv;
    private TextView shopNameTv;

    private FindShopByUserIdPresenter findShopByUserIdPresenter;
    private FindIncomeByShopIdPresenter findIncomeByShopIdPresenter;
    private FindIncomeByInviteePresenter findIncomeByInviteePresenter;

    private int userId;
    private Shop shop;

    private int myShopIncome;
    private int myInviteeShopIncome;
    private int allIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        getUserId();
        initView();
        initPresenter();
        startFindShopByUserId();
        startFindIncome();
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
        shopNameTv = (TextView)findViewById(R.id.tv_shop_name);

        backMyShop.setOnClickListener(this);
        shareMyShop.setOnClickListener(this);
        shopOrdersRl.setOnClickListener(this);
        myInviteShopOrdersRl.setOnClickListener(this);
    }

    private void getUserId(){
        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        userId = sharedPreferences.getInt("id", 0);
    }

    private void initPresenter(){
        findShopByUserIdPresenter = new FindShopByUserIdPresenter(this,this);
        findIncomeByInviteePresenter = new FindIncomeByInviteePresenter(this,this);
        findIncomeByShopIdPresenter = new FindIncomeByShopIdPresenter(this, this);
    }

    private void startFindShopByUserId(){
        findShopByUserIdPresenter.findShopByUserId(userId);
    }

    private void startFindIncome(){
        findIncomeByInviteePresenter.load(userId);
    }

    private void startFindIncomeByShopId(){
        findIncomeByShopIdPresenter.load(shop.getId());
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
    public void showShopsByUserId(Shop shop) {
        if(shop != null){
            this.shop = shop;
            startFindIncomeByShopId();
            shopNameTv.setText(shop.getName());
        }else {
            //找不到
            Toast.makeText(this, "找不到店铺", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showIncomeByInvitee(int income) {
        dismissDialogs();
        myInviteeShopIncome = income;
        allIncome = myShopIncome + myInviteeShopIncome;
        myInviteShopIncomeTv.setText(TypeConverters.IntConvertToString(myInviteeShopIncome));
        allIncomeTv.setText(TypeConverters.IntConvertToString(allIncome));
    }

    @Override
    public void showIncomeByShopId(int income) {
        dismissDialogs();
        myShopIncome = income;
        allIncome = myShopIncome + myInviteeShopIncome;
        shopIncomeTv.setText(TypeConverters.IntConvertToString(myShopIncome));
        allIncomeTv.setText(TypeConverters.IntConvertToString(allIncome));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_my_shop:
                //返回键
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.img_share_my_shop:
                //分享键
                break;
            case R.id.rl_shop_orders:
                //商店订单
                Intent intent = new Intent(this, ShopOrderActivity.class);
                intent.putExtra("shopId", shop.getId());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.rl_invite_shop_orders:
                //邀请的商店的订单
                Intent intent1 = new Intent(this, InviteeShopOrderActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        findIncomeByInviteePresenter.setView(null);
        findIncomeByShopIdPresenter.setView(null);
        findShopByUserIdPresenter.setFindShopByUserIdView(null);
    }
}
