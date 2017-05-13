package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

/**
 * Created by wnw on 2017/5/8.
 */

public interface IUpdateArticleReadTimes {
    /**
     * 加载数据
     * */
    void updateArticle(Context context, int id, ArticleUpdateReadTimesListener articleUpdateReadTimesListener);

    /**
     * 加载数据完成的回调
     * */
    interface ArticleUpdateReadTimesListener{
        void complete(boolean isSuccess);
    }
}
