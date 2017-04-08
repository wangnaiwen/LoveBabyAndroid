package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.ShoppingCar;
import com.wnw.lovebaby.model.modelImpl.FindShoppingCarByUserIdModelImpl;
import com.wnw.lovebaby.model.modelInterface.IFindShoppingByUserIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindShoppingCarByUserIdView;

import java.util.List;

/**
 * Created by wnw on 2017/4/5.
 */

public class FindShoppingCarByUserIdPresenter {
    private Context context;
    //view
    private IFindShoppingCarByUserIdView findShoppingCarByUserIdView;
    //model
    private IFindShoppingByUserIdModel findShoppingByUserIdModel = new FindShoppingCarByUserIdModelImpl();
    //ͨ通过构造函数传入view

    public FindShoppingCarByUserIdPresenter(Context context, IFindShoppingCarByUserIdView findShoppingCarByUserIdView) {
        this.context = context;
        this.findShoppingCarByUserIdView = findShoppingCarByUserIdView;
    }
    //将最新的View传递过来，在Destroy被销毁时，将setView(null)
    public void setView(IFindShoppingCarByUserIdView findShoppingCarByUserIdView){
        this.findShoppingCarByUserIdView = findShoppingCarByUserIdView;
    }

    //加载数据
    public void findShoppingCarByUserId(int userId) {
        //加载进度条
        findShoppingCarByUserIdView.showDialog();
        //model进行数据获取
        if(findShoppingByUserIdModel != null){
            findShoppingByUserIdModel.findShoppingCarByUserId(context, userId, new IFindShoppingByUserIdModel.ShoppingFindByUserIdListener() {
                @Override
                public void complete(List<ShoppingCar> shoppingCarList) {
                    if(findShoppingCarByUserIdView != null){
                        findShoppingCarByUserIdView.showResult(shoppingCarList);
                    }
                }
            });
        }
    }
}
