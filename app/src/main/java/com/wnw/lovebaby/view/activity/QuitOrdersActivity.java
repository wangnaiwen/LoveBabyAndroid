package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.MyOrderAdapter;
import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.FindQuitOrdersPresenter;
import com.wnw.lovebaby.view.viewInterface.IFindQuitOrdersView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/5/30.
 */

public class QuitOrdersActivity  extends Activity implements AdapterView.OnItemClickListener,
        IFindQuitOrdersView, View.OnClickListener{
    private ListView orderLv;
    private TextView noOrder;
    private ImageView back;

    private MyOrderAdapter myOrderAdapter;
    private List<Order> orderList = new ArrayList<>();

    private FindQuitOrdersPresenter findQuitOrdersPresenter;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quit_order);
        getUserId();
        initView();
        initPresenter();
        startFindOrder();
    }

    private void initView(){
        orderLv = (ListView)findViewById(R.id.lv_order);
        noOrder = (TextView)findViewById(R.id.null_order);
        back = (ImageView)findViewById(R.id.back);

        back.setOnClickListener(this);
        orderLv.setOnItemClickListener(this);
        noOrder.setOnClickListener(this);
    }

    private void getUserId(){
        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        userId = sharedPreferences.getInt("id",0);
    }

    private void initPresenter(){
        findQuitOrdersPresenter = new FindQuitOrdersPresenter(this,this);
    }

    private void startFindOrder(){
        findQuitOrdersPresenter.findQuitOrders(userId);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.lv_order:
                if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
                    Toast.makeText(this, "当前无网络", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(this, MyOrderDetailActivity.class);
                    intent.putExtra("order", orderList.get(i));
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.null_order:
                startFindOrder();
                break;
            case R.id.back:
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
    public void showOrders(List<Order> orders) {
        dismissDialogs();
        if (orders == null){
            noOrder.setVisibility(View.VISIBLE);
            orderLv.setVisibility(View.GONE);
        }else {
            Log.d("www", orders.size()+"");
            if (orders.size() == 0){
                noOrder.setVisibility(View.VISIBLE);
                orderLv.setVisibility(View.GONE);
            }else {
                orderLv.setVisibility(View.VISIBLE);
                noOrder.setVisibility(View.GONE);
                orderList.addAll(orders);
                setAdapter();
            }
        }
    }

    private void setAdapter(){
        if (myOrderAdapter == null){
            myOrderAdapter = new MyOrderAdapter(this, orderList);
            orderLv.setAdapter(myOrderAdapter);
        }else{
            myOrderAdapter.setOrderList(orderList);
            myOrderAdapter.notifyDataSetChanged();
        }
    }
}