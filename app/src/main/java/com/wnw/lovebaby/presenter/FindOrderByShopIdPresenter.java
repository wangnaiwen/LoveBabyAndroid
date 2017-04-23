package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.model.modelImpl.FindOrderByShopIdModelImpl;
import com.wnw.lovebaby.model.modelInterface.IFindOrderByShopIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindOrderByShopIdView;

import java.util.List;

/**
 * Created by wnw on 2017/4/11.
 */

public class FindOrderByShopIdPresenter {
    private Context context;
    //view
    private IFindOrderByShopIdView findOrderByShopIdView;
    //model
    private IFindOrderByShopIdModel findOrderByShopIdModel = new FindOrderByShopIdModelImpl();
    //ͨ通过构造函数传入view

    public FindOrderByShopIdPresenter(Context context,IFindOrderByShopIdView findOrderByShopIdView) {
        this.context = context;
        this.findOrderByShopIdView = findOrderByShopIdView;
    }

    public void setFindOrderByShopIdView(IFindOrderByShopIdView findOrderByShopIdView){
        this.findOrderByShopIdView = findOrderByShopIdView;
    }

    //加载数据
    public void findOrderByShopId(int shopId, int number) {
        //加载进度条
        findOrderByShopIdView.showDialog();
        //model进行数据获取
        if(findOrderByShopIdModel != null) {
            findOrderByShopIdModel.findOrderByShopId(context, shopId,number, new IFindOrderByShopIdModel.OrderFindByShopIdListener() {
                @Override
                public void complete(List<Order> orders) {
                    findOrderByShopIdView.showOrdersByShopId(orders);
                }
            });
        }
    }
}
