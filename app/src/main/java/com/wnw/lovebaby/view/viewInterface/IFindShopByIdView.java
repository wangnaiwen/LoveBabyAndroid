package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.Shop;


/**
 * Created by wnw on 2017/4/11.
 */

public interface IFindShopByIdView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showShopById(Shop shop);
}
