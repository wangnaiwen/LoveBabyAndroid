package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Product;

import java.util.List;

/**
 * Created by wnw on 2017/4/3.
 */

public interface IFindNewProductModel {
    /**
     * find data
     * */
    void findNewProduct(Context context, IFindNewProductModel.FindNewProductListener findNewProductListener);

    /**
     * completed
     * */
    interface FindNewProductListener{
        void complete(List<Product> productList);
    }
}
