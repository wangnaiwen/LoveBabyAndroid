package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.Article;

import java.util.List;

/**
 * Created by wnw on 2017/5/8.
 */

public interface IFindAllArticleView {
    void showDialog();
    void showArticles(List<Article> articles);
}
