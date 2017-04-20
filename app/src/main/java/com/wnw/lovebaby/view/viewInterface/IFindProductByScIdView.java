package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.Product;

import java.util.List;

/**
 * Created by wnw on 2017/4/20.
 */

public interface IFindProductByScIdView {
    void showDialog();
    void showProducts(List<Product> products);
}
