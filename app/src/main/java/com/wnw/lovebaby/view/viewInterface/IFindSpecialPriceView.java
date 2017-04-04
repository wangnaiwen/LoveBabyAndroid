package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.HotSale;
import com.wnw.lovebaby.domain.Product;
import com.wnw.lovebaby.domain.SpecialPrice;

import java.util.List;

/**
 * Created by wnw on 2017/4/3.
 */

public interface IFindSpecialPriceView {
    //显示进度条
    void showLoading();
    //展示用户数据
    void showSpecialPrice(List<Product> products);
}
