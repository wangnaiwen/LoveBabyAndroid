package com.wnw.lovebaby.view.viewInterface;

/**
 * Created by wnw on 2017/4/16.
 */

public interface ISubWalletMoneyView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void subWalletMoneyResult(boolean isSuccess);
}
