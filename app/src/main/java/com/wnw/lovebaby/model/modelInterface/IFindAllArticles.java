package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;


import com.wnw.lovebaby.domain.Article;

import java.util.List;

/**
 * Created by wnw on 2017/5/8.
 */

public interface IFindAllArticles {
    /**
     * 加载数据
     * */
    void findArticles(Context context, int page, ArticleFindByPageListener articleFindByPageListener);

    /**
     * 加载数据完成的回调
     * */
    interface ArticleFindByPageListener{
        void complete(List<Article> articles);
    }
}
