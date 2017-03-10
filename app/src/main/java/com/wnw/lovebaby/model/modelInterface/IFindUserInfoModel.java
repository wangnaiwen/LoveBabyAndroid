package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.User;
import com.wnw.lovebaby.domain.UserInfo;

/**
 * Created by wnw on 2017/3/10.
 */

public interface IFindUserInfoModel {
    /**
     * 加载数据
     * */
    void FindUserInfo(Context context, int userId, UserInfoLoadingListener userInfoLoadingListener);

    /**
     * 加载数据完成的回调
     * */
    interface UserInfoLoadingListener{
        void complete(UserInfo userInfo);
    }
}
