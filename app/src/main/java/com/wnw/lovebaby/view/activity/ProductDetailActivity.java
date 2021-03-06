package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.ProductImageAdapter;
import com.wnw.lovebaby.config.MyKeys;
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

import java.io.File;
import java.net.URI;
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
    private ImageView mShareTv;         //分享按钮

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
    private IWXAPI api;

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
        initAPI();
        getUser();
        initView();
        initPresenter();
        getProductFromLastActivity();
        setShoppingCar();
        startFindImages();
    }
    private void initAPI(){
        //微信分享初始化
        api = WXAPIFactory.createWXAPI(this, MyKeys.AppID, true);
        api.registerApp(MyKeys.AppID);

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
        mShareTv = (ImageView) findViewById(R.id.img_product_share);
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
            case R.id.img_product_share:
                showShareDialog();
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
        intent.putExtra(Intent.EXTRA_TEXT, "'"+product.getName()+"'这个产品还挺好的，而且在爱婴粑粑APP上买只要" +TypeConverters.LongConvertToString(product.getRetailPrice()));
        startActivity(intent);
    }

    /**
     * 分享信息到朋友
     *
     */
    private void shareToWxFriend() {
        String text = "这个产品还挺好的，而且在爱婴粑粑APP上买只要"+TypeConverters.LongConvertToString(product.getRetailPrice());

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
        String text = "这个产品还挺好的，而且在爱婴粑粑APP上买只要"+TypeConverters.LongConvertToString(product.getRetailPrice());

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

