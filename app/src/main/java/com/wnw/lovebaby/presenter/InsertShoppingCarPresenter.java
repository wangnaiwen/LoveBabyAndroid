package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.ShoppingCar;
import com.wnw.lovebaby.model.modelImpl.InsertShoppingCarModelImpl;
import com.wnw.lovebaby.model.modelInterface.IInsertShoppingCarModel;
import com.wnw.lovebaby.view.viewInterface.IInsertShoppingCarView;

/**
 * Created by wnw on 2017/4/5.
 */

public class InsertShoppingCarPresenter {
    private Context context;
    //view
    private IInsertShoppingCarView insertShoppingCarView;
    //model
    private IInsertShoppingCarModel insertShoppingCarModel = new InsertShoppingCarModelImpl();
    //ͨ通过构造函数传入view

    public InsertShoppingCarPresenter(Context context, IInsertShoppingCarView insertShoppingCarView) {
        this.context = context;
        this.insertShoppingCarView = insertShoppingCarView;
    }
    //将最新的View传递过来，在Destroy被销毁时，将setView(null)
    public void setView(IInsertShoppingCarView insertShoppingCarView){
        this.insertShoppingCarView = insertShoppingCarView;
    }

    //加载数据
    public void insertShoppingCar(ShoppingCar car) {
        //加载进度条
        insertShoppingCarView.showDialog();
        //model进行数据获取
        if(insertShoppingCarModel != null){
            insertShoppingCarModel.insertShoppingCar(context, car, new IInsertShoppingCarModel.ShoppingCarInsertListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if(insertShoppingCarView != null){
                        insertShoppingCarView.showResult(isSuccess);
                    }
                }
            });
        }
    }
}
