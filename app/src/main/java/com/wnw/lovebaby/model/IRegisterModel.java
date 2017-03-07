package com.wnw.lovebaby.model;

import android.content.Context;
import com.wnw.lovebaby.domain.User;

/**
 * Created by wnw on 2016/10/17.
 */

public interface IRegisterModel {
    /**
     * 加载数据
     * */
    void registerNetUser(Context context, User user, IRegisterModel.UserRegisterListener userRegisterListener);

    /**
     * 加载数据完成的回调
     * */
    interface UserRegisterListener{
        void complete(boolean isSuccess);
    }
}
