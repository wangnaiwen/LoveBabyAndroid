package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

/**
 * Created by wnw on 2017/5/8.
 */

public interface IUpdateArticleLikeTimes {
    /**
     * 加载数据
     * */
    void updateArticle(Context context, int id, ArticleUpdateLikeTimesListener articleUpdateLikeTimesListener );

    /**
     * 加载数据完成的回调
     * */
    interface ArticleUpdateLikeTimesListener{
        void complete(boolean isSuccess);
    }
}
