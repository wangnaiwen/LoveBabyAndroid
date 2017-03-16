package com.wnw.lovebaby.view.viewInterface;


import com.wnw.lovebaby.domain.ReceAddress;

/**
 * Created by wnw on 2017/3/11.
 */

public interface IInsertReceAddressView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void insertReceAddress(ReceAddress address);
}
