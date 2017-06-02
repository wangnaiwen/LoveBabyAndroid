package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.MyOrderAdapter;
import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.FindOrderByShopIdPresenter;
import com.wnw.lovebaby.view.viewInterface.IFindOrderByShopIdView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/23.
 */

public class ShopOrderActivity extends Activity implements View.OnClickListener,
        AdapterView.OnItemClickListener,AbsListView.OnScrollListener, IFindOrderByShopIdView{

    private ImageView back;
    private ListView myOrderLv;
    private TextView noOrderTv;
    private MyOrderAdapter myOrderAdapter;
    private List<Order> orderList = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();

    private int page = 1; //当前是第一页
    private boolean isEnd = false;  //是否是最后一页，已经加载完

    private FindOrderByShopIdPresenter findOrderByShopIdPresenter;

    private int shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_order);
        getShopId();
        initView();
        initPresenter();
        startPresenter();
    }

    private void getShopId(){
        Intent intent = getIntent();
        shopId = intent.getIntExtra("shopId", 0);
    }

    private void initView(){
        myOrderLv = (ListView)findViewById(R.id.lv_shop_order);
        noOrderTv = (TextView)findViewById(R.id.tv_no_shop_order);
        back = (ImageView)findViewById(R.id.back_shop_order);
        myOrderLv.setOnScrollListener(this);

        myOrderLv.setOnItemClickListener(this);
        back.setOnClickListener(this);
        noOrderTv.setOnClickListener(this);
    }

    private void initPresenter(){
        findOrderByShopIdPresenter = new FindOrderByShopIdPresenter(this,this);
    }

    private void startPresenter(){
        if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "暂无网络", Toast.LENGTH_SHORT).show();
            noOrderTv.setVisibility(View.VISIBLE);
            myOrderLv.setVisibility(View.GONE);
        }else {
            findOrderByShopIdPresenter.findOrderByShopId(shopId, page);
        }
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
    public void showOrdersByShopId(List<Order> orders, List<String> names) {
        dismissDialogs();
        page ++;
        if(orders != null){
            if(orders.size() < 10){  //一次加载10条，不够就是说明加载完成了
                isEnd = true;
            }

            orderList.addAll(orders);
            nameList.addAll(names);
            if(orderList.size() == 0){
                //没有
                noOrderTv.setVisibility(View.VISIBLE);
                myOrderLv.setVisibility(View.GONE);
            }else{
                //存在
                //没有
                noOrderTv.setVisibility(View.GONE);
                myOrderLv.setVisibility(View.VISIBLE);
                setOrderAdapter();
            }
        }else{
            isEnd = true;
            if(orderList.size() == 0){
                //没有
                noOrderTv.setVisibility(View.VISIBLE);
                myOrderLv.setVisibility(View.GONE);
            }else {
                noOrderTv.setVisibility(View.GONE);
                myOrderLv.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setOrderAdapter(){
        if(myOrderAdapter == null){
            myOrderAdapter = new MyOrderAdapter(this, orderList, nameList);
            myOrderLv.setAdapter(myOrderAdapter);
        }else {
            myOrderAdapter.setOrderList(orderList);
            myOrderAdapter.setNameList(nameList);
            myOrderAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.lv_shop_order:
                if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
                    Toast.makeText(this, "当前无网络", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(this, MyOrderDetailActivity.class);
                    intent.putExtra("order", orderList.get(i));
                    startActivity(intent);
                    ((Activity)this).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_shop_order:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.tv_no_shop_order:
                startPresenter();
                break;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        // 当不滚动时
        if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            // 判断是否滚动到底部
            if (absListView.getLastVisiblePosition() == absListView.getCount() - 1) {
                //加载更多功能的代码
                //监听ListView滑动到底部，加载更多评论
                if(isEnd){  //到底
                    Toast.makeText(ShopOrderActivity.this,"已经到底了",Toast.LENGTH_SHORT).show();
                }else {
                    startPresenter();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

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
        findOrderByShopIdPresenter.setFindOrderByShopIdView(null);
    }
}
