package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.User;

/**
 * Created by wnw on 2017/4/23.
 */

public interface IFindUserByIdView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showUserById(User user);
}
