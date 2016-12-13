package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wnw.lovebaby.R;

/**
 * Created by wnw on 2016/12/13.
 */

public class InviteOpenShopActivity extends Activity implements View.OnClickListener{

    private ImageView backInviteOpenShop;
    private TextView inviteOpenShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_open_shop);
        initView();
    }

    private void initView(){
        backInviteOpenShop = (ImageView)findViewById(R.id.back_invite_open_shop);
        inviteOpenShop = (TextView)findViewById(R.id.btn_invite_open_shop);

        backInviteOpenShop.setOnClickListener(this);
        inviteOpenShop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_invite_open_shop:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.btn_invite_open_shop:

                break;
            default:

                break;
        }
    }
}
