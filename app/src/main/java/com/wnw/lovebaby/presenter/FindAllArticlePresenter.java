package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Article;
import com.wnw.lovebaby.model.modelImpl.FindAllArticleImp;
import com.wnw.lovebaby.model.modelInterface.IFindAllArticles;
import com.wnw.lovebaby.view.viewInterface.IFindAllArticleView;

import java.util.List;

/**
 * Created by wnw on 2017/5/8.
 */

public class FindAllArticlePresenter {

    private Context context;
    //view
    private IFindAllArticleView findAllArticleView;
    //model
    private IFindAllArticles findAllArticles = new FindAllArticleImp();
    //ͨ通过构造函数传入view

    public FindAllArticlePresenter(Context context, IFindAllArticleView findAllArticleView) {
        this.context = context;
        this.findAllArticleView = findAllArticleView;
    }

    public void setView(IFindAllArticleView findAllArticleView) {
        this.findAllArticleView = findAllArticleView;
    }

    //加载数据
    public void findAllArticles(int page) {
        //加载进度条
        findAllArticleView.showDialog();
        //model进行数据获取
        if (findAllArticles != null) {
            findAllArticles.findArticles(context, page, new IFindAllArticles.ArticleFindByPageListener() {
                @Override
                public void complete(List<Article> articles) {
                    if (findAllArticleView != null) {
                        findAllArticleView.showArticles(articles);
                    }
                }
            });
        }
    }
}
