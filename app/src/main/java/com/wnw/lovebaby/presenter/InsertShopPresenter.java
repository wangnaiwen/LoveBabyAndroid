package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Shop;
import com.wnw.lovebaby.model.modelImpl.InsertShopModelImpl;
import com.wnw.lovebaby.model.modelInterface.IInsertShopModel;
import com.wnw.lovebaby.view.viewInterface.IInsertShopView;

/**
 * Created by wnw on 2017/4/11.
 */

public class InsertShopPresenter {
    private Context context;
    //view
    private IInsertShopView insertShopView;
    //model
    private IInsertShopModel insertShopModel = new InsertShopModelImpl();
    //ͨ通过构造函数传入view

    public InsertShopPresenter(Context context, IInsertShopView insertShopView) {
        super();
        this.context = context;
        this.insertShopView = insertShopView;
    }

    public void setInsertShopView(IInsertShopView insertShopView){
        this.insertShopView = insertShopView;
    }

    //加载数据
    public void insertShop(Shop shop) {
        //加载进度条
        insertShopView.showDialog();
        //model进行数据获取
        if(insertShopModel != null) {
            insertShopModel.insertShop(context, shop, new IInsertShopModel.ShopInsertListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if(insertShopView != null){
                        insertShopView.showInsertShopResult(isSuccess);
                    }
                }
            });
        }
    }
}
