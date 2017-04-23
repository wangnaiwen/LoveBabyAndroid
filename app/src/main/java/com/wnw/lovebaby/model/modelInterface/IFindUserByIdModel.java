package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.User;

/**
 * Created by wnw on 2017/4/23.
 */

public interface IFindUserByIdModel {
    /**
     * 加载数据
     * */
    void findUserById(Context context, int id,FindUserByIdListener findUserByIdListener);

    /**
     * 加载数据完成的回调
     * */
    interface FindUserByIdListener{
        void complete(User user);
    }
}
