package com.wnw.lovebaby.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.domain.User;
import com.wnw.lovebaby.presenter.LoginPresenter;
import com.wnw.lovebaby.view.activity.MainActivity;
import com.wnw.lovebaby.view.viewInterface.ILoginView;
import com.wnw.lovebaby.view.viewInterface.MvpBaseActivity;

/**
 * Created by wnw on 2016/10/17.88498646
 */

public class LoginActivity extends MvpBaseActivity<ILoginView, LoginPresenter> implements
        View.OnClickListener, ILoginView{

    private EditText phone;
    private EditText password;
    private Button login;
    private TextView newUser;

    private TextView forgetPasswd;

    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }


    private void initView(){

        ActivityCollector.addActivity(this);

        phone = (EditText)findViewById(R.id.login_phone);
        password = (EditText)findViewById(R.id.login_password);
        login = (Button)findViewById(R.id.btn_login);
        newUser = (TextView)findViewById(R.id.login_new_user);
        forgetPasswd = (TextView)findViewById(R.id.login_forget_passwd);

        login.setOnClickListener(this);
        newUser.setOnClickListener(this);
        forgetPasswd.setOnClickListener(this);

        user = new User();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                if(validateEditText()){
                    Toast.makeText(this, "手机和密码都不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    //验证密码
                    user.setPhone(phone.getText().toString().trim());
                    user.setPassword(password.getText().toString().trim());
                    mPresenter.validate(this, user);   //开始获得数据
                }
                break;
            case R.id.login_new_user:
                Intent intent = new Intent(this, RegisterEditNumAty.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.login_forget_passwd:
                Intent intent1= new Intent(this, RecoverPasswordActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            default:
                break;
        }
    }

    /**
     * 验证两个EditText是否都已经不为空了
     * */
    private boolean validateEditText(){
        return phone.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty();
    }

    private void openMainAty(){

        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra("netUser",netUser);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    public void showDialog() {
        //在这里显示进度条
        Toast.makeText(this, "正在拼命登录中", Toast.LENGTH_SHORT).show();
    }

    private void saveAccount(){
        SharedPreferences.Editor editor = getSharedPreferences("account",
                MODE_PRIVATE).edit();
        editor.clear();
        /*editor.putInt("id", netUser.getId());
        editor.putString("name", netUser.getName());
        editor.putString("phone", netUser.getPhone());
        editor.putString("password", netUser.getPassword());
        editor.putString("phone2", netUser.getPhone2());
        editor.putString("email", netUser.getEmail());
        editor.apply();*/
    }

    @Override
    public void validate(User user) {
        //在这里得到返回的数据

        if(user == null){
            Toast.makeText(this, "手机或密码错误", Toast.LENGTH_SHORT).show();
        }else {
            this.user = user;
            Log.d("wnwUser",user.getId() + " "+ user.getPhone() + " " + user.getPassword() + user.getType());
            Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
            /**
             * 保存这个账号的SharePreference
             * */
            //saveAccount();
            openMainAty();
        }
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }
}
