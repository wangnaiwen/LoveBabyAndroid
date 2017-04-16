package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Shop;
import com.wnw.lovebaby.model.modelImpl.FindShopByIdModelImpl;
import com.wnw.lovebaby.model.modelInterface.IFindShopByIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindShopByIdView;

/**
 * Created by wnw on 2017/4/11.
 */

public class FindShopByIdPresenter {
    private Context context;
    //view
    private IFindShopByIdView findShopByIdView;
    //model
    private IFindShopByIdModel findShopByIdModel = new FindShopByIdModelImpl();
    //ͨ通过构造函数传入view

    public FindShopByIdPresenter(Context context, IFindShopByIdView findShopByIdView) {
        this.context = context;
        this.findShopByIdView = findShopByIdView;
    }

    public void setFindShopByIdView(IFindShopByIdView findShopByIdView){
        this.findShopByIdView = findShopByIdView;
    }

    //加载数据
    public void findShopById(int id) {
        //加载进度条
        findShopByIdView.showDialog();
        //model进行数据获取
        if(findShopByIdModel != null) {
            findShopByIdModel.findShopById(context, id, new IFindShopByIdModel.ShopFindByIdListener() {
                @Override
                public void complete(Shop shop) {
                    findShopByIdView.showShopById(shop);
                }
            });
        }
    }
}
