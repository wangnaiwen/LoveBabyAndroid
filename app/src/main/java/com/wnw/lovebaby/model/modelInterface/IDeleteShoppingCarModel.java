package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

/**
 * Created by wnw on 2017/4/4.
 */

public interface IDeleteShoppingCarModel {
    /**
     * delete data
     * */
    void deleteShoppingCar(Context context, int id, ShoppingCarDeleteListener shoppingCarDeleteListener);

    /**
     * completed
     * */
    interface ShoppingCarDeleteListener{
        void complete(boolean isSuccess);
    }
}
