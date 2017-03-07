package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.User;

/**
 * Created by wnw on 2017/3/7.
 */

public interface IFindPasswordView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void findPassword(User user);
}
