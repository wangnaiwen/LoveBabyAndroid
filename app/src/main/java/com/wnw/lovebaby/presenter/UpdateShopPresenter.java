package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Shop;
import com.wnw.lovebaby.model.modelImpl.UpdateShopModelImpl;
import com.wnw.lovebaby.model.modelInterface.IUpdateShopModel;
import com.wnw.lovebaby.view.viewInterface.IUpdateShopView;


/**
 * Created by wnw on 2017/4/11.
 */

public class UpdateShopPresenter {
    private Context context;
    //view
    private IUpdateShopView updateShopView;
    //model
    private IUpdateShopModel updateShopModel = new UpdateShopModelImpl();
    //ͨ通过构造函数传入view

    public UpdateShopPresenter(Context context, IUpdateShopView updateShopView) {
        super();
        this.context = context;
        this.updateShopView = updateShopView;
    }

    public void setUpdateShopView(IUpdateShopView updateShopView){
        this.updateShopView = updateShopView;
    }

    //加载数据
    public void updateShop(Shop shop) {
        //加载进度条
        updateShopView.showDialog();
        //model进行数据获取
        if(updateShopModel != null){
            updateShopModel.updateShop(context, shop, new IUpdateShopModel.ShopUpdateListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if(updateShopView != null){
                        updateShopView.showUpdateShopResult(isSuccess);
                    }
                }
            });
        }
    }
}
