package com.wnw.lovebaby.view.viewInterface;

/**
 * Created by wnw on 2017/5/19.
 */

public interface IUpdateUserImgView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void updateUserImg(boolean isSuccess);
}
