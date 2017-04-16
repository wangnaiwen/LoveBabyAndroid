package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Shop;
import com.wnw.lovebaby.model.modelImpl.FindShopByUserIdModelImpl;
import com.wnw.lovebaby.model.modelInterface.IFindShopByUserIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindShopByUserIdView;

/**
 * Created by wnw on 2017/4/11.
 */

public class FindShopByUserIdPresenter {
    private Context context;
    //view
    private IFindShopByUserIdView findShopByUserIdView;
    //model
    private IFindShopByUserIdModel findShopByUserIdModel = new FindShopByUserIdModelImpl();
    //ͨ通过构造函数传入view

    public FindShopByUserIdPresenter(Context context, IFindShopByUserIdView findShopByUserIdView) {
        super();
        this.context = context;
        this.findShopByUserIdView = findShopByUserIdView;
    }

    public void setFindShopByUserIdView(IFindShopByUserIdView findShopByUserIdView){
        this.findShopByUserIdView = findShopByUserIdView;
    }

    //加载数据
    public void findShopByUserId(int userId) {
        //加载进度条
        findShopByUserIdView.showDialog();
        //model进行数据获取
        if(findShopByUserIdModel != null) {
            findShopByUserIdModel.findShopByUserId(context, userId, new IFindShopByUserIdModel.ShopFindByUserIdListener() {
                @Override
                public void complete(Shop shop) {
                    findShopByUserIdView.showShopsByUserId(shop);
                }
            });
        }
    }
}
