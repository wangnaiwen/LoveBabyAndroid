package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.User;

/**
 * Created by wnw on 2017/3/6.
 */

public interface ILoginView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void validate(User user);
}