package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.OrderLvAdapter;
import com.wnw.lovebaby.domain.Deal;
import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.domain.ReceAddress;
import com.wnw.lovebaby.bean.ShoppingCarItem;
import com.wnw.lovebaby.domain.ShoppingCar;
import com.wnw.lovebaby.login.ActivityCollector;
import com.wnw.lovebaby.model.modelInterface.IDeleteShoppingCarModel;
import com.wnw.lovebaby.presenter.DeleteShoppingCarPresenter;
import com.wnw.lovebaby.presenter.InsertDealPresenter;
import com.wnw.lovebaby.presenter.InsertOrderPresenter;
import com.wnw.lovebaby.presenter.UpdateOrderPresenter;
import com.wnw.lovebaby.util.TypeConverters;
import com.wnw.lovebaby.view.viewInterface.IDeleteShoppingCarView;
import com.wnw.lovebaby.view.viewInterface.IInsertDealView;
import com.wnw.lovebaby.view.viewInterface.IInsertOrderView;
import com.wnw.lovebaby.view.viewInterface.IUpdateOrderView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by wnw on 2016/12/11.
 */

public class OrderConfirmationActivity extends Activity implements View.OnClickListener,
        IInsertOrderView,IInsertDealView,IDeleteShoppingCarView,IUpdateOrderView{
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
    private RelativeLayout picShopRl;       //选择店铺
    private TextView disShopNameTv;         //显示店铺名称

    private OrderLvAdapter orderLvAdapter;
    private List<ShoppingCarItem> shoppingCarItemList;    //shopping car item List
    private List<ShoppingCar> shoppingCarList;            //shopping car list
    private int sumPrice;                                 //总价

    private Order order;         //要插入的Order,和返回更新的订单

    private int userId;
    private int shopId = 1;            //拿来保存选择的店铺Id,默认为1，代表不选择任何店铺
    private String shopName;          //拿来保存选择的店铺的Name
    private ReceAddress receAddress;  //选择的address
    private String remark;            //备注

    private int insertDealPosition = 0;        //标志插入Deal到达哪一个位置了
    private int deleteShoppingCarPosition = 0; //标志删除ShoppingCar到达哪一个位置

    private InsertOrderPresenter insertOrderPresenter;
    private InsertDealPresenter insertDealPresenter;
    private DeleteShoppingCarPresenter deleteShoppingCarPresenter;
    private UpdateOrderPresenter updateOrderPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        ActivityCollector.addActivity(this);

        getUserId();
        getList();
        initView();
        initPresenter();
        setAdapter();
    }

    //get userId from sharedPrefenreces

    private void getUserId(){
        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        userId = sharedPreferences.getInt("id",0);
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
        picShopRl = (RelativeLayout)findViewById(R.id.rl_pick_shop);
        disShopNameTv = (TextView)findViewById(R.id.tv_dis_shop_name);

        pickAddress.setOnClickListener(this);
        changeAddress.setOnClickListener(this);
        backOrder.setOnClickListener(this);
        orderPay.setOnClickListener(this);
        picShopRl.setOnClickListener(this);

        orderSumPrice.setText(TypeConverters.IntConvertToString(sumPrice));
    }

    //init presenter
    private void initPresenter(){
        insertOrderPresenter = new InsertOrderPresenter(this, this);
        insertDealPresenter = new InsertDealPresenter(this, this);
        deleteShoppingCarPresenter = new DeleteShoppingCarPresenter(this, this);
        updateOrderPresenter = new UpdateOrderPresenter(this, this);
    }

    //get List of shopping car and shopping car item
    private void getList(){
        Intent intent = getIntent();
        shoppingCarItemList = (List<ShoppingCarItem>) intent.getSerializableExtra("checkedShoppingCarItemList");
        shoppingCarList = (List<ShoppingCar>) intent.getSerializableExtra("checkedShoppingCarList");
        sumPrice = intent.getIntExtra("sumPrice",0);
    }

    //set adapter
    private void setAdapter(){
        orderLvAdapter = new OrderLvAdapter(this, shoppingCarItemList);
        orderListView.setAdapter(orderLvAdapter);
    }

    //start insert order
    private void startInsertOrder(){

        //订单号 = 时间戳 + IMEI
        TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date d = new java.util.Date();
        String str = sdf.format(d);
        String orderNumbering = str + szImei;

        //订单创建时间
        String createTime = sdf.format(d);

        remark = remarkEd.getText().toString().trim();

        order = new Order();
        order.setUserId(userId);
        order.setShopId(shopId);
        order.setRemark(remark);
        order.setOrderNumber(orderNumbering);
        order.setOrderType(0);
        order.setCreateTime(createTime);
        order.setAddressId(receAddress.getId());
        insertOrderPresenter.insertOrder(order);
    }

    //start insert deal
    private void startInsertDeal(){
        //获取要插入的Deal
        ShoppingCarItem shoppingCarItem = shoppingCarItemList.get(insertDealPosition);
        Deal deal = new Deal();
        deal.setOrderId(order.getId());
        deal.setProductId(shoppingCarItem.getProductId());
        deal.setProductName(shoppingCarItem.getGoodsTitle());
        deal.setProductCount(shoppingCarItem.getGoodsNum());
        deal.setSumPrice(shoppingCarItem.getGoodsNum()*shoppingCarItem.getGoodsPrice());
        insertDealPresenter.insertDeal(deal);
    }

    //start delete from shopping car
    private void startDeleteShoppingCar(){

        deleteShoppingCarPresenter.deleteShoppingCar(shoppingCarItemList.get(deleteShoppingCarPosition).getId());

    }

    //start update order order_type = 1
    private void startUpdateOrder(){
        order.setOrderType(1);
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
    public void showInsertOrderResult(boolean isSuccess, int id) {
        if(isSuccess){
            order.setId(id);
            //开始插入Deal
            startInsertDeal();
        }else {
            dismissDialogs();
            Toast.makeText(this,"订单提交失败",Toast.LENGTH_SHORT).show();
        }
    }

    //计数，查看成功插入的次数是不是等于应该插入的次数
    @Override
    public void showInsertDealResult(boolean isSuccess) {
        if(isSuccess){
            insertDealPosition ++;
            if(insertDealPosition == shoppingCarItemList.size()){
                //全部插入成功，开始删除ShoppingCar
                startDeleteShoppingCar();
            }else{
                //还没插完，继续插入
                startInsertDeal();
            }
        }else {
            dismissDialogs();
            Toast.makeText(this,"订单提交失败",Toast.LENGTH_SHORT).show();
        }
    }

    //计数，查看成功删除的次数是不是等于应该删除的次数，等于则删除成功，开始更新订单状态
    @Override
    public void showDeleteResult(boolean isSuccess) {
        if(isSuccess){
            deleteShoppingCarPosition++;
            if(deleteShoppingCarPosition == shoppingCarItemList.size()){
                //全部删除成功，开始更新订单状态为未付款状态
                startUpdateOrder();
            }else{
                //还没有删除完，继续删除
                startDeleteShoppingCar();
            }
        }else {
            dismissDialogs();
            Toast.makeText(this,"订单提交失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showUpdateOrderResult(boolean isSuccess) {
        dismissDialogs();
        if(isSuccess){
            Toast.makeText(this,"订单提交成功",Toast.LENGTH_SHORT).show();
            //订单提交成功，跳转到支付页面，并且finish当前页面
            Intent intent = new Intent(this, PayActivity.class);
            intent.putExtra("sumPrice", sumPrice);
            intent.putExtra("order", order);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this,"订单提交失败",Toast.LENGTH_SHORT).show();
        }
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
                if(receAddress == null){
                    Toast.makeText(this, "请选择地址", Toast.LENGTH_SHORT).show();
                }else {
                    startInsertOrder();
                }
                break;
            case R.id.rl_pick_shop:
                //go to 选择店铺
                Intent intent = new Intent(this, PickShopActivity.class);
                intent.putExtra("shopId", shopId);
                startActivityForResult(intent,2);
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
        intent.putExtra("lastAty", 0);
        startActivityForResult(intent, REQUEST_CODE);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == AddressListActivity.RESULT_CODE){
            Intent intent = data;
            receAddress = (ReceAddress)intent.getSerializableExtra("receAddress_data");
            if(receAddress != null){
                pickAddress.setVisibility(View.GONE);
                changeAddress.setVisibility(View.VISIBLE);
                orderReceiver.setText(receAddress.getReceiver());
                orderRecePhone.setText(receAddress.getPhone());
                orderReceAddress.setText(receAddress.getProvince()+" " +receAddress.getCity() + " "
                        + receAddress.getCounty() + " " + receAddress.getDetailAddress());
            }
        }else if(requestCode == 2 && resultCode == RESULT_OK){
            shopId = data.getIntExtra("shopId", 0);
            shopName = data.getStringExtra("shopName");
            disShopNameTv.setText(shopName);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
