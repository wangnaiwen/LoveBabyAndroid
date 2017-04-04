package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Product;
import com.wnw.lovebaby.model.modelImpl.FindNewProductModelImpl;
import com.wnw.lovebaby.model.modelInterface.IFindNewProductModel;
import com.wnw.lovebaby.view.viewInterface.IFindNewProductView;

import java.util.List;

/**
 * Created by wnw on 2017/4/3.
 */

public class FindNewProductPresenter {
    private Context context;
    //view
    private IFindNewProductView newProductView;
    //model
    private IFindNewProductModel findNewProductModel = new FindNewProductModelImpl();
    //ͨ通过构造函数传入view

    public FindNewProductPresenter(Context context, IFindNewProductView findNewProductView) {
        super();
        this.context = context;
        this.newProductView = findNewProductView;
    }

    //加载数据
    public void load() {
        //加载进度条
        newProductView.showLoading();
        //model进行数据获取
        if(findNewProductModel != null){
            findNewProductModel.findNewProduct(context, new IFindNewProductModel.FindNewProductListener() {
                @Override
                public void complete(List<Product> productList) {
                    if(newProductView != null){
                        newProductView.showNewProduct(productList);
                    }
                }
            });
        }
    }
}
