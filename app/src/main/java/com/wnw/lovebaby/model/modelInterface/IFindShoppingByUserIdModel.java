package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.ShoppingCar;

import java.util.List;

/**
 * Created by wnw on 2017/4/4.
 */

public interface IFindShoppingByUserIdModel {
    /**
     * update data
     * */
    void findShoppingCarByUserId(Context context, int userId,ShoppingFindByUserIdListener shoppingFindByUserIdListener);

    /**
     * completed
     * */
    interface ShoppingFindByUserIdListener{
        void complete(List<ShoppingCar> shoppingCarList);
    }
}
