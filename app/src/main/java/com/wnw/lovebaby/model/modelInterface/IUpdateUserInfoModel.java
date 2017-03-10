package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.UserInfo;

/**
 * Created by wnw on 2017/3/10.
 */

public interface IUpdateUserInfoModel {
    /**
     * 加载数据
     * */
    void updateUserInfo(Context context, UserInfo userInfo, UserInfoUpdateListener userInfoUpdateListener);

    /**
     * 更新数据完成的回调
     * */
    interface UserInfoUpdateListener{
        void complete(boolean isSuccess);
    }
}
