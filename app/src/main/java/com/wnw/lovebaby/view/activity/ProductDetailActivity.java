package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.domain.Product;
import com.wnw.lovebaby.login.ActivityCollector;
import com.wnw.lovebaby.util.TypeConverters;

/**
 * Created by wnw on 2017/4/2.
 */

public class ProductDetailActivity extends Activity implements View.OnClickListener{

    /**
     * 能点击的部分
     * */
    private ImageView mBack;          //返回键
    private TextView mShoppingCar;    //加入购物车
    private RelativeLayout mReview;   //查看评论

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

    private Product product;         //当前页显示的Product对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ActivityCollector.addActivity(this);
        initView();
        getProductFromLastActivity();
    }

    /**
     * 初始化界面
     * */
    private void initView(){
        mBack = (ImageView)findViewById(R.id.product_detail_back);
        mReview = (RelativeLayout)findViewById(R.id.product_detail_of_review);
        mShoppingCar = (TextView)findViewById(R.id.product_detail_action_shopping_car);
        mBack.setOnClickListener(this);
        mReview.setOnClickListener(this);
        mShoppingCar.setOnClickListener(this);

        mTitleTv = (TextView)findViewById(R.id.product_detail_of_title);
        mCoverImg = (ImageView)findViewById(R.id.product_detail_of_cover);
        mPriceTv = (TextView)findViewById(R.id.product_detail_of_price);

        mNumberingTv = (TextView)findViewById(R.id.product_detail_of_numbering);
        mNameTv = (TextView)findViewById(R.id.product_detail_of_name);
        mBrandTv = (TextView)findViewById(R.id.product_detail_of_brand);
        mDescriptionTv = (TextView)findViewById(R.id.product_detail_of_description);
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

                break;
            case R.id.product_detail_of_review:
                startActivity(new Intent(this, ProductReviewActivity.class));
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
        ActivityCollector.removeActivity(this);
    }
}

