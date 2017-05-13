package com.wnw.lovebaby.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.ArticleAdapter;
import com.wnw.lovebaby.domain.Article;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.FindAllArticlePresenter;
import com.wnw.lovebaby.view.activity.ArticleDetailActivity;
import com.wnw.lovebaby.view.viewInterface.IFindAllArticleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/5/9.
 */

public class ArticleFragment extends Fragment implements AdapterView.OnItemClickListener,
        AbsListView.OnScrollListener,
        View.OnClickListener, IFindAllArticleView{

    private View view;
    private Context context;
    private LayoutInflater mInflater;
    private ListView listView;
    private TextView nullArticleTv;

    private List<Article> articleList = new ArrayList<>();
    private int page = 1;
    private boolean isEnd = false;

    private FindAllArticlePresenter findAllArticlePresenter;
    private ArticleAdapter articleAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_article, container, false);
        this.context = inflater.getContext();
        this.mInflater = inflater;
        initView();
        initPresenter();
        findArticle();
        return view;
    }

    private void initView(){
        listView = (ListView)view.findViewById(R.id.college_lv);
        nullArticleTv = (TextView)view.findViewById(R.id.null_article);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);
        nullArticleTv.setOnClickListener(this);
    }

    private void initPresenter(){
        findAllArticlePresenter = new FindAllArticlePresenter(context,this);
    }

    private void findArticle(){
        if (NetUtil.getNetworkState(context) == NetUtil.NETWORN_NONE){
            nullArticleTv.setVisibility(View.VISIBLE);
        }else {
            findAllArticlePresenter.findAllArticles(page);
        }
    }

    @Override
    public void showDialog() {
        showDialogs();
    }

    ProgressDialog dialog = null;
    private void showDialogs(){
        if(dialog == null){
            dialog = new ProgressDialog(context);
            dialog.setMessage("正在努力中...");
        }
        dialog.show();
    }

    private void dismissDialogs(){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void showArticles(List<Article> articles) {
        dismissDialogs();
        if (articles == null){
            if(articleList.size() == 0){
                nullArticleTv.setVisibility(View.VISIBLE);
            }else {
            }
        }else{
            page ++;
            if (articles.size() < 10){
                isEnd = true;
            }
            nullArticleTv.setVisibility(View.GONE);
            articleList.addAll(articles);
            setAdapter();
        }
    }

    private void setAdapter(){
        if (articleAdapter == null){
            articleAdapter = new ArticleAdapter(context, articleList);
            listView.setAdapter(articleAdapter);
        }else{
            articleAdapter.setArticleList(articleList);
            articleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.null_article:
                reFindArticle();
                break;
        }
    }

    private int position;
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.college_lv:
                if (NetUtil.getNetworkState(context) == NetUtil.NETWORN_NONE){
                    Toast.makeText(context, "请确认网络已打开", Toast.LENGTH_SHORT).show();
                }else {
                    position = i;
                    Intent intent = new Intent(context, ArticleDetailActivity.class);
                    intent.putExtra("article", articleList.get(i));
                    startActivityForResult(intent, 1);
                    ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                 break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            int likeTimes = data.getIntExtra("likeTimes", 0);
            if (likeTimes == 1){
                articleList.get(position).setLikeTimes(articleList.get(position).getLikeTimes() + 1);
            }
            articleList.get(position).setReadTimes(articleList.get(position).getReadTimes() + 1);
            articleAdapter.setArticleList(articleList);
            articleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        // 当不滚动时
        if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            // 判断是否滚动到底部
            if (absListView.getLastVisiblePosition() == absListView.getCount() - 1) {
                //加载更多功能的代码
                //监听ListView滑动到底部，加载更多评论
                if(isEnd){  //到底
                    Toast.makeText(context,"已经到底了",Toast.LENGTH_SHORT).show();
                }else {
                    findArticle();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    private void reFindArticle(){
        articleList.clear();
        isEnd = false;
        page = 1;
        findArticle();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        findAllArticlePresenter.setView(null);
    }
}
