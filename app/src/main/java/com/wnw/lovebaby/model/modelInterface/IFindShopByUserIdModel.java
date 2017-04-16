package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Shop;


/**
 * Created by wnw on 2017/4/11.
 */

public interface IFindShopByUserIdModel {
    /**
     * 加载数据
     * */
    void findShopByUserId(Context context, int userId, ShopFindByUserIdListener shopFindByUserIdListener);

    /**
     * 加载数据完成的回调
     * */
    interface ShopFindByUserIdListener{
        void complete(Shop shop);
    }
}
