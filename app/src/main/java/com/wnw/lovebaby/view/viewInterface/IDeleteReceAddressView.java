package com.wnw.lovebaby.view.viewInterface;

/**
 * Created by wnw on 2017/3/11.
 */

public interface IDeleteReceAddressView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void deleteReceAddress(boolean isSuccess);
}
