package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;

import org.w3c.dom.Text;

/**
 * Created by wnw on 2017/2/18.
 */

public class SettingActivity extends Activity implements View.OnClickListener{

    private ImageView backSetting;
    private RelativeLayout checkMyReceAddress;
    private RelativeLayout checkMyShop;
    private RelativeLayout aboutVersion;
    private RelativeLayout aboutLoveBaby;
    private TextView exitLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView(){
        backSetting = (ImageView)findViewById(R.id.setting_back);
        checkMyReceAddress = (RelativeLayout)findViewById(R.id.my_rece_address);
        checkMyShop = (RelativeLayout)findViewById(R.id.my_shop);
        aboutLoveBaby = (RelativeLayout)findViewById(R.id.about_lovebaby);
        aboutVersion = (RelativeLayout)findViewById(R.id.about_version);
        exitLogin = (TextView)findViewById(R.id.exit_login);

        backSetting.setOnClickListener(this);
        checkMyReceAddress.setOnClickListener(this);
        checkMyShop.setOnClickListener(this);
        aboutLoveBaby.setOnClickListener(this);
        aboutVersion.setOnClickListener(this);
        exitLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.my_rece_address:
                Intent intent1 = new Intent(this, AddressListActivity.class);
                intent1.putExtra("lastAty", 1);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.my_shop:

                break;
            case R.id.about_lovebaby:
                Intent intent = new Intent(this, AboutLoveBabyActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.about_version:
                Toast.makeText(this, "当前已经是最新版本", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit_login:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
