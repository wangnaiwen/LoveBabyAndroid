package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.User;
import com.wnw.lovebaby.domain.UserInfo;

/**
 * Created by wnw on 2017/3/11.
 */

public interface IUpdateUserInfoView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void updateUserInfo(boolean isSuccess);
}
