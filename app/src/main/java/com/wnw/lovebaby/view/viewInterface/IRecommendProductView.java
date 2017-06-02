package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.Product;

import java.util.List;

/**
 * Created by wnw on 2017/6/2.
 */

public interface IRecommendProductView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showRecommendProducts(List<Product> products);
}
