package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wnw.lovebaby.R;

/**
 * Created by wnw on 2016/12/13.
 */

public class EditReceAddressActivity extends Activity implements View.OnClickListener{
    private ImageView backEditReceAddress;
    private EditText editReceiver;
    private EditText editRecePhone;
    private LinearLayout rePickReceAddressCity;
    private EditText editReceAddressHouseNum;
    private EditText editPostcode;
    private TextView displayAddress;
    private Button finishEditReceAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        intiView();
        setDefaultData();
    }

    private void intiView(){
        backEditReceAddress = (ImageView)findViewById(R.id.back_edit_address);
        editReceiver= (EditText)findViewById(R.id.et_edit_address_receiver);
        editRecePhone = (EditText)findViewById(R.id.et_edit_address_phone);
        editReceAddressHouseNum = (EditText)findViewById(R.id.et_edit_address_house_num);
        editPostcode = (EditText)findViewById(R.id.et_edit_address_postcode);
        rePickReceAddressCity = (LinearLayout)findViewById(R.id.repick_address_dialog);
        finishEditReceAddress = (Button)findViewById(R.id.finish_edit_address);
        displayAddress = (TextView)findViewById(R.id.display_address);

        backEditReceAddress.setOnClickListener(this);
        rePickReceAddressCity.setOnClickListener(this);
        finishEditReceAddress.setOnClickListener(this);
    }

    private void setDefaultData(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", Integer.MIN_VALUE);
        int userId = intent.getIntExtra("userId", Integer.MIN_VALUE);
        String receiver = intent.getStringExtra("receiver");
        String phone = intent.getStringExtra("phone");
        String province = intent.getStringExtra("province");
        String city = intent.getStringExtra("city");
        String district = intent.getStringExtra("district");
        String detailAddress = intent.getStringExtra("detailAddress");
        int postcode = intent.getIntExtra("postcode", Integer.MIN_VALUE);

        Log.i("wnw", id + " "  + userId + " " + receiver + " " + phone + " " + province + " " +city
        + " " +district + " " +detailAddress +" " + postcode);

        editReceiver.setText(receiver);
        editRecePhone.setText(phone);
        displayAddress.setText(province + " " + city + " " + district);
        editReceAddressHouseNum.setText(detailAddress);
        editPostcode.setText(postcode+ "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_edit_address:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.repick_address_dialog:

                break;
            case R.id.finish_edit_address:

                break;
            default:

                break;

        }
    }
}
