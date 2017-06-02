package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.model.modelImpl.FindOrderByUserIdModelImpl;
import com.wnw.lovebaby.model.modelInterface.IFindOrderByUserIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindOrderByUserIdView;

import java.util.List;

/**
 * Created by wnw on 2017/4/11.
 */

public class FindOrderByUserIdPresenter {
    private Context context;
    //view
    private IFindOrderByUserIdView findOrderByUserIdView;
    //model
    private IFindOrderByUserIdModel findOrderByUserIdModel = new FindOrderByUserIdModelImpl();
    //ͨ通过构造函数传入view

    public FindOrderByUserIdPresenter(Context context,IFindOrderByUserIdView findOrderByUserIdView) {
        this.context = context;
        this.findOrderByUserIdView = findOrderByUserIdView;
    }

    public void setFindOrderByUserIdView(IFindOrderByUserIdView findOrderByUserIdView){
        this.findOrderByUserIdView = findOrderByUserIdView;
    }

    //加载数据
    public void findOrderByUserId(int userId) {
        //加载进度条
        findOrderByUserIdView.showDialog();
        //model进行数据获取
        if(findOrderByUserIdModel != null) {
            findOrderByUserIdModel.findOrderByUserId(context, userId, new IFindOrderByUserIdModel.OrderFindByUserIdListener() {
                @Override
                public void complete(List<Order> orders, List<String> names) {
                    findOrderByUserIdView.showOrdersByUserId(orders, names);
                }
            });
        }
    }
}
