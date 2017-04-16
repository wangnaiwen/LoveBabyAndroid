package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.domain.Shop;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.FindShopByIdPresenter;
import com.wnw.lovebaby.view.viewInterface.IFindShopByIdView;

/**
 * Created by wnw on 2017/4/11.
 */

public class PickShopActivity extends Activity implements View.OnClickListener,
        IFindShopByIdView{

    private ImageView back;
    private EditText shopIdEt;
    private TextView sureShopIdTv;

    private int shopId;  //shopId
    private FindShopByIdPresenter findShopByIdPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_shop);
        initView();
        getShopId();
        initPresenter();
    }

    //得到从上一个Activity中传递过来的shopId
    private void getShopId(){
        Intent intent = getIntent();
        shopId = intent.getIntExtra("shopId", 0);
        if(shopId != 0){
            //说明传递过来的不是空值
            shopIdEt.setText(shopId+"");
        }
    }

    //init view
    private void initView(){
        back = (ImageView)findViewById(R.id.back_pick_shop);
        shopIdEt = (EditText)findViewById(R.id.et_pick_shop_id);
        sureShopIdTv = (TextView)findViewById(R.id.tv_sure_shop_id);

        back.setOnClickListener(this);
        sureShopIdTv.setOnClickListener(this);
    }

    //init presenter
    private void initPresenter(){
        findShopByIdPresenter = new FindShopByIdPresenter(this, this);
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
    public void showShopById(Shop shop) {
        dismissDialogs();
        if(shop == null){
            Toast.makeText(this,"找不到该店铺",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"已选择‘"+shop.getName()+"’",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("shopId",shop.getId());
            intent.putExtra("shopName", shop.getName());
            setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_pick_shop:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.tv_sure_shop_id:
                if(shopIdEt.getText().toString().trim().isEmpty()){
                    Toast.makeText(this,"输入不能为空",Toast.LENGTH_SHORT).show();
                }else if(shopIdEt.getText().toString().trim().equals(shopId)){
                    Toast.makeText(this,"选择的店铺没有发生改变",Toast.LENGTH_SHORT).show();
                }else{
                    if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
                        Toast.makeText(this,"选择的店铺没有发生改变",Toast.LENGTH_SHORT).show();
                    }else {
                        //在这里去查找
                        findShopByIdPresenter.findShopById(Integer.parseInt(shopIdEt.getText().toString().trim()));
                    }
                }
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
