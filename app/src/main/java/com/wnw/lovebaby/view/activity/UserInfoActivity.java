package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.domain.UserInfo;
import com.wnw.lovebaby.presenter.FindUserInfoPresenter;
import com.wnw.lovebaby.presenter.UpdateUserInfoPresenter;
import com.wnw.lovebaby.view.viewInterface.IFindUserInfoView;
import com.wnw.lovebaby.view.viewInterface.MvpBaseActivity;

import java.util.Calendar;

/**
 * Created by wnw on 2017/2/19.
 */

public class UserInfoActivity extends MvpBaseActivity<IFindUserInfoView, FindUserInfoPresenter>
        implements View.OnClickListener, IFindUserInfoView{
    private RelativeLayout nickNameView;
    private RelativeLayout sexView;
    private RelativeLayout emailView;
    private RelativeLayout birthdayView;
    private RelativeLayout hobbyView;
    private RelativeLayout signatureView;

    private ImageView backUserInfo;

    private TextView disNickName;
    private TextView disBirthday;
    private TextView disSex;
    private TextView saveInfo;

    private int userId;
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        getUserId();
    }

    private void initView(){
        nickNameView = (RelativeLayout)findViewById(R.id.user_info_nickname);
        sexView = (RelativeLayout)findViewById(R.id.user_info_sex);
        birthdayView = (RelativeLayout)findViewById(R.id.user_info_birthday);
        emailView = (RelativeLayout)findViewById(R.id.user_info_email);
        hobbyView = (RelativeLayout)findViewById(R.id.user_info_hobby);
        signatureView = (RelativeLayout)findViewById(R.id.user_info_signature);

        disNickName = (TextView)findViewById(R.id.dis_nickname);
        disSex = (TextView)findViewById(R.id.dis_sex);
        disBirthday = (TextView)findViewById(R.id.dis_birthday);
        saveInfo = (TextView)findViewById(R.id.save_info);

        backUserInfo = (ImageView)findViewById(R.id.user_info_back);

        nickNameView.setOnClickListener(this);
        sexView.setOnClickListener(this);
        birthdayView.setOnClickListener(this);
        emailView.setOnClickListener(this);
        hobbyView.setOnClickListener(this);
        signatureView.setOnClickListener(this);

        saveInfo.setOnClickListener(this);
        backUserInfo.setOnClickListener(this);
    }

    private void getUserId(){
        SharedPreferences sharedPreferences = getSharedPreferences("account",MODE_PRIVATE);
        userId = sharedPreferences.getInt("id", 0);

        mPresenter.findUserInfo(this, userId);
    }

    @Override
    public void showDialog() {
        Toast.makeText(this, "正在努力中...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void findUserInfo(UserInfo userInfo) {
        this.mUserInfo = userInfo;
        disNickName.setText(mUserInfo.getNickName());
        disSex.setText(mUserInfo.getSex());
        disBirthday.setText(mUserInfo.getBirthday());
    }

    @Override
    public void updateUserInfo(boolean isSuccess) {
        if (isSuccess){
            Toast.makeText(this, "成功保存修改", Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected FindUserInfoPresenter createPresenter() {
        return new FindUserInfoPresenter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_info_back:
                onBackPressed();
                break;
            case R.id.user_info_nickname:
                Intent intent = new Intent(this, EditNickNameActivity.class);
                intent.putExtra("nickName",mUserInfo.getNickName());
                startActivityForResult(intent, 0);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.user_info_birthday:
                showDatePickerDialog();
                break;
            case R.id.user_info_email:
                Intent intent1 = new Intent(this, EditEmailActivity.class);
                intent1.putExtra("email", mUserInfo.getEmail());
                startActivityForResult(intent1, 1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.user_info_sex:
                showSexDialog();
                break;
            case R.id.user_info_hobby:
                Intent intent2 = new Intent(this, EditHobbyActivity.class);
                intent2.putExtra("hobby", mUserInfo.getHobby());
                startActivityForResult(intent2,2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.user_info_signature:
                Intent intent3 = new Intent(this, EditSignatureActivity.class);
                intent3.putExtra("signature", mUserInfo.getPersonalizedSignature());
                startActivityForResult(intent3, 3);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.save_info:
                mPresenter.updateUserInfo(this, mUserInfo);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0:
                if(resultCode == RESULT_OK){
                    mUserInfo.setNickName(data.getStringExtra("nickName"));
                    disNickName.setText(mUserInfo.getNickName());
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    mUserInfo.setEmail(data.getStringExtra("email"));
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    mUserInfo.setHobby(data.getStringExtra("hobby"));
                }
                break;
            case 3:
                if(resultCode == RESULT_OK){
                    mUserInfo.setPersonalizedSignature(data.getStringExtra("signature"));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    public void showSexDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("你的性别");
        //    指定下拉列表的显示数据
        final String[] cities = {"男", "女"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                disSex.setText(cities[which]);
                mUserInfo.setSex(cities[which]);
            }
        });
        builder.show();
    }

    public void showDatePickerDialog(){
        Calendar c = Calendar.getInstance();
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(this,
                // 绑定监听器
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        disBirthday.setText(year+""+monthOfYear+""+dayOfMonth);
                        mUserInfo.setBirthday(year+""+monthOfYear+""+dayOfMonth);
                    }
                },
                // 设置初始日期
                c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        ).show();
    }
}
