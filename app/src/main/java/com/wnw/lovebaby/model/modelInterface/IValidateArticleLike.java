package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

/**
 * Created by wnw on 2017/6/1.
 */

public interface IValidateArticleLike {
    /**
     * 加载数据
     * */
    void validateArticleLike(Context context, int userId, int articleId, ValidateArticleLikeListener validateArticleLikeListener);

    /**
     * 加载数据完成的回调
     * */
    interface ValidateArticleLikeListener{
        void complete(boolean isSuccess);
    }
}
