package com.wnw.lovebaby.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.domain.User;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.FindPasswordPresenter;
import com.wnw.lovebaby.view.activity.MainActivity;
import com.wnw.lovebaby.view.viewInterface.IFindPasswordView;

/**
 * Created by wnw on 2017/3/7.
 */

public class ResetPasswordActivity  extends FindPasswordBaseActivity<IFindPasswordView, FindPasswordPresenter> implements
        View.OnClickListener, IFindPasswordView{
    /**定义一个PhoneNum用户存储在LoginGetCodesAty中Intent传递过来的手机号码，在初始化时完成*/
    private String phoneNum = null;

    private String password = null;

    /**定义一个JudgePasswdIsLegal对象，调用其passwdIsLegal(String passwd)方法输入密码是否合法*/
    private JudgePasswdIsLegal judgePasswdIsLegal;

    /**返回键*/
    private ImageView back_imgbtn;

    /**完成键*/
    private Button login_setpasswd_ok;

    /**password的EditTxt*/
    private EditText login_edit_setpasswd;

    /**password input again*/
    private EditText login_edit_setpasswd_again;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("phone");
        //phoneNum = getPhoneNum();
        init();
    }


    /**初始化组件，并且设置事件监听*/
    public void init(){

        //初始化judgePasswdIsLegal对象
        judgePasswdIsLegal = new JudgePasswdIsLegal();

        /**将本地的Activity添加到ActivityCollector中*/
        ActivityCollector.addActivity(this);

        back_imgbtn = (ImageView)findViewById(R.id.back_password_recover);
        login_setpasswd_ok = (Button)findViewById(R.id.password_recover_setpasswd_ok);
        login_edit_setpasswd = (EditText)findViewById(R.id.password_recover_edit_setpasswd);
        login_edit_setpasswd_again = (EditText)findViewById(R.id.password_recover_edit_setpasswd_again);

        back_imgbtn.setOnClickListener(this);
        login_setpasswd_ok.setOnClickListener(this);

        /**一旦用户输入发生改变，就调用passwdIsLegal()方法判断输入的密码格式是不是合法*/
        login_edit_setpasswd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                /**如果用户输入的合法，就设置login_setpasswd_ok为可点击的，并且设置背景颜色*/
                boolean isLegal = judgePasswdIsLegal.passwdIsLegal(s.toString());
                if (isLegal) {
                    login_setpasswd_ok.setClickable(true);
                    login_setpasswd_ok.setTextColor(getResources().getColor(R.color.text_color_yes));
                    login_setpasswd_ok.setBackgroundColor(getResources().getColor(R.color.btn_color_yes));
                } else {
                    login_setpasswd_ok.setClickable(false);
                    login_setpasswd_ok.setTextColor(getResources().getColor(R.color.text_color_no));
                    login_setpasswd_ok.setBackgroundColor(getResources().getColor(R.color.btn_color_no));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.password_recover_setpasswd_ok:
                if(judgePasswdEqual()){
                    startFindPassword();
                }else{
                    Toast.makeText(this, "输入的两次密码不一致",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.back_password_recover:
                finish();
                break;

            default:
                break;
        }
    }

    //找回密码
    private void startFindPassword(){
        if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "网络不可用",Toast.LENGTH_SHORT).show();
        }else{
            mPresenter.findPassword(this, phoneNum, login_edit_setpasswd.getText().toString().trim());
        }
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


    /**
     * 判断两次密码输入是否相同
     *
     * */
    private boolean judgePasswdEqual(){
        return login_edit_setpasswd.getText().toString().trim().equals(login_edit_setpasswd_again.getText().toString().trim());
    }

    /**将用户的手机号码和密码保存起来,从LoginGetCodesAty方法中传递过来的phoneNums*/
    private void savePassword(){
        Intent intent = new Intent();
        phoneNum = intent.getStringExtra("phone");
        password = login_edit_setpasswd.getText().toString();
        Log.d("wnw",phoneNum + " "+password);
    }

    /**在finish()方法中，将Activity从activityCollector中删除*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    protected FindPasswordPresenter createPresenter() {
        return new FindPasswordPresenter();
    }

    @Override
    public void findPassword(User user) {
        dismissDialogs();
        if(user == null){
            Toast.makeText(this, "找回密码失败！", Toast.LENGTH_SHORT).show();
        }else {
            this.user = user;
            Toast.makeText(this, "成功设置密码！", Toast.LENGTH_SHORT).show();
            ActivityCollector.finishAllActivity();
            Intent intent = new Intent(ResetPasswordActivity.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void showDialog() {
        showDialogs();
    }
}
