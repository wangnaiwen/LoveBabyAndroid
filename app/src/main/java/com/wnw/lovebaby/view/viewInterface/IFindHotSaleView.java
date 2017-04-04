package com.wnw.lovebaby.view.viewInterface;

import android.widget.Toast;

import com.wnw.lovebaby.domain.HotSale;
import com.wnw.lovebaby.domain.Product;

import java.util.List;

/**
 * Created by wnw on 2017/4/3.
 */

public interface IFindHotSaleView {
    //显示进度条
    void showLoading();
    //展示用户数据
    void showHotSale(List<Product> products);
}
