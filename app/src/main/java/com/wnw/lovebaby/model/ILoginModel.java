package com.wnw.lovebaby.model;

import android.content.Context;
import com.wnw.lovebaby.domain.User;

/**
 * Created by wnw on 2016/10/17.
 */

public interface ILoginModel {
    /**
     * 加载数据
     * */
    void loadUser(Context context, User user, ILoginModel.UserLoadingListener loadingListener);

    /**
     * 加载数据完成的回调
     * */
    interface UserLoadingListener{
        void complete(User user);
    }
}
