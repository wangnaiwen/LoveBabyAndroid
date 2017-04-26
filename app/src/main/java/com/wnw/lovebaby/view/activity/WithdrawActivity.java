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
import com.wnw.lovebaby.domain.Withdraw;
import com.wnw.lovebaby.presenter.InsertWithdrawPresenter;
import com.wnw.lovebaby.util.TypeConverters;
import com.wnw.lovebaby.view.viewInterface.IInsertWithdrawView;

/**
 * Created by wnw on 2017/4/26.
 */

public class WithdrawActivity extends Activity implements View.OnClickListener,IInsertWithdrawView{

    private ImageView back;
    private TextView balanceTv;
    private EditText withdrawNumberEt;
    private TextView sureTv;

    private int userId;
    private int balance;

    private InsertWithdrawPresenter insertWithdrawPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        getBalance();
        initView();
        initPresenter();
    }

    private void getBalance(){
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        balance = intent.getIntExtra("balance", 0);
    }

    private void initView(){
        back = (ImageView)findViewById(R.id.back);
        balanceTv = (TextView)findViewById(R.id.tv_balance);
        withdrawNumberEt = (EditText)findViewById(R.id.et_withdraw_number);
        sureTv = (TextView)findViewById(R.id.tv_sure_withdraw);

        balanceTv.setText(TypeConverters.IntConvertToString(balance));

        back.setOnClickListener(this);
        sureTv.setOnClickListener(this);
    }

    private void initPresenter(){
        insertWithdrawPresenter = new InsertWithdrawPresenter(this, this);
    }

    private void startWithdraw(int money){
        Withdraw withdraw = new Withdraw();
        withdraw.setUserId(userId);
        withdraw.setMoney(money);
        insertWithdrawPresenter.insertWithdraw(withdraw);
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
    public void showInsertWithdrawResult(boolean isSuccess) {
        dismissDialogs();
        if(isSuccess){  //提现成功
            Toast.makeText(this, "提现成功", Toast.LENGTH_SHORT).show();
            balance = balance - Integer.parseInt(withdrawNumberEt.getText().toString().trim()) * 100;
            Intent intent = new Intent();
            intent.putExtra("balance", balance);
            setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else {
            Toast.makeText(this, "提现失败，请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.tv_sure_withdraw:
                if(balance < 10000){
                    Toast.makeText(this, "额度不足100元，不可以提现", Toast.LENGTH_SHORT).show();
                }else if(withdrawNumberEt.getText().toString().trim().isEmpty()){
                    withdrawNumberEt.setHint("请输入提现金额");
                }else if (Integer.parseInt(withdrawNumberEt.getText().toString().trim()) * 100 > balance){
                    Toast.makeText(this, "余额不足", Toast.LENGTH_SHORT).show();
                }else{
                    startWithdraw(Integer.parseInt(withdrawNumberEt.getText().toString().trim()) * 100);
                }
                break;
        }
    }
}
