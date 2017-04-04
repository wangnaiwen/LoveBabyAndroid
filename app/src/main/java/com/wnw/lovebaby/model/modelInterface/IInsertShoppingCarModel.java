package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.ShoppingCar;

/**
 * Created by wnw on 2017/4/4.
 */

public interface IInsertShoppingCarModel {
    /**
     * insert data
     * */
    void insertShoppingCar(Context context, ShoppingCar car,ShoppingCarInsertListener shoppingCarInsertListener );

    /**
     * completed
     * */
    interface ShoppingCarInsertListener{
        void complete(boolean isSuccess);
    }
}
