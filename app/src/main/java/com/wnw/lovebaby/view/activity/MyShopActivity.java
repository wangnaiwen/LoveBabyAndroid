package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DisplayContext;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.config.MyKeys;
import com.wnw.lovebaby.domain.Shop;
import com.wnw.lovebaby.presenter.FindIncomeByInviteePresenter;
import com.wnw.lovebaby.presenter.FindIncomeByShopIdPresenter;
import com.wnw.lovebaby.presenter.FindShopByUserIdPresenter;
import com.wnw.lovebaby.presenter.FindWithdrawMoneyByUserIdPresenter;
import com.wnw.lovebaby.util.TypeConverters;
import com.wnw.lovebaby.view.viewInterface.IFindIncomeByInviteeView;
import com.wnw.lovebaby.view.viewInterface.IFindIncomeByShopIdView;
import com.wnw.lovebaby.view.viewInterface.IFindShopByUserIdView;
import com.wnw.lovebaby.view.viewInterface.IFindWithdrawMoneyByUserIdView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/10.
 */

public class MyShopActivity extends Activity implements View.OnClickListener ,
        IFindIncomeByShopIdView, IFindIncomeByInviteeView, IFindShopByUserIdView, IFindWithdrawMoneyByUserIdView{
    private ImageView backMyShop;
    private ImageView shareMyShop;
    private TextView allIncomeTv;
    private RelativeLayout shopOrdersRl;
    private TextView shopIncomeTv;
    private RelativeLayout myInviteShopOrdersRl;
    private TextView myInviteShopIncomeTv;
    private TextView shopNameTv;

    private RelativeLayout shopBalanceRl; //余额点击
    private TextView shopBalanceTv;       //余额
    private TextView withdrawMoneyTv;     //提现额度

    private FindShopByUserIdPresenter findShopByUserIdPresenter;
    private FindIncomeByShopIdPresenter findIncomeByShopIdPresenter;
    private FindIncomeByInviteePresenter findIncomeByInviteePresenter;
    private FindWithdrawMoneyByUserIdPresenter findWithdrawMoneyByUserIdPresenter;

    private int userId;
    private Shop shop;

    private int myShopIncome;             //店铺收入
    private int myInviteeShopIncome;      //邀请的店铺的收入
    private int allIncome;                //总收入
    private int withdraw;                 //已经提现的数量
    private int balance;                  //店铺余额
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        getUserId();
        initAPI();
        initView();
        initPresenter();
        startFindShopByUserId();
        startFindIncome();
        startFindWithdraw();
    }

    private void initAPI(){
        //微信分享初始化
        api = WXAPIFactory.createWXAPI(this, MyKeys.AppID, true);
        api.registerApp(MyKeys.AppID);
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

        shopBalanceRl = (RelativeLayout)findViewById(R.id.rl_shop_balance);
        shopBalanceTv = (TextView)findViewById(R.id.tv_shop_balance);
        withdrawMoneyTv = (TextView)findViewById(R.id.tv_withdraw_money);

        shopBalanceRl.setOnClickListener(this);

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
        findWithdrawMoneyByUserIdPresenter = new FindWithdrawMoneyByUserIdPresenter(this, this);
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

    private void startFindWithdraw(){
        findWithdrawMoneyByUserIdPresenter.findWithdrawMoneyByUserId(userId);
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
        myInviteeShopIncome = income * 2/100;    //邀请获得的，可以拿到2%的提成
        allIncome = myShopIncome + myInviteeShopIncome ;
        myInviteShopIncomeTv.setText(TypeConverters.IntConvertToString(myInviteeShopIncome));
        allIncomeTv.setText(TypeConverters.IntConvertToString(allIncome));

        balance = allIncome - withdraw;
        shopBalanceTv.setText(TypeConverters.IntConvertToString(balance));
    }

    @Override
    public void showIncomeByShopId(int income) {
        dismissDialogs();
        myShopIncome = income * 8/100;       //拿到8%的提成
        allIncome = myShopIncome + myInviteeShopIncome;
        shopIncomeTv.setText(TypeConverters.IntConvertToString(myShopIncome));
        allIncomeTv.setText(TypeConverters.IntConvertToString(allIncome));

        balance = allIncome - withdraw;
        shopBalanceTv.setText(TypeConverters.IntConvertToString(balance));
    }

    @Override
    public void showWithdrawMoney(int money) {
        dismissDialogs();
        withdraw = money;
        balance = allIncome - withdraw;
        shopBalanceTv.setText(TypeConverters.IntConvertToString(balance));
        withdrawMoneyTv.setText(TypeConverters.IntConvertToString(withdraw));

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
                showShareDialog();
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
            case R.id.rl_shop_balance:
                //店铺余额被点击
                Intent intent2 = new Intent(this, WithdrawActivity.class);
                intent2.putExtra("userId", userId);
                intent2.putExtra("balance", balance);
                startActivityForResult(intent2, 1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK){
            withdraw = withdraw + balance - data.getIntExtra("balance", 0);
            balance = data.getIntExtra("balance", 0);
            withdrawMoneyTv.setText(TypeConverters.IntConvertToString(withdraw));
            shopBalanceTv.setText(TypeConverters.IntConvertToString(balance));
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


    AlertDialog shareDialog;
    private void showShareDialog(){
        final List<String> list = new ArrayList<>();
        String[] sharePlatform = new String[]{"微信","朋友圈", "QQ好友"};
        for(int  i = 0; i < sharePlatform.length; i++){
            list.add(sharePlatform[i]);
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        final LinearLayout linearLayout =(LinearLayout) inflater.inflate(R.layout.dialog_lv_platform_share,null);
        ListView listView = (ListView)linearLayout.findViewById(R.id.lv_dialog);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.dialog_lv_item, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openShare(i);
            }
        });

        shareDialog = new AlertDialog.Builder(this).create();
        shareDialog.setView(linearLayout);
        shareDialog.show();
    }

    //根据用户的选择来打开相应的
    private void openShare(int i){
        if(shareDialog.isShowing()){
            shareDialog.dismiss();
        }

        if(i == 0){
            shareToWxFriend();
        }else if(i == 2){
            shareToQQFriend();
        }else if(i == 1){
            shareToTimeLine();
        }
    }

    /**
     * 分享到QQ好友
     *
     */
    private void shareToQQFriend() {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
        intent.setComponent(componentName);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_TEXT, "我在爱婴粑粑这个母婴APP上面开店了，谢谢各位朋友支持，我的店铺购买码是："+userId);
        startActivity(intent);
    }

    /**
     * 分享信息到朋友
     *
     */
    private void shareToWxFriend() {
        String text = "我在爱婴粑粑这个母婴APP上面开店了，谢谢各位朋友支持，我的店铺购买码是："+userId;

        //初始化一个网页
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }


    private void shareToTimeLine() {

        String text = "我在爱婴粑粑这个母婴APP上面开店了，谢谢各位朋友支持，我的店铺购买码是："+userId;

        //初始化一个网页
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }
}
