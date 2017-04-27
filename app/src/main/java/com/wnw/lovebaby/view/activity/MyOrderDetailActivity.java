package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.OrderLvAdapter;
import com.wnw.lovebaby.bean.ShoppingCarItem;
import com.wnw.lovebaby.domain.Deal;
import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.domain.Pr;
import com.wnw.lovebaby.domain.Product;
import com.wnw.lovebaby.domain.ReceAddress;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.FindDealByOrderIdPresenter;
import com.wnw.lovebaby.presenter.FindProductByIdPresenter;
import com.wnw.lovebaby.presenter.FindReceAddressByIdPresenter;
import com.wnw.lovebaby.presenter.UpdateOrderPresenter;
import com.wnw.lovebaby.util.OrderTypeConvert;
import com.wnw.lovebaby.util.TimeConvert;
import com.wnw.lovebaby.util.TypeConverters;
import com.wnw.lovebaby.view.viewInterface.IFindDealByOrderIdView;
import com.wnw.lovebaby.view.viewInterface.IFindProductByIdView;
import com.wnw.lovebaby.view.viewInterface.IFindReceAddressByIdView;
import com.wnw.lovebaby.view.viewInterface.IUpdateOrderView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by wnw on 2017/4/17.
 */

public class MyOrderDetailActivity extends Activity implements View.OnClickListener,
        AdapterView.OnItemClickListener, IUpdateOrderView, IFindDealByOrderIdView,
        IFindReceAddressByIdView, IFindProductByIdView{

    private ImageView back;           //返回键
    private TextView orderTypeTv;     //订单状态
    private TextView orderNumberTv;   //订单号
    private TextView createTimeTv;    //创建时间
    private TextView payTimeTv;       //付款时间
    private TextView finishTimeTv;    //完成时间
    private TextView nameTv;          //收货人姓名
    private TextView phoneTv;         //收货人联系方式
    private TextView addressTv;       //收货人地址
    private ListView dealLv;          //交易列表
    private TextView sumPriceTv;      //总价
    private TextView payTv;           //支付
    private TextView confirmReceiptTv;//确认收货
    private TextView submitEvaluatioinTv; //立即评价

    private Order order;             //当前的Order
    private List<Deal> dealList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();
    private List<ShoppingCarItem> shoppingCarItemList = new ArrayList<>();

    private FindDealByOrderIdPresenter findDealByOrderIdPresenter;
    private UpdateOrderPresenter updateOrderPresenter;
    private FindReceAddressByIdPresenter findReceAddressByIdPresenter;
    private FindProductByIdPresenter findProductByIdPresenter;

    private OrderLvAdapter orderLvAdapter;

    private int sumPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_detail);
        getOrder();
        initPresenter();
        startFindDeals();
        startFindAddress();
        initView();
    }

    private void getOrder(){
        Intent intent = getIntent();
        order = (Order)intent.getSerializableExtra("order");
    }

    private void initView(){
        back = (ImageView)findViewById(R.id.back_my_order_detail);
        orderTypeTv = (TextView)findViewById(R.id.tv_order_type);
        orderNumberTv = (TextView)findViewById(R.id.tv_order_number);
        createTimeTv = (TextView)findViewById(R.id.tv_order_create_time);
        payTimeTv = (TextView)findViewById(R.id.tv_order_pay_time);
        finishTimeTv = (TextView)findViewById(R.id.tv_order_finish_time);
        nameTv = (TextView)findViewById(R.id.tv_order_name);
        phoneTv = (TextView)findViewById(R.id.tv_order_phone);
        addressTv = (TextView)findViewById(R.id.tv_order_address);
        sumPriceTv = (TextView)findViewById(R.id.tv_order_all_price);
        payTv = (TextView)findViewById(R.id.tv_order_pay);
        dealLv = (ListView) findViewById(R.id.lv_order_deal);
        confirmReceiptTv = (TextView)findViewById(R.id.tv_confirm_receipt);
        submitEvaluatioinTv = (TextView)findViewById(R.id.tv_submit_evaluation);

        back.setOnClickListener(this);
        payTv.setOnClickListener(this);
        confirmReceiptTv.setOnClickListener(this);
        dealLv.setOnItemClickListener(this);
        submitEvaluatioinTv.setOnClickListener(this);

        //设初始值
        int orderType = order.getOrderType();

        orderTypeTv.setText(OrderTypeConvert.getStringType(orderType));
        orderNumberTv.setText("订单号:"+order.getOrderNumber());
        createTimeTv.setText("创建时间:"+ TimeConvert.getTime(order.getCreateTime()));

        if(orderType == 1){
            payTimeTv.setVisibility(View.GONE);
            finishTimeTv.setVisibility(View.GONE);
            payTv.setVisibility(View.VISIBLE);
        }else if(orderType == 2){
            payTimeTv.setText("付款时间:"+TimeConvert.getTime(order.getPayTime()));
            finishTimeTv.setVisibility(View.GONE);
            payTv.setVisibility(View.GONE);
        }else if(orderType == 3){
            payTimeTv.setText("付款时间:"+TimeConvert.getTime(order.getPayTime()));
            finishTimeTv.setVisibility(View.GONE);
            payTv.setVisibility(View.GONE);
            confirmReceiptTv.setVisibility(View.VISIBLE);
        }else if(orderType == 4){
            payTimeTv.setText("付款时间:"+TimeConvert.getTime(order.getPayTime()));
            finishTimeTv.setText("完成时间:"+TimeConvert.getTime(order.getFinishTime()));
            payTv.setVisibility(View.GONE);
            submitEvaluatioinTv.setVisibility(View.VISIBLE);
        }else if (orderType == 5){
            payTimeTv.setText("付款时间:"+TimeConvert.getTime(order.getPayTime()));
            finishTimeTv.setText("完成时间:"+TimeConvert.getTime(order.getFinishTime()));
            payTv.setVisibility(View.GONE);
        }
    }

    private void initPresenter(){
        findDealByOrderIdPresenter = new FindDealByOrderIdPresenter(this,this);
        updateOrderPresenter = new UpdateOrderPresenter(this,this);
        findReceAddressByIdPresenter = new FindReceAddressByIdPresenter(this, this);
        findProductByIdPresenter = new FindProductByIdPresenter(this,this);
    }

    private void startFindDeals(){
        findDealByOrderIdPresenter.findDealByOrderId(order.getId());
    }

    private void startUpdateOrder(){
        if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "当前无网络", Toast.LENGTH_SHORT).show();
        }else{
            updateOrderPresenter.updateOrder(order);
        }
    }

    private void startFindAddress(){
        findReceAddressByIdPresenter.findReceAddresById(order.getAddressId());
    }

    private int position = 0;
    private void startFindProduct(){
        findProductByIdPresenter.findProductById(dealList.get(position).getProductId());
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
    public void showDealsByOrderId(List<Deal> deals) {
        if(deals == null){
            Toast.makeText(this, "加载不出来", Toast.LENGTH_SHORT).show();
        }else{
            this.dealList = deals;
            startFindProduct();
        }
    }

    @Override
    public void showProduct(Product product) {
        if(product == null){
            Toast.makeText(this, "加载不出来", Toast.LENGTH_SHORT).show();
        }else{
            productList.add(product);
            position ++;
            if(position != dealList.size()){
                startFindProduct();
            }else{
                //已经全部查出来了，开始组装ShoppingCatItem，并且设置Adapter
                setAdapter();
                dismissDialogs();
            }
        }
    }

    @Override
    public void showUpdateOrderResult(boolean isSuccess) {
        dismissDialogs();
        if(isSuccess){
            Toast.makeText(this, "收货成功", Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else{
            Toast.makeText(this, "收货失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showReceAddressFindById(ReceAddress address) {
        if(address != null){
            nameTv.setText(address.getReceiver());
            phoneTv.setText(address.getPhone());
            addressTv.setText(address.getProvince()+address.getCity()+address.getCounty()+address.getDetailAddress());
        }else {
            Toast.makeText(this, "地址加载不出来", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter(){
        if(dealList.size() != productList.size()){
            Toast.makeText(this, "加载不出来", Toast.LENGTH_SHORT).show();
        }else{
            int length = dealList.size();
            for(int i = 0; i < length; i++){
                ShoppingCarItem shoppingCarItem = new ShoppingCarItem();
                shoppingCarItem.setProductId(productList.get(i).getId());
                shoppingCarItem.setGoodsNum(dealList.get(i).getProductCount());
                shoppingCarItem.setGoodsImg(productList.get(i).getCoverImg());
                shoppingCarItem.setGoodsPrice((int)productList.get(i).getRetailPrice());
                shoppingCarItem.setGoodsTitle(productList.get(i).getName());

                shoppingCarItemList.add(shoppingCarItem);

                sumPrice = sumPrice + (int)dealList.get(i).getSumPrice();
            }

            orderLvAdapter = new OrderLvAdapter(this, shoppingCarItemList);
            dealLv.setAdapter(orderLvAdapter);
            sumPriceTv.setText("总价格:" + TypeConverters.IntConvertToString(sumPrice));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tv_order_pay:
                Intent intent = new Intent(this, PayActivity.class);
                intent.putExtra("tag",1);
                intent.putExtra("order", order);
                intent.putExtra("sumPrice", sumPrice);
                startActivityForResult(intent,1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.back_my_order_detail:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.tv_confirm_receipt:
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                java.util.Date d = new java.util.Date();
                String str = sdf.format(d);
                order.setFinishTime(str);
                order.setOrderType(4);
                startUpdateOrder();
                break;
            case R.id.tv_submit_evaluation:
                //跳转到评价页面
                Intent intent1 = new Intent(this, InsertPrActivity.class);
                intent1.putExtra("shoppingCarItemList", (Serializable)shoppingCarItemList);
                intent1.putExtra("dealList", (Serializable)dealList);
                intent1.putExtra("order", order);
                startActivityForResult(intent1, 2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK){  //说明支付成功
            finish();
        }else if(requestCode == 2 && resultCode == RESULT_OK){
            boolean isSuccess = data.getBooleanExtra("isSuccess", false);
            if(isSuccess){
                submitEvaluatioinTv.setVisibility(View.GONE);
                orderTypeTv.setText(OrderTypeConvert.getStringType(5));
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.lv_order_deal:
                Intent intent = new Intent(this, ProductDetailActivity.class);
                intent.putExtra("product", productList.get(i));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
        findDealByOrderIdPresenter.setFindDealByOrderIdView(null);
        findProductByIdPresenter.setView(null);
        updateOrderPresenter.setUpdateOrderView(null);
        findReceAddressByIdPresenter.setFindReceAddressByIdView(null);
    }
}
