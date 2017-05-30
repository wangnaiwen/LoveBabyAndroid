package com.wnw.lovebaby.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.domain.UserInfo;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.FindUserImgPresenter;
import com.wnw.lovebaby.view.activity.ImgUploadActivity;
import com.wnw.lovebaby.view.activity.MyOrderActivity;
import com.wnw.lovebaby.view.activity.QuitOrdersActivity;
import com.wnw.lovebaby.view.activity.SettingActivity;
import com.wnw.lovebaby.view.activity.UserInfoActivity;
import com.wnw.lovebaby.view.activity.WalletActivity;
import com.wnw.lovebaby.view.viewInterface.IFindUserImgView;

/**
 * Created by wnw on 2016/12/1.
 */

public class MyFragment extends Fragment implements View.OnClickListener, IFindUserImgView{

    private View mView;
    private LayoutInflater mInflater;
    private Context mContext;

    private ImageView userImg;
    private ImageView settingView;
    private ImageView editMyInfoViewView;
    private RelativeLayout checkAllOrdersView;
    private RelativeLayout bePayView;
    private RelativeLayout beSentView;
    private RelativeLayout beReceivedView;
    private RelativeLayout beEvaluatedView;
    private RelativeLayout rerundsView;
    private RelativeLayout walletView;
    private TextView disPhone;

    private int id;
    private String phone;
    private UserInfo userInfo;

    private FindUserImgPresenter findUserImgPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my, container, false);
        mContext = inflater.getContext();
        mInflater = inflater;
        initView();
        initPresenter();
        getUserLoginInfo();
        startFindUserInfo();
        return mView;
    }

    private void initView(){
        userImg = (ImageView)mView.findViewById(R.id.mine_icon_user);
        settingView = (ImageView)mView.findViewById(R.id.mine_setting);
        editMyInfoViewView = (ImageView)mView.findViewById(R.id.mine_edit_my_info);
        checkAllOrdersView = (RelativeLayout)mView.findViewById(R.id.mine_all_order);
        bePayView = (RelativeLayout)mView.findViewById(R.id.mine_be_pad);
        beSentView = (RelativeLayout)mView.findViewById(R.id.mine_be_sent);
        beReceivedView = (RelativeLayout)mView.findViewById(R.id.mine_be_received);
        beEvaluatedView = (RelativeLayout)mView.findViewById(R.id.mine_be_evaluated);
        rerundsView = (RelativeLayout)mView.findViewById(R.id.mine_refunds);
        walletView = (RelativeLayout)mView.findViewById(R.id.mine_my_wallet);
        disPhone = (TextView)mView.findViewById(R.id.mine_dis_phone);

        userImg.setOnClickListener(this);
        settingView.setOnClickListener(this);
        editMyInfoViewView.setOnClickListener(this);
        checkAllOrdersView.setOnClickListener(this);
        bePayView.setOnClickListener(this);
        beSentView.setOnClickListener(this);
        beReceivedView.setOnClickListener(this);
        beEvaluatedView.setOnClickListener(this);
        rerundsView.setOnClickListener(this);
        walletView.setOnClickListener(this);
    }

    private void initPresenter(){
        findUserImgPresenter = new FindUserImgPresenter(mContext,this);
    }

    private void getUserLoginInfo(){
        SharedPreferences preferences = mContext.getSharedPreferences("account", mContext.MODE_PRIVATE);
        id = preferences.getInt("id", 0);
        phone = preferences.getString("phone","");
        disPhone.setText(phone);
    }

    @Override
    public void showDialog() {
        showDialogs();
    }

    ProgressDialog dialog = null;
    private void showDialogs(){
        if(dialog == null){
            dialog = new ProgressDialog(mContext);
            dialog.setMessage("正在努力中...");
        }
        dialog.show();
    }

    private void dismissDialogs(){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    private void startFindUserInfo(){
        if (NetUtil.getNetworkState(mContext) == NetUtil.NETWORN_NONE){
            Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
        }else {
            findUserImgPresenter.findUserInfo(id);
        }
    }

    @Override
    public void showUserInfo(UserInfo userInfo) {
        dismissDialogs();
        if (userInfo == null){
            this.userInfo = new UserInfo();
            userImg.setImageResource(R.drawable.user);
        }else{
            this.userInfo = userInfo;
            Glide.with(mContext).load(userInfo.getUserImg()).error(R.drawable.user).into(userImg);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_icon_user:
                Intent intent = new Intent(mContext, ImgUploadActivity.class);
                intent.putExtra("userId", id);
                intent.putExtra("url", userInfo.getUserImg());
                startActivityForResult(intent, 100);
                ((Activity)mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.mine_setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                ((Activity)mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.mine_edit_my_info:
                startActivity(new Intent(mContext, UserInfoActivity.class));
                ((Activity)mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.mine_all_order:
                openMyOrderActivity(0);
                break;
            case R.id.mine_be_pad:
                openMyOrderActivity(1);
                break;
            case R.id.mine_be_sent:
                openMyOrderActivity(2);
                break;
            case R.id.mine_be_received:
                openMyOrderActivity(3);
                break;
            case R.id.mine_be_evaluated:
                openMyOrderActivity(4);
                break;
            case R.id.mine_refunds:
                Intent intent2 = new Intent(mContext, QuitOrdersActivity.class);
                startActivity(intent2);
                ((Activity)mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.mine_my_wallet:
                Intent intent1 = new Intent(mContext, WalletActivity.class);
                startActivity(intent1);
                ((Activity)mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == Activity.RESULT_OK){
            userInfo.setUserImg(data.getStringExtra("url"));
            Glide.with(mContext).load(userInfo.getUserImg()).error(R.drawable.user).into(userImg);
        }
    }

    private void openMyOrderActivity(int position){
        Intent intent = new Intent(getContext(), MyOrderActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
        ((Activity)getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
