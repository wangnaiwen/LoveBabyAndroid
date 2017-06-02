package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.model.modelImpl.FindQuitOrdersImp;
import com.wnw.lovebaby.model.modelInterface.IFindQuitOrders;
import com.wnw.lovebaby.view.viewInterface.IFindQuitOrdersView;
import java.util.List;

/**
 * Created by wnw on 2017/5/8.
 */

public class FindQuitOrdersPresenter {
    private Context context;
    //view
    private IFindQuitOrdersView findQuitOrdersView;
    //model
    private IFindQuitOrders findQuitOrders = new FindQuitOrdersImp();
    //ͨ通过构造函数传入view

    public FindQuitOrdersPresenter(Context context, IFindQuitOrdersView findQuitOrdersView) {
        this.context = context;
        this.findQuitOrdersView = findQuitOrdersView;
    }

    public void setView(IFindQuitOrdersView findQuitOrdersView){
        this.findQuitOrdersView = findQuitOrdersView;
    }

    //加载数据
    public void findQuitOrders(int userId) {
        //加载进度条
        findQuitOrdersView.showDialog();
        //model进行数据获取
        if(findQuitOrders != null) {
            findQuitOrders.findQuitOrders(context, userId, new IFindQuitOrders.FindQuitOrdersListener() {
                @Override
                public void complete(List<Order> orders, List<String> nameList) {
                    if (findQuitOrdersView != null){
                        findQuitOrdersView.showOrders(orders, nameList);
                    }
                }
            });
        }
    }
}
