package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wnw.lovebaby.R;

/**
 * Created by wnw on 2016/12/13.
 */

public class AddReceAddressActivity extends Activity implements View.OnClickListener {

    private ImageView backAddReceAddress;
    private EditText addReceiver;
    private EditText addRecePhone;
    private LinearLayout pickReceAddressCity;
    private EditText addReceAddressHouseNum;
    private Button finishAddReceAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        intiView();
    }

    private void intiView(){
        backAddReceAddress = (ImageView)findViewById(R.id.back_add_address);
        addReceiver= (EditText)findViewById(R.id.et_add_address_receiver);
        addRecePhone = (EditText)findViewById(R.id.et_add_address_phone);
        addReceAddressHouseNum = (EditText)findViewById(R.id.et_add_address_house_num);
        pickReceAddressCity = (LinearLayout)findViewById(R.id.pick_address_dialog);
        finishAddReceAddress = (Button)findViewById(R.id.finish_add_address);

        backAddReceAddress.setOnClickListener(this);
        pickReceAddressCity.setOnClickListener(this);
        finishAddReceAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_add_address:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.pick_address_dialog:

                break;
            case R.id.finish_add_address:

                break;
            default:

                break;

        }
    }
}
