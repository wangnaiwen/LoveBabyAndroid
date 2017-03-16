package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.ReceAddress;

import java.util.List;

/**
 * Created by wnw on 2017/3/11.
 */

public interface IFindReceAddressView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void findReceAdress(List<ReceAddress> addressList);

    /**
     * 返回数据
     * */
    void deleteReceAddress(boolean isSuccess);
}
