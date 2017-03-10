package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.User;

/**
 * Created by wnw on 2017/3/7.
 */

public interface IUpdatePasswordModel {
    /**
     * 加载数据
     * */
    void updateUserPassword(Context context, String phone, String password, IUpdatePasswordModel.UpdateUserPasswordListener updateUserPasswordListener);

    /**
     * 加载数据完成的回调
     * */
    interface UpdateUserPasswordListener{
        void complete(User user);
    }
}
