package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Product;

import java.util.List;

/**
 * Created by wnw on 2017/4/26.
 */

public interface IFindProductByKeyModel {
    /**
     * find data
     * */
    void findProductByKey(Context context, String key, int userId,FindProductByKeyListener findProductByKeyListener);

    /**
     * completed
     * */
    interface FindProductByKeyListener{
        void complete(List<Product> products);
    }
}
