package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.login.ActivityCollector;
import com.wnw.lovebaby.login.LoginActivity;

import org.w3c.dom.Text;

/**
 * Created by wnw on 2017/2/18.
 */

public class SettingActivity extends Activity implements View.OnClickListener{

    private ImageView backSetting;
    private RelativeLayout checkMyReceAddress;
    private RelativeLayout aboutVersion;
    private RelativeLayout aboutLoveBaby;
    private TextView exitLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ActivityCollector.addActivity(this);
        initView();
    }

    private void initView(){
        backSetting = (ImageView)findViewById(R.id.setting_back);
        checkMyReceAddress = (RelativeLayout)findViewById(R.id.my_rece_address);
        aboutLoveBaby = (RelativeLayout)findViewById(R.id.about_lovebaby);
        aboutVersion = (RelativeLayout)findViewById(R.id.about_version);
        exitLogin = (TextView)findViewById(R.id.exit_login);
        backSetting.setOnClickListener(this);
        checkMyReceAddress.setOnClickListener(this);
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
            case R.id.about_lovebaby:
                Intent intent = new Intent(this, AboutLoveBabyActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.about_version:
                Toast.makeText(this, "当前已经是最新版本", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit_login:
                showExitDialog();
                break;
        }
    }


    /**
     * show the dialog of delete the address
     * */
    private void showExitDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("退出账号");
        builder.setMessage("是否退成当前账号？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishLogin();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog deleteDialog = builder.create();
        deleteDialog.show();
    }

    //退出登录，清除保存登录账号的信息
    public void finishLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCollector.finishAllActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
