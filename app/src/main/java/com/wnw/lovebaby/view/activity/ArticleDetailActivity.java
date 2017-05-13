package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.domain.Article;
import com.wnw.lovebaby.model.modelInterface.IUpdateArticleLikeTimes;
import com.wnw.lovebaby.model.modelInterface.IUpdateArticleReadTimes;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.UpdateArticleLikeTimesPresenter;
import com.wnw.lovebaby.presenter.UpdateArticleReadTimesPresenter;
import com.wnw.lovebaby.view.viewInterface.IUpdateArticleLikeTimesView;
import com.wnw.lovebaby.view.viewInterface.IUpdateArticleReadTimesView;


/**
 * Created by wnw on 2017/5/11.
 */

public class ArticleDetailActivity extends Activity implements View.OnClickListener,
        IUpdateArticleReadTimesView,IUpdateArticleLikeTimesView{

    private ImageView back;
    private ImageView praiseImg;
    private TextView numTv;
    private WebView articleWv;

    private Article article;

    private int praiseNum = 0;

    private UpdateArticleLikeTimesPresenter updateArticleLikeTimesPresenter;
    private UpdateArticleReadTimesPresenter updateArticleReadTimesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        getArticle();
        initView();
        initPresenter();
        startUpdateArticleReadTime();
    }

    private void getArticle(){
        Intent intent = getIntent();
        article = (Article)intent.getSerializableExtra("article");
    }

    private void initView(){
        back = (ImageView)findViewById(R.id.back);
        numTv = (TextView)findViewById(R.id.article_praise_num);
        articleWv = (WebView)findViewById(R.id.wv_article);
        praiseImg = (ImageView)findViewById(R.id.article_praise_icon);

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

        numTv.setText(article.getLikeTimes()+"");
    }

    private void initPresenter(){
        updateArticleLikeTimesPresenter = new UpdateArticleLikeTimesPresenter(this, this);
        updateArticleReadTimesPresenter = new UpdateArticleReadTimesPresenter(this, this);
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
        }
    }

    private void startUpdateArticleLikeTime(){
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "请确认网络已打开", Toast.LENGTH_SHORT).show();
        }else {
            updateArticleLikeTimesPresenter.updateLikeTimes(article.getId());
        }
    }

    private void startUpdateArticleReadTime(){
        updateArticleReadTimesPresenter.updateReadTimes(article.getId());
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showUpdateLikeTimesResult(boolean isSuccess) {
        if (isSuccess){
            praiseNum = 1;
        }
        praiseImg.setImageResource(R.drawable.ic_praised);
        numTv.setText((article.getLikeTimes()+1)+"");
    }

    @Override
    public void showUpdateReadTimesResult(boolean isSuccess) {

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
}
