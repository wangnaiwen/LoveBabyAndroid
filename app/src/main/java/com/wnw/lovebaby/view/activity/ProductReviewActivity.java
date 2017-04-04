package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.ReviewAdapter;
import com.wnw.lovebaby.domain.Pr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/2.
 */

public class ProductReviewActivity extends Activity implements View.OnClickListener{

    private ImageView mBack;     //返回键
    private ListView mReviewLv;  //评论列表
   // private ProgressBar mProgressBar;  //加载更多评论Progress
    private TextView mNullReviewTv;    //评论为空

    private List<Pr> prList;
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_review);
        initView();
        initAdapter();
    }

    /**
     * 初始化界面
     * */
    private void initView(){
        mBack = (ImageView)findViewById(R.id.back_review);
        mReviewLv = (ListView)findViewById(R.id.lv_product_review);
        //mProgressBar = (ProgressBar)findViewById(R.id.load_more_review);
        mNullReviewTv = (TextView)findViewById(R.id.null_review);

        mBack.setOnClickListener(this);
    }


    /**
     *  初始化数据和Adapter
     * */
    private void initAdapter(){
        prList = new ArrayList<Pr>();

        for(int i = 0; i < 20; i ++){
            Pr pr = new Pr();
            pr.setDealId(123);
            pr.setId(123);
            pr.setProductId(123);
            pr.setTime("2016-04-02");
            pr.setProductScore(5);
            pr.setServiceScore(4);
            pr.setLogisticsScore(1);
            pr.setEvalution("真的不错，很好，女朋友很喜欢，宝宝很喜欢");
            pr.setUserId(123);
            pr.setUserNickName("小王");
            prList.add(pr);
        }
        if(prList.size() == 0){
            mNullReviewTv.setVisibility(View.VISIBLE);
        }
        reviewAdapter = new ReviewAdapter(this, prList);
        mReviewLv.setAdapter(reviewAdapter);

        mReviewLv.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){
                // 当不滚动时
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        //加载更多功能的代码
                        //监听ListView滑动到底部，加载更多评论
                        ProgressDialog progressDialog = new ProgressDialog(ProductReviewActivity.this);
                        progressDialog.setTitle("正在加载更多");
                        progressDialog.show();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    /**
     * Set Adapter
     * */
    private void setAdapter(){
        reviewAdapter.setPrList(prList);
        reviewAdapter.notifyDataSetChanged();
    }

    /**
     * 监听点击事件
     * */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_review:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;

        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
