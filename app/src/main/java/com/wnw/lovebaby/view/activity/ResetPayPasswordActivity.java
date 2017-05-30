package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.domain.Wallet;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.UpdateWalletPasswordPresenter;
import com.wnw.lovebaby.presenter.ValiteWalletPresenter;
import com.wnw.lovebaby.view.viewInterface.IUpdateWalletPasswordView;
import com.wnw.lovebaby.view.viewInterface.IValiteWalletView;

/**
 * Created by wnw on 2017/5/29.
 */

public class ResetPayPasswordActivity extends Activity implements View.OnClickListener,
        IUpdateWalletPasswordView, IValiteWalletView{


    private ImageView back;
    private EditText oldPasswordEt;
    private EditText newPasswrodEt;
    private TextView finishTv;

    private int userId;
    private Wallet wallet ;

    private UpdateWalletPasswordPresenter updateWalletPasswordPresenter;
    private ValiteWalletPresenter valiteWalletPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pay_password);
        getData();
        initView();
        initPresenter();
    }

    private void getData(){
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
    }

    private void initView(){
        back = (ImageView)findViewById(R.id.back);
        oldPasswordEt = (EditText)findViewById(R.id.et_old_password);
        newPasswrodEt = (EditText)findViewById(R.id.et_new_password);
        finishTv = (TextView)findViewById(R.id.tv_finish);

        back.setOnClickListener(this);
        finishTv.setOnClickListener(this);
    }

    private void initPresenter(){
        updateWalletPasswordPresenter = new UpdateWalletPasswordPresenter(this, this);
        valiteWalletPresenter = new ValiteWalletPresenter(this, this);
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
    public void updateWalletPasswordResult(boolean isSuccess) {
        dismissDialogs();
        if (isSuccess){
            Toast.makeText(this, "密码更新成功", Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else {
            Toast.makeText(this, "密码更新失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void valiteWalletResult(boolean isSuccess) {
        if (isSuccess){
            updatePassword();
        }else {
            oldPasswordEt.setText("");
            oldPasswordEt.setHint("原密码验证错误");
            oldPasswordEt.setHintTextColor(Color.RED);
            dismissDialogs();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.tv_finish:
                if (oldPasswordEt.getText().toString().trim().isEmpty()){
                    oldPasswordEt.setHint("请输入旧密码");
                    oldPasswordEt.setHintTextColor(Color.RED);
                }else if (newPasswrodEt.getText().toString().trim().isEmpty()){
                    newPasswrodEt.setHint("请输入新6位数字密码");
                    newPasswrodEt.setHintTextColor(Color.RED);
                }else if (newPasswrodEt.getText().toString().trim().length() != 6){
                    Toast.makeText(this, "请输入6位数字的新密码",Toast.LENGTH_SHORT).show();
                }else{
                    validatePassword();
                }
                break;
        }
    }

    private void validatePassword(){
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
        }else {
            valiteWalletPresenter.valiteWallet(userId, oldPasswordEt.getText().toString().trim());
        }
    }

    private void updatePassword(){
       updateWalletPasswordPresenter.updateWalletPassword(userId, Integer.parseInt(newPasswrodEt.getText().toString().trim()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
