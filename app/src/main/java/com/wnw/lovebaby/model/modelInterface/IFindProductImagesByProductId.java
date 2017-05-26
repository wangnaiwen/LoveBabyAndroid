package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;


import com.wnw.lovebaby.domain.ProductImage;

import java.util.List;

/**
 * Created by wnw on 2017/5/25.
 */

public interface IFindProductImagesByProductId {
    /**
     * find data
     * */
    void findImagesByProductId(Context context, int productId, FindImagesByProductIdListener findImagesByProductIdListener);

    /**
     * completed
     * */
    interface FindImagesByProductIdListener{
        void complete(List<ProductImage> images);
    }
}
