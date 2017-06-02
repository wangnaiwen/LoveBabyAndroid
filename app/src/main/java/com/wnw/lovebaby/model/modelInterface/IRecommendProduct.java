package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Product;

import java.util.List;

/**
 * Created by wnw on 2017/6/2.
 */

public interface IRecommendProduct {
    /**
     * 加载数据
     * */
    void findRecommendProduct(Context context, int userId, RecommendProductListener recommendProductListener);

    /**
     * 加载数据完成的回调
     * */
    interface RecommendProductListener{
        void complete(List<Product> products);
    }
}
