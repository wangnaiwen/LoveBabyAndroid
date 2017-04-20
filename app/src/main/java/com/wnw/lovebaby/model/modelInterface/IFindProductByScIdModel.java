package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Product;

import java.util.List;

/**
 * Created by wnw on 2017/4/20.
 */

public interface IFindProductByScIdModel {
    void findProductByScId(Context context, int scId, int number,ProductFindByScIdListener productFindByScIdListener);

    interface ProductFindByScIdListener{
        void complete(List<Product> products);
    }
}
