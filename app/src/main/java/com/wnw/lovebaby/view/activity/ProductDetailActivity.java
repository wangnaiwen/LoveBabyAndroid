package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.ProductImageAdapter;
import com.wnw.lovebaby.domain.Product;
import com.wnw.lovebaby.domain.ProductImage;
import com.wnw.lovebaby.domain.ShoppingCar;
import com.wnw.lovebaby.domain.User;
import com.wnw.lovebaby.login.ActivityCollector;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.FindImagesByProductIdPresenter;
import com.wnw.lovebaby.presenter.InsertShoppingCarPresenter;
import com.wnw.lovebaby.util.TypeConverters;
import com.wnw.lovebaby.view.viewInterface.IFindImagesByProductIdView;
import com.wnw.lovebaby.view.viewInterface.IInsertShoppingCarView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/2.
 */

public class ProductDetailActivity extends Activity implements View.OnClickListener, IInsertShoppingCarView,
        IFindImagesByProductIdView {

    /**
     * 能点击的部分
     * */
    private ImageView mBack;          //返回键
    private TextView mShoppingCar;    //加入购物车
    private RelativeLayout mReview;   //查看评论
    private TextView mShareTv;         //分享按钮

    /**
     * 商品信息部分
     * */
    private ImageView mCoverImg;          //图片封面
    private TextView mPriceTv;         //价格
    private TextView mTitleTv;   //标题

    /**
     * 商品描述部分
     * */
    private TextView mNumberingTv;    //编号
    private TextView mNameTv;         //名字
    private TextView mBrandTv;        //品牌
    private TextView mDescriptionTv;    //描述

    private ListView imageLv;
    private ProductImageAdapter imageAdapter;
    private List<ProductImage> imageList = new ArrayList<>();
    private FindImagesByProductIdPresenter findImagesByProductIdPresenter;


    private Product product;         //当前页显示的Product对象

    private InsertShoppingCarPresenter insertShoppingCarPresenter;  //presenter对象
    private ShoppingCar shoppingCar = null;    //要插入的ShoppingCar对象
    private User user = null;                  //当前登录的User对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ActivityCollector.addActivity(this);
        insertShoppingCarPresenter = new InsertShoppingCarPresenter(this,this);
        getUser();
        initView();
        initPresenter();
        getProductFromLastActivity();
        setShoppingCar();
        startFindImages();
    }

    //get the current user
    private void getUser(){
        user = new User();
        SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
        user.setId(preferences.getInt("id", 0));
        user.setPhone(preferences.getString("phone", ""));
        user.setType(preferences.getInt("type",0));
    }

    private void initPresenter(){
        findImagesByProductIdPresenter = new FindImagesByProductIdPresenter(this, this);
    }

    private void startFindImages(){
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(ProductDetailActivity.this,"请检查网络",Toast.LENGTH_LONG).show();
        }else {
            findImagesByProductIdPresenter.findImagesByProductId(product.getId());
        }
    }

    @Override
    public void showProductImages(List<ProductImage> images) {
        dismissDialogs();
        if (images == null){

        }else {
            this.imageList = images;
            setAdapter();
        }
    }

    private void setAdapter(){
        if (imageAdapter != null){
            imageAdapter.setImageList(imageList);
            imageAdapter.notifyDataSetChanged();
        }else{
            Log.d("www", imageList.size()+"");
            imageAdapter = new ProductImageAdapter(this, imageList);
            imageLv.setAdapter(imageAdapter);
        }
    }

    /**
     * 初始化界面
     * */
    private void initView(){
        mBack = (ImageView)findViewById(R.id.product_detail_back);
        mReview = (RelativeLayout)findViewById(R.id.product_detail_of_review);
        mShoppingCar = (TextView)findViewById(R.id.product_detail_action_shopping_car);
        mShareTv = (TextView)findViewById(R.id.tv_product_share);
        mBack.setOnClickListener(this);
        mReview.setOnClickListener(this);
        mShoppingCar.setOnClickListener(this);
        mShareTv.setOnClickListener(this);

        mTitleTv = (TextView)findViewById(R.id.product_detail_of_title);
        mCoverImg = (ImageView)findViewById(R.id.product_detail_of_cover);
        mPriceTv = (TextView)findViewById(R.id.product_detail_of_price);

        mNumberingTv = (TextView)findViewById(R.id.product_detail_of_numbering);
        mNameTv = (TextView)findViewById(R.id.product_detail_of_name);
        mBrandTv = (TextView)findViewById(R.id.product_detail_of_brand);
        mDescriptionTv = (TextView)findViewById(R.id.product_detail_of_description);

        imageLv = (ListView)findViewById(R.id.lv_image);
        imageLv.setDivider(null);
    }

    //setShoppingCat
    private void setShoppingCar(){
        shoppingCar = new ShoppingCar();
        shoppingCar.setUserId(user.getId());
        shoppingCar.setProductId(product.getId());
        shoppingCar.setRetailPrice((int)product.getRetailPrice());
        shoppingCar.setProductCount(1);
        shoppingCar.setProductName(product.getName());
        shoppingCar.setProductCover(product.getCoverImg());
    }

    //start presenter
    private void startPresenter(){
        insertShoppingCarPresenter.insertShoppingCar(shoppingCar);
    }

    /**
     * 得到从上一个Activity传过来的Product对象,并将其信息显示在View中
     * */
    private void getProductFromLastActivity(){
        Intent intent = getIntent();
        product = (Product)intent.getSerializableExtra("product");

        Glide.with(this).load(product.getCoverImg()).into(mCoverImg);
        mTitleTv.setText(product.getName());
        mNameTv.setText(product.getName());
        mBrandTv.setText(product.getBrand());
        mNumberingTv.setText(product.getNumbering());
        mDescriptionTv.setText(product.getDescription());
        mPriceTv.setText(TypeConverters.LongConvertToString(product.getRetailPrice()));
    }

    @Override
    public void showDialog() {
        showDialogs();
    }

    @Override
    public void showResult(boolean isSuccess) {
        dismissDialogs();
        if(isSuccess){  //插入成功
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
        }
    }


    ProgressDialog dialog = null;
    private void showDialogs(){
        if(dialog == null){
            dialog = new ProgressDialog(this);
            dialog.setMessage("正在添加到购物车中..");
        }
        dialog.show();
    }

    private void dismissDialogs(){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    /**
     * 监听点击事件
     * */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.product_detail_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.product_detail_action_shopping_car:
                startPresenter();
                break;
            case R.id.product_detail_of_review:
                Intent intent = new Intent(this, ProductReviewActivity.class);
                intent.putExtra("productId",product.getId());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.tv_product_share:

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
        insertShoppingCarPresenter.setView(null);
        insertShoppingCarPresenter = null;
        ActivityCollector.removeActivity(this);
    }
}

