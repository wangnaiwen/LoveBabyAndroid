package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.UserInfo;

/**
 * Created by wnw on 2017/5/20.
 */

public interface IFindUserImgView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showUserInfo(UserInfo userInfo);

}
