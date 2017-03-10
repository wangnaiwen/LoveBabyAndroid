package com.wnw.lovebaby.view.activity;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.ReceAddressAdapter;
import com.wnw.lovebaby.domain.ReceAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2016/12/11.
 */

public class AddressListActivity extends Activity implements View.OnClickListener{

    public static int RESULT_CODE = 2;

    private ListView receAddressListView;
    private ImageView addressBack;
    private RelativeLayout addReceAddress;

    private ReceAddressAdapter receAddressAdapter;
    private List<ReceAddress> receAddressList;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ReceAddressAdapter.SELECT_RECE_ADDRESS:
                    forResultActivity(msg.arg1);
                    break;
                case ReceAddressAdapter.EDIT_RECE_ADDRESS:
                    startEditReceAddressAty(msg.arg1);
                    break;
                case ReceAddressAdapter.DELETE_RECE_ADDRESS:
                    showDeleteAddressDialog(msg.arg1);
                    break;
            }
        }
    };

    /**
     * start edit address activity
     * */
    private void startEditReceAddressAty(int index){
        Intent intent = new Intent(AddressListActivity.this, EditReceAddressActivity.class);
        ReceAddress receAddress = receAddressList.get(index);
        intent.putExtra("receAddress_data", receAddress);/*
        intent.putExtra("id", receAddress.getId());
        intent.putExtra("userId", receAddress.getUserId());
        intent.putExtra("receiver", receAddress.getReceiver());
        intent.putExtra("phone", receAddress.getPhone());
        intent.putExtra("province", receAddress.getProvince());
        intent.putExtra("city",receAddress.getCity());
        intent.putExtra("district", receAddress.getDistrict());
        intent.putExtra("detailAddress",receAddress.getDetailAddress());
        intent.putExtra("postcode",receAddress.getPostcode());*/
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        initView();
        setAdapter();
    }

    private void setAdapter(){
        receAddressList = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            ReceAddress receAddress = new ReceAddress();
            receAddress.setId(i);
            receAddress.setUserId(i);
            receAddress.setReceiver("小高"+i);
            receAddress.setPhone("1567730144"+i);
            receAddress.setProvince("广西");
            receAddress.setCity("桂林市");
            receAddress.setDistrict("灵川县");
            receAddress.setDetailAddress("桂林电子科技大学花江校区");
            receAddress.setPostcode(541004);
            receAddressList.add(receAddress);
        }
        receAddressAdapter = new ReceAddressAdapter(this, handler, receAddressList);
        receAddressListView.setAdapter(receAddressAdapter);
    }


    /**
     * update the adapter
     * */
    private void updateAdapter(){
        receAddressAdapter.setReceAddressList(receAddressList);
        receAddressAdapter.notifyDataSetChanged();
    }

    private void initView(){
        addressBack = (ImageView)findViewById(R.id.back_address_list);
        receAddressListView = (ListView)findViewById(R.id.lv_rece_address);
        addReceAddress = (RelativeLayout)findViewById(R.id.add_address);

        addReceAddress.setOnClickListener(this);
        addressBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_address_list:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.add_address:
                Intent intent = new Intent(this, AddReceAddressActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    /**
     * show the dialog of delete the address
     * */
    private void showDeleteAddressDialog(final int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除地址");
        builder.setMessage("是否删除这个收货地址？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                receAddressList.remove(index);
                updateAdapter();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog deleteDialog = builder.create();
        deleteDialog.show();
        //Window window = deleteDialog.getWindow();
       // window.setWindowAnimations(R.style.dialog_anim);
    }


    /**
     * start activity for result
     * */
    private void forResultActivity(int index){
        Intent intent = new Intent();
        ReceAddress receAddress = receAddressList.get(index);
        /*intent.putExtra("id", receAddress.getId());
        intent.putExtra("userId", receAddress.getUserId());
        intent.putExtra("receiver", receAddress.getReceiver());
        intent.putExtra("phone", receAddress.getPhone());
        intent.putExtra("province", receAddress.getProvince());
        intent.putExtra("city",receAddress.getCity());
        intent.putExtra("district", receAddress.getDistrict());
        intent.putExtra("detailAddress",receAddress.getDetailAddress());
        intent.putExtra("postcode",receAddress.getPostcode());*/
        intent.putExtra("receAddress_data", receAddress);
        setResult(RESULT_CODE, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
