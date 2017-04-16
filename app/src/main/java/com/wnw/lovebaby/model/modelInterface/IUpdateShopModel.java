package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Shop;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IUpdateShopModel {
    /**
     * 加载数据
     * */
    void updateShop(Context context, Shop shop, ShopUpdateListener shopUpdateListener);

    /**
     * 加载数据完成的回调
     * */
    interface ShopUpdateListener{
        void complete(boolean isSuccess);
    }
}
