package com.wnw.lovebaby.view.viewInterface;


/**
 * Created by wnw on 2017/4/26.
 */

public interface IInsertWithdrawView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showInsertWithdrawResult(boolean isSuccess);
}
