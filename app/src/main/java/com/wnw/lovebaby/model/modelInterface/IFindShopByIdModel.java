package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Shop;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IFindShopByIdModel {
    /**
     * 加载数据
     * */
    void findShopById(Context context, int id, ShopFindByIdListener shopFindByIdListener);

    /**
     * 加载数据完成的回调
     * */
    interface ShopFindByIdListener{
        void complete(Shop shop);
    }
}
