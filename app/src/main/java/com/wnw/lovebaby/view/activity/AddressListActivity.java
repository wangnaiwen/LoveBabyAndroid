package com.wnw.lovebaby.view.activity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.ReceAddressAdapter;
import com.wnw.lovebaby.bean.ReceAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2016/12/11.
 */

public class AddressListActivity extends Activity implements View.OnClickListener{

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

                    break;
                case ReceAddressAdapter.EDIT_RECE_ADDRESS:

                    break;
                case ReceAddressAdapter.DELETE_RECE_ADDRESS:
                    break;
            }
        }
    };

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
            receAddress.setAddress("桂林 灵川县 桂林电子科技大学花江校区");
            receAddress.setPostcode(541004);
            receAddressList.add(receAddress);
        }
        receAddressAdapter = new ReceAddressAdapter(this, handler, receAddressList);
        receAddressListView.setAdapter(receAddressAdapter);
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
}
