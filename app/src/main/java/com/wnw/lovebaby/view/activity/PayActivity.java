package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.FindWalletMoneyByUserIdPresenter;
import com.wnw.lovebaby.presenter.SubWalletMoneyPresenter;
import com.wnw.lovebaby.presenter.UpdateOrderPresenter;
import com.wnw.lovebaby.presenter.ValiteWalletPresenter;
import com.wnw.lovebaby.util.TypeConverters;
import com.wnw.lovebaby.view.viewInterface.IFindWalletMoneyByUserIdView;
import com.wnw.lovebaby.view.viewInterface.ISubWalletMoneyView;
import com.wnw.lovebaby.view.viewInterface.IUpdateOrderView;
import com.wnw.lovebaby.view.viewInterface.IValiteWalletView;

import java.text.SimpleDateFormat;

import c.b.BP;
import c.b.PListener;
import c.b.QListener;

/**
 * Created by wnw on 2017/4/13.
 */

public class PayActivity extends Activity implements View.OnClickListener,
        IValiteWalletView, IFindWalletMoneyByUserIdView, ISubWalletMoneyView,IUpdateOrderView{

    private ImageView back;
    private TextView payNumberTv;
    private TextView surePayTv;
    private EditText passwordEt;
    private TextView alipayTv;

    private int userId;

    private int tag = 0;
    private Order order;
    private int sumPrice;

    /**
     * 1. 验证密码
     * 2. 查询余额
     * 3. 修改余额
     * 4. 修改订单状态
     * */
    private ValiteWalletPresenter valiteWalletPresenter;
    private FindWalletMoneyByUserIdPresenter findWalletMoneyByUserIdPresenter;
    private SubWalletMoneyPresenter subWalletMoneyPresenter;
    private UpdateOrderPresenter updateOrderPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initView();
        getData();
        initPresenter();
    }

    //get data from order confirmation activity
    private void getData(){
        Intent intent = getIntent();
        tag = intent.getIntExtra("tag",0);
        order = (Order)intent.getSerializableExtra("order");
        sumPrice = intent.getIntExtra("sumPrice",0);
        payNumberTv.setText(TypeConverters.IntConvertToString(sumPrice));

        SharedPreferences s = getSharedPreferences("account", MODE_PRIVATE);
        userId = s.getInt("id", 0);
    }

    //init view
    private void initView(){
        back = (ImageView)findViewById(R.id.back_pay);
        payNumberTv = (TextView)findViewById(R.id.tv_pay_number);
        surePayTv = (TextView)findViewById(R.id.tv_sure_pay);
        passwordEt = (EditText)findViewById(R.id.et_pay_password);
        alipayTv = (TextView)findViewById(R.id.tv_alipay);

        back.setOnClickListener(this);
        surePayTv.setOnClickListener(this);
        alipayTv.setOnClickListener(this);
    }

    //inti presenter
    private void initPresenter(){
        valiteWalletPresenter = new ValiteWalletPresenter(this, this);
        findWalletMoneyByUserIdPresenter = new FindWalletMoneyByUserIdPresenter(this, this);
        subWalletMoneyPresenter = new SubWalletMoneyPresenter(this, this);
        updateOrderPresenter = new UpdateOrderPresenter(this, this);
    }

    //开始验证密码
    private void startValitePassword(String password){
        valiteWalletPresenter.valiteWallet(userId, password);
    }

    //开始查余额
    private void startFindWalletMoney(){
        findWalletMoneyByUserIdPresenter.findWalletMoneyByUserId(userId);
    }

    //开始修改余额
    private void startSubWalletMoney(){
        subWalletMoneyPresenter.subWalletMoney(userId, sumPrice);
    }

    //开始更改订单状态
    private void startUpdateOrder(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        java.util.Date d = new java.util.Date();
        String str = sdf.format(d);
        order.setPayTime(str);
        order.setOrderType(2);
        updateOrderPresenter.updateOrder(order);
    }

    @Override
    public void showDialog() {
        showDialogs();
    }
    ProgressDialog dialog = null;
    private void showDialogs(){
        if(dialog == null){
            dialog = new ProgressDialog(this);
            dialog.setMessage("订单正在提交中...");
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
    public void valiteWalletResult(boolean isSuccess) {
        if (isSuccess){
            startFindWalletMoney();
        }else {
            //验证密码不对
            Toast.makeText(this, "密码不正确", Toast.LENGTH_SHORT).show();
            dismissDialogs();
        }
    }

    @Override
    public void showFindWalletMoneyByUserIdResult(int result) {
        if(sumPrice > result){
            //余额不足
            dismissDialogs();
            Toast.makeText(this, "余额不足", Toast.LENGTH_SHORT).show();
        }else{
            //开始扣钱
            startSubWalletMoney();
        }
    }

    @Override
    public void subWalletMoneyResult(boolean isSuccess) {
        if(isSuccess){
            //更新订单状态
            startUpdateOrder();
        }else {
            //扣钱发生错误
            dismissDialogs();
            Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showUpdateOrderResult(boolean isSuccess) {
        dismissDialogs();
        if(isSuccess){
            //支付成功
            if (tag == 1){
                //返回订单列表
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
            }
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else{
            //支付失败，但是钱已经扣了，再改回来
            Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
        }
    }


    private String payOrderId ;  //支付宝支付的订单号
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_pay:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.tv_sure_pay:
                String password = passwordEt.getText().toString().trim();
                if(password.isEmpty()){
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else {
                    if(password.length() != 6){
                        Toast.makeText(this, "密码长度为6", Toast.LENGTH_SHORT).show();
                    }else{
                        if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
                            Toast.makeText(this, "当前无网络", Toast.LENGTH_SHORT).show();
                        }else{
                            startValitePassword(password);
                        }
                    }
                }
                break;
            case R.id.tv_alipay:
                startAlipay();
                break;
        }
    }

    private void startAlipay(){
        double price = ((double)sumPrice)/100;
        BP.pay("母婴产品", "宝宝的产品", price, true, new PListener() {
            @Override
            public void orderId(String s) {
                payOrderId = s;
            }

            @Override
            public void succeed() {
                findPayResult();
            }

            @Override
            public void fail(int i, String s) {
                Log.d("code", i + "  " + s);
            }

            @Override
            public void unknow() {
                Log.d("code", "unknow");
            }
        });
    }

    private void findPayResult(){
        BP.query(payOrderId, new QListener() {
            @Override
            public void succeed(String s) {
                //说明支付成功
                startUpdateOrder();
            }

            @Override
            public void fail(int i, String s) {
                Log.d("code", i + "  " + s);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        valiteWalletPresenter.setValiteWalletView(null);
        findWalletMoneyByUserIdPresenter.setFindWalletMoneyByUserIdView(null);
        subWalletMoneyPresenter.setSubWalletMoneyView(null);
        updateOrderPresenter.setUpdateOrderView(null);
    }
}
