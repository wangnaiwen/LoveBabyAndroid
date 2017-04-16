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
import com.wnw.lovebaby.presenter.RegisterPresenter;
import com.wnw.lovebaby.view.activity.MainActivity;
import com.wnw.lovebaby.view.viewInterface.IRegisterView;
import com.wnw.lovebaby.view.viewInterface.RegisterBaseActivity;

/**
 * Created by wangnainwen on 2016/3/26.
 * 这个类是由LoginGetCodesAty跳转而来
 * 主要完成以下功能：
 * 1. 判断用户设置的密码是否合适，不合适，完成按钮为不可点击，合适就变为可点击
 * 2. 如果增加用户二次输入密码，判断用户两次输入的密码是否一致
 * 3. 在这里需要将用户的密码进行加工，转移到另一个类中存入数据库，或者直接调入数据库
 * 4. 完成用户的按钮接听工作
 */
public class LoginSetPasswdAty extends RegisterBaseActivity<IRegisterView, RegisterPresenter> implements
        View.OnClickListener, IRegisterView{
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
        setContentView(R.layout.activity_register_set_passwd);

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

        back_imgbtn = (ImageView)findViewById(R.id.back_imgbtn);
        login_setpasswd_ok = (Button)findViewById(R.id.login_setpasswd_ok);
        login_edit_setpasswd = (EditText)findViewById(R.id.login_edit_setpasswd);
        login_edit_setpasswd_again = (EditText)findViewById(R.id.login_edit_setpasswd_again);

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
            case R.id.login_setpasswd_ok:
                /**一旦用户输入密码成功，有以下工作需要完成：
                 * 0. 判断两个密码是否相同
                 * 1. 将用户的手机号码和密码存储起来，对密码进行相应的加工
                 * 2. 调用ActivityCollector中的finishAllActivity()方法，将用户注册这三个步骤需要的
                 *     所有Activity进行销毁
                 * 3.  打开登陆界面，进行用户登录*/
                if(judgePasswdEqual()){
                    user = new User();
                    user.setPhone(phoneNum);
                    user.setPassword(login_edit_setpasswd.getText().toString().trim());
                    startRegisterPresenter();

                }else{
                    Toast.makeText(this, "输入的两次密码不一致",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.back_imgbtn:
                finish();
                break;

            default:
                break;
        }
    }

    //开始调用Presenter去注册
    private void startRegisterPresenter(){
        if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "网络不可用",Toast.LENGTH_SHORT).show();
        }else{
            mPresenter.register(this, user);
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
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
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
    public void register(boolean isSuccess) {
        dismissDialogs();
        if(isSuccess){
            Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
            ActivityCollector.finishAllActivity();
            Intent intent = new Intent(LoginSetPasswdAty.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }else {
            Toast.makeText(this, "注册失败！", Toast.LENGTH_SHORT).show();
        }
    }
}
