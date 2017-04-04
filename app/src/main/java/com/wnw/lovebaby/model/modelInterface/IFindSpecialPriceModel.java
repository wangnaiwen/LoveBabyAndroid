package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Product;

import java.util.List;

/**
 * Created by wnw on 2017/4/3.
 */

public interface IFindSpecialPriceModel {
    /**
     * find data
     * */
    void findSpecialPrice(Context context, IFindSpecialPriceModel.FindSpecialPriceListener findSpecialPriceListener);

    /**
     * completed
     * */
    interface FindSpecialPriceListener{
        void complete(List<Product> productList);
    }
}
