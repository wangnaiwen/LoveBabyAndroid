package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

/**
 * Created by wnw on 2017/5/19.
 */

public interface IUpdateUserImgModel {
    /**
     * 加载数据
     * */
    void updateUserImg(Context context, int userId, String image, UserImgUpdateListener userImgUpdateListener);

    /**
     * 更新数据完成的回调
     * */
    interface UserImgUpdateListener{
        void complete(boolean isSuccess);
    }
}
