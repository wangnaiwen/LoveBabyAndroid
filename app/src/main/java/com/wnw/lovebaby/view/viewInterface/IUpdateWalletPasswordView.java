package com.wnw.lovebaby.view.viewInterface;

/**
 * Created by wnw on 2017/4/16.
 */

public interface IUpdateWalletPasswordView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void updateWalletPasswordResult(boolean isSuccess);
}
