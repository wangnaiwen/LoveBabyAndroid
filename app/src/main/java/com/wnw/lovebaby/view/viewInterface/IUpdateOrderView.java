package com.wnw.lovebaby.view.viewInterface;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IUpdateOrderView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showUpdateOrderResult(boolean isSuccess);

}
