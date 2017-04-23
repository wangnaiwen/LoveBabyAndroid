package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.domain.Shop;
import com.wnw.lovebaby.domain.User;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.FindUserByIdPresenter;
import com.wnw.lovebaby.presenter.InsertShopPresenter;
import com.wnw.lovebaby.view.viewInterface.IFindUserByIdView;
import com.wnw.lovebaby.view.viewInterface.IInsertShopView;

import java.io.Serializable;

/**
 * Created by wnw on 2017/4/23.
 */

public class OpenMyShopActivity extends Activity implements View.OnClickListener,
        IInsertShopView, IFindUserByIdView{

    private ImageView back;
    private EditText nameEt;
    private EditText idCardEt;
    private EditText shopNameEt;
    private EditText wechatEt;
    private RelativeLayout pickInviteeRl;
    private TextView disInviteeNameTv;
    private TextView submitTv;

    private Shop shop = new Shop();
    private int userId;
    private int invitee;

    private InsertShopPresenter insertShopPresenter;
    private FindUserByIdPresenter findUserByIdPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_my_shop);
        getUserId();
        initView();
        initPresenter();
    }

    private void getUserId(){
        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        userId = sharedPreferences.getInt("id",0);
        shop.setUserId(userId);
    }

    private void initView(){
        back = (ImageView)findViewById(R.id.back_open_my_shop);
        nameEt = (EditText)findViewById(R.id.et_open_shop_name);
        idCardEt = (EditText)findViewById(R.id.et_open_shop_id_card);
        shopNameEt = (EditText)findViewById(R.id.et_open_shop_shop_name);
        wechatEt = (EditText)findViewById(R.id.et_open_shop_wechat);
        submitTv = (TextView)findViewById(R.id.tv_submit_open_shop);
        pickInviteeRl = (RelativeLayout)findViewById(R.id.rl_pick_invitee);
        disInviteeNameTv = (TextView)findViewById(R.id.tv_dis_invitee_name);

        pickInviteeRl.setOnClickListener(this);
        back.setOnClickListener(this);
        submitTv.setOnClickListener(this);
    }

    private void initPresenter(){
        insertShopPresenter = new InsertShopPresenter(this,this);
        findUserByIdPresenter = new FindUserByIdPresenter(this, this);
    }

    private void startInsertShop(){
        if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "暂无网路", Toast.LENGTH_SHORT).show();
        }else {
            insertShopPresenter.insertShop(shop);
        }
    }

    private void startFindUser(int id){
        if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "暂无网路", Toast.LENGTH_SHORT).show();
        }else {
            findUserByIdPresenter.findUserById(id);
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
    public void showUserById(User user) {
        dismissDialogs();
        if(user == null){
            Toast.makeText(this, "邀请者不存在", Toast.LENGTH_SHORT).show();
        }else {
            if(userId == user.getId()){
                Toast.makeText(this, "邀请者不能是自己", Toast.LENGTH_SHORT).show();
            }else {
                invitee = user.getId();
                disInviteeNameTv.setText(user.getId()+"");
            }
        }
    }

    @Override
    public void showInsertShopResult(boolean isSuccess) {
        dismissDialogs();
        if(isSuccess){
            Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("shop", shop);
            setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else {
            Toast.makeText(this, "提交失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_pick_invitee:
                showInsertPrDialog();
                break;
            case R.id.back_open_my_shop:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.tv_submit_open_shop:
                shop.setOwner(nameEt.getText().toString().trim());
                shop.setIdCard(idCardEt.getText().toString().trim());
                shop.setWechat(wechatEt.getText().toString().trim());
                shop.setReviewType(1);
                shop.setInvitee(invitee);
                shop.setName(shopNameEt.getText().toString().trim());
                if(shop.getOwner().isEmpty()){
                    nameEt.setHint("输入店铺的拥有者");
                }else if(shop.getIdCard().isEmpty()){
                    idCardEt.setHint("请输入你的身份证号码");
                }else if(shop.getName().isEmpty()){
                    shopNameEt.setHint("输入店铺的名称");
                }else if(shop.getWechat().isEmpty()){
                    nameEt.setHint("输入你的微信");
                }else{
                    startInsertShop();
                }
                break;
        }
    }

    AlertDialog alertDialog = null;
    private void showInsertPrDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_find_user, null);
        final EditText userIdEt = (EditText)view.findViewById(R.id.et_user_id);
        TextView sureTv = (TextView)view.findViewById(R.id.tv_sure);
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userIdEt.getText().toString().trim().isEmpty()){
                    userIdEt.setHint("请输入用户的ID");
                }else{
                    alertDialog.dismiss();
                    startFindUser(Integer.parseInt(userIdEt.getText().toString().trim()));
                }
            }
        });

        alertDialog = new AlertDialog.Builder(this)
                .setTitle("邀请者的ID")
                .setView(view).create();
        alertDialog.show();
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
        insertShopPresenter.setInsertShopView(null);
        findUserByIdPresenter.setView(null);
    }
}
