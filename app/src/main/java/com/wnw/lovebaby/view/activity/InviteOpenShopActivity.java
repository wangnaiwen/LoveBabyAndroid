package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.config.MyKeys;
import com.wnw.lovebaby.domain.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2016/12/13.
 */

public class InviteOpenShopActivity extends Activity implements View.OnClickListener{

    private ImageView backInviteOpenShop;
    private TextView inviteOpenShop;

    private int userId;
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_open_shop);
        getUser();
        initAPI();
        initView();
    }

    private void initAPI(){
        //微信分享初始化
        api = WXAPIFactory.createWXAPI(this, MyKeys.AppID, true);
        api.registerApp(MyKeys.AppID);
    }

    //get the current user
    private void getUser(){
        SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
        userId = preferences.getInt("id", 0);
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
                showShareDialog();
                break;
            default:

                break;
        }
    }

    AlertDialog shareDialog;
    private void showShareDialog(){
        final List<String> list = new ArrayList<>();
        String[] sharePlatform = new String[]{"微信","朋友圈", "QQ好友"};
        for(int  i = 0; i < sharePlatform.length; i++){
            list.add(sharePlatform[i]);
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        final LinearLayout linearLayout =(LinearLayout) inflater.inflate(R.layout.dialog_lv_platform_share,null);
        ListView listView = (ListView)linearLayout.findViewById(R.id.lv_dialog);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.dialog_lv_item, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openShare(i);
            }
        });

        shareDialog = new AlertDialog.Builder(this).create();
        shareDialog.setView(linearLayout);
        shareDialog.show();
    }

    //根据用户的选择来打开相应的
    private void openShare(int i){
        if(shareDialog.isShowing()){
            shareDialog.dismiss();
        }

        if(i == 0){
            shareToWxFriend();
        }else if(i == 2){
            shareToQQFriend();
        }else if(i == 1){
            shareToTimeLine();
        }
    }

    /**
     * 分享到QQ好友
     *
     */
    private void shareToQQFriend() {
        String text = "向大家推荐一个叫爱婴粑粑的APP，上面有很丰富的母婴产品，还有宝宝护理的文章，" +
                "还可以免费开店赚钱，我的邀请码是:"+userId;
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
        intent.setComponent(componentName);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(intent);
    }

    /**
     * 分享信息到朋友
     *
     */
    private void shareToWxFriend() {
        String text = "向大家推荐一个叫爱婴粑粑的APP，上面有很丰富的母婴产品，还有宝宝护理的文章，" +
                "还可以免费开店赚钱，我的邀请码是:"+userId;

        //初始化一个网页
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }

    private void shareToTimeLine() {

        String text = "向大家推荐一个叫爱婴粑粑的APP，上面有很丰富的母婴产品，还有宝宝护理的文章，" +
                "还可以免费开店赚钱，我的邀请码是:"+userId;

        //初始化一个网页
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
