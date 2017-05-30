package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.domain.Wallet;
import com.wnw.lovebaby.presenter.FindWalletMoneyByUserIdPresenter;
import com.wnw.lovebaby.util.TypeConverters;
import com.wnw.lovebaby.view.viewInterface.IFindWalletMoneyByUserIdView;

/**
 * Created by wnw on 2017/4/7.
 */

public class WalletActivity extends Activity implements View.OnClickListener, IFindWalletMoneyByUserIdView{

    private ImageView back;
    private TextView moneyTv;

    private Wallet wallet;
    private int userId;
    private int money; //余额

    private RelativeLayout resetPasswordRl;
    private FindWalletMoneyByUserIdPresenter findWalletMoneyByUserIdPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        getUserId();
        initView();
        initPresenter();
        findWallet();
    }

    private void initView(){
        back = (ImageView)findViewById(R.id.back_wallet);
        moneyTv = (TextView)findViewById(R.id.dis_money);
        resetPasswordRl = (RelativeLayout)findViewById(R.id.rl_reset_password);

        back.setOnClickListener(this);
        resetPasswordRl.setOnClickListener(this);
    }

    private void initPresenter(){
        findWalletMoneyByUserIdPresenter = new FindWalletMoneyByUserIdPresenter(this, this);
    }

    private void getUserId(){
        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        userId = sharedPreferences.getInt("id",0);
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

    private void findWallet(){
        findWalletMoneyByUserIdPresenter.findWalletMoneyByUserId(userId);
    }

    @Override
    public void showFindWalletMoneyByUserIdResult(int result) {
        dismissDialogs();
        money = result;
        moneyTv.setText(TypeConverters.LongConvertToString(result));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_wallet:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.rl_reset_password:
                Intent intent = new Intent(this, ResetPayPasswordActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
