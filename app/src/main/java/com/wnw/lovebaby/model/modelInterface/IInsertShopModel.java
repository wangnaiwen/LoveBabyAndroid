package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Shop;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IInsertShopModel {
    /**
     * 加载数据
     * */
    void insertShop(Context context, Shop shop, ShopInsertListener shopInsertListener);

    /**
     * 加载数据完成的回调
     * */
    interface ShopInsertListener{
        void complete(boolean isSuccess);
    }
}
