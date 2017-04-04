package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.HotSale;
import com.wnw.lovebaby.domain.NewProduct;
import com.wnw.lovebaby.domain.Pr;
import com.wnw.lovebaby.domain.Product;

import java.util.List;

/**
 * Created by wnw on 2017/4/3.
 */

public interface IFindNewProductView {
    //显示进度条
    void showLoading();
    //展示用户数据
    void showNewProduct(List<Product> products);
}
