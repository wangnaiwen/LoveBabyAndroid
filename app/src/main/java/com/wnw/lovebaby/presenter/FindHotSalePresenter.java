package com.wnw.lovebaby.presenter;

import android.content.Context;
import com.wnw.lovebaby.domain.Product;
import com.wnw.lovebaby.model.modelImpl.FindHotSaleModelImpl;
import com.wnw.lovebaby.model.modelInterface.IFindHotSaleModel;
import com.wnw.lovebaby.view.viewInterface.IFindHotSaleView;

import java.util.List;

/**
 * Created by wnw on 2017/4/3.
 */

public class FindHotSalePresenter {
    private Context context;
    //view
    private IFindHotSaleView hotSaleView;
    //model
    private IFindHotSaleModel findHotSaleModel = new FindHotSaleModelImpl();
    //ͨ通过构造函数传入view

    public FindHotSalePresenter(Context context, IFindHotSaleView hotSaleView) {
        super();
        this.context = context;
        this.hotSaleView = hotSaleView;
    }

    //加载数据
    public void load() {
        //加载进度条
        hotSaleView.showLoading();
        //model进行数据获取
        if(findHotSaleModel != null){
            findHotSaleModel.findHotSale(context, new IFindHotSaleModel.FindHotSaleListener() {
                @Override
                public void complete(List<Product> productList) {
                    if(hotSaleView != null){
                        hotSaleView.showHotSale(productList);
                    }
                }
            });
        }
    }
}
