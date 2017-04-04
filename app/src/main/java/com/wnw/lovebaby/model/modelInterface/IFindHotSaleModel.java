package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Product;

import java.util.List;

/**
 * Created by wnw on 2017/4/3.
 */

public interface IFindHotSaleModel {
    /**
     * find data
     * */
    void findHotSale(Context context, IFindHotSaleModel.FindHotSaleListener findHotSaleListener);

    /**
     * completed
     * */
    interface FindHotSaleListener{
        void complete(List<Product> productList);
    }
}
