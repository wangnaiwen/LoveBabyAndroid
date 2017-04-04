package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.ShoppingCar;

/**
 * Created by wnw on 2017/4/4.
 */

public interface IUpdateShoppingCarProductCountModel {
    /**
     * update data
     * */
    void updateShoppingCarProductCount(Context context, int id, int count, ShoppingCarProductCountUpdateListener shoppingCarProductCountUpdateListener);

    /**
     * completed
     * */
    interface ShoppingCarProductCountUpdateListener{
        void complete(boolean isSuccess);
    }
}
