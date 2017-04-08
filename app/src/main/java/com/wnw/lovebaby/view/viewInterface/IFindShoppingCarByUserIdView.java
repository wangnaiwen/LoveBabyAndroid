package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.ShoppingCar;

import java.util.List;

/**
 * Created by wnw on 2017/4/5.
 */

public interface IFindShoppingCarByUserIdView {
    void showDialog();
    void showResult(List<ShoppingCar> shoppingCars);
}
