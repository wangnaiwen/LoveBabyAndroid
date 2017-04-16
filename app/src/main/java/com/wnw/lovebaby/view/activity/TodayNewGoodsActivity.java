package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.ProductAdapter;
import com.wnw.lovebaby.domain.Product;
import com.wnw.lovebaby.login.ActivityCollector;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.FindNewProductPresenter;
import com.wnw.lovebaby.view.viewInterface.IFindNewProductView;


import java.util.List;

/**
 * Created by wnw on 2016/12/13.
 */

public class TodayNewGoodsActivity extends Activity implements View.OnClickListener,
        AdapterView.OnItemClickListener, IFindNewProductView{

    private ImageView backTodayNewGoods;
    private GridView todayNewGoodsGv;
    private TextView netErrorTv;

    private List<Product> productList;            //得到新上架的商品List
    private ProductAdapter productAdapter;        //Product Adapter
    private FindNewProductPresenter findNewProductPresenter ; //presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_new_goods);
        ActivityCollector.addActivity(this);
        initView();
        initPresenter();
        startNewGoodsPresenter();
    }

    private void initView(){
        backTodayNewGoods = (ImageView)findViewById(R.id.back_today_new_goods);
        todayNewGoodsGv = (GridView)findViewById(R.id.gv_today_new_goods);
        netErrorTv = (TextView)findViewById(R.id.net_error);

        backTodayNewGoods.setOnClickListener(this);
        netErrorTv.setOnClickListener(this);
        todayNewGoodsGv.setOnItemClickListener(this);
    }

    //init presenter
    private void initPresenter(){
        findNewProductPresenter = new FindNewProductPresenter(this,this);
    }

    //find New Goods Presenter
    private void startNewGoodsPresenter(){
        if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "网络不可用",Toast.LENGTH_SHORT).show();
            netErrorTv.setVisibility(View.VISIBLE);
        }else{
            findNewProductPresenter.load();
        }
    }

    @Override
    public void showLoading() {
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
    public void showNewProduct(List<Product> products) {
        dismissDialogs();
        productList = products;
        if (products != null){
            netErrorTv.setVisibility(View.GONE);
            todayNewGoodsGv.setVisibility(View.VISIBLE);
            setProductAdapter();
        }else {
            todayNewGoodsGv.setVisibility(View.GONE);
            netErrorTv.setVisibility(View.VISIBLE);
        }
    }

    //set Adapter
    private void setProductAdapter(){
        if(productAdapter == null){
            productAdapter = new ProductAdapter(this, productList);
            todayNewGoodsGv.setAdapter(productAdapter);
        }else {
            productAdapter.setDatas(productList);
            productAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_today_new_goods:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.net_error:
                startNewGoodsPresenter();
                break;
            default:

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.gv_today_new_goods:
                Intent intent = new Intent(this, ProductDetailActivity.class);
                intent.putExtra("product", productList.get(i));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
