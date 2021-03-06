package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.config.MyKeys;
import com.wnw.lovebaby.domain.Article;
import com.wnw.lovebaby.model.modelInterface.IUpdateArticleLikeTimes;
import com.wnw.lovebaby.model.modelInterface.IUpdateArticleReadTimes;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.UpdateArticleLikeTimesPresenter;
import com.wnw.lovebaby.presenter.UpdateArticleReadTimesPresenter;
import com.wnw.lovebaby.presenter.ValidateArticleLikePresenter;
import com.wnw.lovebaby.view.viewInterface.IUpdateArticleLikeTimesView;
import com.wnw.lovebaby.view.viewInterface.IUpdateArticleReadTimesView;
import com.wnw.lovebaby.view.viewInterface.IValidateArticleLikeView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by wnw on 2017/5/11.
 */

public class ArticleDetailActivity extends Activity implements View.OnClickListener,
        IUpdateArticleReadTimesView,IUpdateArticleLikeTimesView, IValidateArticleLikeView{

    private ImageView back;
    private ImageView praiseImg;
    private TextView numTv;
    private WebView articleWv;
    private ImageView shareImg;

    private Article article;

    private int userId;

    private int praiseNum = 0;
    private IWXAPI api;

    private UpdateArticleLikeTimesPresenter updateArticleLikeTimesPresenter;
    private UpdateArticleReadTimesPresenter updateArticleReadTimesPresenter;
    private ValidateArticleLikePresenter validateArticleLikePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        initAPI();
        getArticle();
        getUserId();
        initView();
        initPresenter();
        startUpdateArticleReadTime();
        startValidateArticleLike();
    }

    private void initAPI(){
        //微信分享初始化
        api = WXAPIFactory.createWXAPI(this, MyKeys.AppID, true);
        api.registerApp(MyKeys.AppID);

    }

    private void getArticle(){
        Intent intent = getIntent();
        article = (Article)intent.getSerializableExtra("article");
    }

    private void getUserId(){
        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        userId = sharedPreferences.getInt("id", 0);
    }

    private void initView(){
        back = (ImageView)findViewById(R.id.back);
        numTv = (TextView)findViewById(R.id.article_praise_num);
        articleWv = (WebView)findViewById(R.id.wv_article);
        praiseImg = (ImageView)findViewById(R.id.article_praise_icon);
        shareImg = (ImageView)findViewById(R.id.img_share);

        WebSettings webSettings = articleWv.getSettings();
        webSettings.setJavaScriptEnabled(true); //支持js
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。 这个取决于setSupportZoom(), 若setSupportZoom(false)，则该WebView不可缩放，这个不管设置什么都不能缩放。
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口

        articleWv.loadUrl(article.getContent());
        back.setOnClickListener(this);
        praiseImg.setOnClickListener(this);
        shareImg.setOnClickListener(this);
        numTv.setText(article.getLikeTimes()+"");
    }

    private void initPresenter(){
        updateArticleLikeTimesPresenter = new UpdateArticleLikeTimesPresenter(this, this);
        updateArticleReadTimesPresenter = new UpdateArticleReadTimesPresenter(this, this);
        validateArticleLikePresenter = new ValidateArticleLikePresenter(this,this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                returnLastActivity();
                break;
            case R.id.article_praise_icon:
                if (praiseNum == 0){
                    startUpdateArticleLikeTime();
                }
                break;
            case R.id.img_share:
                showShareDialog();
                break;
        }
    }

    private void startUpdateArticleLikeTime(){

        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "请确认网络已打开", Toast.LENGTH_SHORT).show();
        }else {
            updateArticleLikeTimesPresenter.updateLikeTimes(userId, article.getId());
        }
    }

    private void startUpdateArticleReadTime(){
        updateArticleReadTimesPresenter.updateReadTimes(article.getId());
    }

    private void startValidateArticleLike(){
        validateArticleLikePresenter.validateArticleLike(userId, article.getId());
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
    public void showUpdateLikeTimesResult(boolean isSuccess) {
        dismissDialogs();
        if (isSuccess){
            praiseNum = 1;
        }
        praiseImg.setImageResource(R.drawable.ic_praised);
        numTv.setText((article.getLikeTimes()+1)+"");
    }

    @Override
    public void showUpdateReadTimesResult(boolean isSuccess) {
        dismissDialogs();
    }

    @Override
    public void showValidateArticleLikeResult(boolean isSuccess) {
        if (isSuccess){
            //说明已经点赞过了
            praiseImg.setImageResource(R.drawable.ic_praised);
            praiseImg.setClickable(false);
        }else {

        }
    }

    @Override
    public void onBackPressed() {
        returnLastActivity();
    }

    private void returnLastActivity(){
        Intent intent = new Intent();
        intent.putExtra("likeTimes" , praiseNum);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        validateArticleLikePresenter.setView(null);
        updateArticleLikeTimesPresenter.setView(null);
        updateArticleReadTimesPresenter.setView(null);
    }

    AlertDialog shareDialog;
    private void showShareDialog(){
        final List<String> list = new ArrayList<>();
        String[] sharePlatform = new String[]{"微信","朋友圈","QQ好友"};
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
            shareToWechat();
        }else if(i == 1){
            shareTimeLine();
        }else if(i == 2){
            shareToQQFriend();
        }
    }

    /**
     * 分享到QQ好友
     *
     */
    private void shareToQQFriend() {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
        intent.setComponent(componentName);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_TEXT, article.getTitle()+"：" +article.getContent());
        startActivity(intent);
    }


    //分享网页到微信
    private void shareToWechat(){
        //初始化一个网页
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = article.getContent();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = article.getTitle();
        msg.description = article.getTitle();
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.cover);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }

    //分享网页到朋友圈
    private void shareTimeLine(){
        //初始化一个网页
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = article.getContent();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = article.getTitle();
        msg.description = article.getTitle();
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.cover);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }
}
