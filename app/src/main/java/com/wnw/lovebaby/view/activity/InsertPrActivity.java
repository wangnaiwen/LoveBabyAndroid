package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.InsertPrAdapter;
import com.wnw.lovebaby.bean.ShoppingCarItem;
import com.wnw.lovebaby.domain.Deal;
import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.domain.Pr;
import com.wnw.lovebaby.domain.UserInfo;
import com.wnw.lovebaby.model.modelImpl.FindPrByDealIdImpl;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.FindPrByDealIdPresenter;
import com.wnw.lovebaby.presenter.FindUserInfoPresenter;
import com.wnw.lovebaby.presenter.InsertPrPresenter;
import com.wnw.lovebaby.presenter.UpdateOrderPresenter;
import com.wnw.lovebaby.view.viewInterface.IFindPrByDealIdView;
import com.wnw.lovebaby.view.viewInterface.IFindUserInfoView;
import com.wnw.lovebaby.view.viewInterface.IInsertPrView;
import com.wnw.lovebaby.view.viewInterface.IUpdateOrderView;
import com.wnw.lovebaby.view.viewInterface.MvpBaseActivity;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/18.
 */

public class InsertPrActivity extends MvpBaseActivity<IFindUserInfoView, FindUserInfoPresenter> implements View.OnClickListener,
        IInsertPrView, IFindPrByDealIdView, IFindUserInfoView, IUpdateOrderView{

    private ListView beEvaluatedLv;
    private ImageView back;
    private InsertPrAdapter insertPrAdapter;

    private Order order;

    //这是从上一个Activity传递过来的
    private List<ShoppingCarItem> shoppingCarItemList = new ArrayList<>();
    private List<Deal> dealList = new ArrayList<>();


    //这些是没有评论过的
    private List<ShoppingCarItem> shoppingCarItemList1 = new ArrayList<>();
    private List<Deal> dealList1 = new ArrayList<>();

    private FindPrByDealIdPresenter findPrByDealIdPresenter;
    private InsertPrPresenter insertPrPresenter;
    private UpdateOrderPresenter updateOrderPresenter;

    int pos = 0;  //用户点击评论的位置

    int userId = 0;

    private UserInfo userInfo;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    //得到点击的位置
                     pos = msg.arg1;
                    showInsertPrDialog();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_pr);
        getUser();
        getShoppingCarItem();
        initPresenter();
        initView();
        startFindPrByDealId();

    }

    private void getUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        userId = sharedPreferences.getInt("id",0);
        mPresenter.findUserInfo(this, userId);
    }

    private void getShoppingCarItem(){
        Intent intent = getIntent();
        shoppingCarItemList = (List<ShoppingCarItem>)intent.getSerializableExtra("shoppingCarItemList");
        dealList = (List<Deal>)intent.getSerializableExtra("dealList");
        order = (Order)intent.getSerializableExtra("order");
    }

    private void initView(){
        back = (ImageView)findViewById(R.id.back_insert_pr);
        beEvaluatedLv = (ListView)findViewById(R.id.lv_be_evaluated);
        back.setOnClickListener(this);
    }


    private void initPresenter(){
        findPrByDealIdPresenter = new FindPrByDealIdPresenter(this, this);
        insertPrPresenter = new InsertPrPresenter(this, this);
        updateOrderPresenter = new UpdateOrderPresenter(this,this);
    }

    @Override
    public void findUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public void updateUserInfo(boolean isSuccess) {

    }

    @Override
    protected FindUserInfoPresenter createPresenter() {
        return new FindUserInfoPresenter();
    }

    private void startFindPrByDealId(){
        if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(InsertPrActivity.this, "当前无网络", Toast.LENGTH_SHORT).show();
        }else {
            findPrByDealIdPresenter.findPrsByDealId(dealList.get(position).getId());
        }
    }

    private int position = 0;
    @Override
    public void showPrs(List<Pr> prs) {

        if(prs != null){
            //说明已经评论过了,不用添加
        }else {
            //说明没有评论过
            shoppingCarItemList1.add(shoppingCarItemList.get(position));
            dealList1.add(dealList.get(position));
        }
        position ++;
        if(position == dealList.size()){
            //已经查找结束，开始设置Adapter
            dismissDialogs();
            setAdapter();
        }else{
            startFindPrByDealId();
        }
    }

    private void setAdapter(){
        insertPrAdapter = new InsertPrAdapter(this, handler, shoppingCarItemList1);
        beEvaluatedLv.setAdapter(insertPrAdapter);
    }

    private void insertPr(int productScore, int serviceScore, int logisticsScore, String detail){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date d = new java.util.Date();
        String time = sdf.format(d);

        Pr pr = new Pr();
        pr.setProductId(shoppingCarItemList1.get(pos).getProductId());
        pr.setLogisticsScore(logisticsScore);
        pr.setServiceScore(serviceScore);
        pr.setProductScore(productScore);
        pr.setEvaluation(detail);
        pr.setTime(time);
        pr.setDealId(dealList1.get(pos).getId());
        pr.setUserId(userId);
        if (userInfo == null){
            pr.setUserNickName("匿名用户");
        }else{
            if (userInfo.getNickName() == null){
                pr.setUserNickName("匿名用户");
            }else{
                pr.setUserNickName(userInfo.getNickName());
            }
        }

        insertPrPresenter.insertPr(pr);
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
    public void showResult(boolean isSuccess) {
        dismissDialogs();
        if(isSuccess){
            Toast.makeText(InsertPrActivity.this, "评论提交成功", Toast.LENGTH_SHORT).show();
            //判断当前是否存在未评论的deal
            shoppingCarItemList1.remove(pos);
            dealList1.remove(pos);
            if (shoppingCarItemList1.size() == 0){
                startUpdateOrder();
            }else {
                //更新Adapter
                insertPrAdapter.setShoppingCarItemList(shoppingCarItemList1);
                insertPrAdapter.notifyDataSetChanged();
            }
        }else{
            Toast.makeText(InsertPrActivity.this, "评论提交失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void startUpdateOrder(){
        order.setOrderType(5);
        updateOrderPresenter.updateOrder(order);
    }

    @Override
    public void showUpdateOrderResult(boolean isSuccess) {
        dismissDialogs();
        if (isSuccess){
            //更改订单的状态为完成
            //finish 当前的Activity
            Intent intent = new Intent();
            intent.putExtra("isSuccess",true);
            setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else{

        }
    }

    AlertDialog alertDialog = null;
    private void showInsertPrDialog(){

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_insert_pr, null);
        final RatingBar productScoreBar = (RatingBar)view.findViewById(R.id.rb_insert_product_score);
        final RatingBar serviceScoreBar = (RatingBar)view.findViewById(R.id.rb_insert_service_score);
        final RatingBar logisticsScoreBar = (RatingBar)view.findViewById(R.id.rb_insert_logistics_score);
        final EditText detailEvaluationEt = (EditText)view.findViewById(R.id.et_insert_detail_evaluation);
        final TextView sureTv = (TextView)view.findViewById(R.id.tv_sure);
        productScoreBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            }
        });
        serviceScoreBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            }
        });
        logisticsScoreBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            }
        });
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String detailEvaluation = detailEvaluationEt.getText().toString().trim();
                //判断，然后提交评论
                if(detailEvaluation.isEmpty() || detailEvaluation.equals("")){
                    detailEvaluationEt.setHintTextColor(Color.RED);
                    detailEvaluationEt.setHint("请输入具体评论");
                    Log.d("deatailEvalution", "aa"+detailEvaluation+"bb");
                }else{
                    //开始插入
                    int productScore = (int)productScoreBar.getRating();
                    int serviceScore = (int)serviceScoreBar.getRating();
                    int logistics = (int)logisticsScoreBar.getRating();
                    insertPr(productScore, serviceScore, logistics, detailEvaluation);
                    alertDialog.dismiss();
                }
            }
        });

        alertDialog = new AlertDialog.Builder(this)
                .setTitle("提交评论")
                .setView(view).create();
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_insert_pr:
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
}
