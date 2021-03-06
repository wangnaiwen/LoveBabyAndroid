package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.Shop;

import java.util.List;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IFindShopByUserIdView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showShopsByUserId(Shop shop);
}
