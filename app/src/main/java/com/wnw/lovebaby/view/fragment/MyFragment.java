package com.wnw.lovebaby.view.fragment;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.view.activity.SettingActivity;
import com.wnw.lovebaby.view.activity.UserInfoActivity;

/**
 * Created by wnw on 2016/12/1.
 */

public class MyFragment extends Fragment implements View.OnClickListener{

    private View mView;
    private LayoutInflater mInflater;
    private Context mContext;

    private ImageView settingView;
    private ImageView editMyInfoViewView;
    private RelativeLayout checkAllOrdersView;
    private LinearLayout bePayView;
    private LinearLayout beSentView;
    private LinearLayout beReceivedView;
    private LinearLayout beEvaluatedView;
    private LinearLayout rerundsView;
    private ImageView joinUsView;
    private TextView disPhone;

    private String phone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my, container, false);
        mContext = inflater.getContext();
        mInflater = inflater;
        initView();
        getUserLoginInfo();
        return mView;
    }

    private void initView(){
        settingView = (ImageView)mView.findViewById(R.id.mine_setting);
        editMyInfoViewView = (ImageView)mView.findViewById(R.id.mine_edit_my_info);
        checkAllOrdersView = (RelativeLayout)mView.findViewById(R.id.mine_all_order);
        bePayView = (LinearLayout)mView.findViewById(R.id.mine_be_pad);
        beSentView = (LinearLayout)mView.findViewById(R.id.mine_be_sent);
        beReceivedView = (LinearLayout)mView.findViewById(R.id.mine_be_received);
        beEvaluatedView = (LinearLayout)mView.findViewById(R.id.mine_be_evaluated);
        rerundsView = (LinearLayout)mView.findViewById(R.id.mine_refunds);
        joinUsView = (ImageView)mView.findViewById(R.id.mine_join_us);
        disPhone = (TextView)mView.findViewById(R.id.mine_dis_phone);

        settingView.setOnClickListener(this);
        editMyInfoViewView.setOnClickListener(this);
        checkAllOrdersView.setOnClickListener(this);
        bePayView.setOnClickListener(this);
        beSentView.setOnClickListener(this);
        beReceivedView.setOnClickListener(this);
        beEvaluatedView.setOnClickListener(this);
        rerundsView.setOnClickListener(this);
        joinUsView.setOnClickListener(this);
    }

    private void getUserLoginInfo(){
        SharedPreferences preferences = mContext.getSharedPreferences("account", mContext.MODE_PRIVATE);
        phone = preferences.getString("phone","");
        disPhone.setText(phone);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                ((Activity)mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.mine_edit_my_info:
                startActivity(new Intent(mContext, UserInfoActivity.class));
                ((Activity)mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.mine_all_order:

                break;
            case R.id.mine_be_pad:

                break;
            case R.id.mine_be_sent:

                break;
            case R.id.mine_be_received:

                break;
            case R.id.mine_be_evaluated:

                break;
            case R.id.mine_refunds:

                break;
            case R.id.mine_join_us:

                break;

        }
    }
}
