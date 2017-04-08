package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.wnw.lovebaby.presenter.FindPrsByProductIdPresenter;
import com.wnw.lovebaby.view.viewInterface.IFindPrsByProductIdView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**
 * Created by wnw on 2017/4/2.
 */

public class ProductReviewActivity extends Activity implements
        IFindPrsByProductIdView,View.OnClickListener{

    private ImageView mBack;     //返回键
    private ListView mReviewLv;  //评论列表
   // private ProgressBar mProgressBar;  //加载更多评论Progress
    private TextView mNullReviewTv;    //评论为空

    private List<Pr> prList;
    private ReviewAdapter reviewAdapter;

    private int productId;
    private int page = 1;   //第一页

    //上一次加载出来的数据大小，够不够二十条，不够说明已经到底，反之没有到底
    private int lastReturnSize = 20;

    private FindPrsByProductIdPresenter findPrsByProductIdPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_review);
        Intent intent = getIntent();
        productId = intent.getIntExtra("productId",0);
        initView();
        findPrsByProductIdPresenter = new FindPrsByProductIdPresenter(this,this);
        findPrsByProductIdPresenter.findPrsByProductId(productId,page);
    }

    /**
     * 初始化界面
     * */
    ProgressDialog progressDialog;
    private void initView(){
        mBack = (ImageView)findViewById(R.id.back_review);
        mReviewLv = (ListView)findViewById(R.id.lv_product_review);
        //mProgressBar = (ProgressBar)findViewById(R.id.load_more_review);
        mNullReviewTv = (TextView)findViewById(R.id.null_review);

        mBack.setOnClickListener(this);
        progressDialog = new ProgressDialog(ProductReviewActivity.this);
        progressDialog.setTitle("正在加载更多...");
        mReviewLv.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){
                // 当不滚动时
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        //加载更多功能的代码
                        //监听ListView滑动到底部，加载更多评论
                        if(lastReturnSize != 20){  //到底
                            Toast.makeText(ProductReviewActivity.this,"已经到底了",Toast.LENGTH_SHORT).show();
                        }else {
                            if(!progressDialog.isShowing()){
                                progressDialog.show();
                            }
                            findPrsByProductIdPresenter.findPrsByProductId(productId, page);
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    @Override
    public void showDialog() {
        Toast.makeText(this, "正在努力加载...",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPrs(List<Pr> prs) {
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }

        if (prs != null ) {
            if (page == 1){  //第一次加载
                mNullReviewTv.setVisibility(GONE);
                this.prList = prs;
                reviewAdapter = new ReviewAdapter(this, prList);
                mReviewLv.setAdapter(reviewAdapter);
            }else {
                int length = prs.size();
                for (int i = 0; i <length; i++){
                    this.prList.add(prs.get(i));
                }
                reviewAdapter.setPrList(this.prList);
                reviewAdapter.notifyDataSetChanged();
            }
            lastReturnSize = prList.size();
            page = page + 1;
        } else if( prList == null){
            mNullReviewTv.setVisibility(View.VISIBLE);
        }
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
