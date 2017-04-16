package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.model.modelImpl.FindOrderByIdModelImpl;
import com.wnw.lovebaby.model.modelInterface.IFindOrderByIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindOrderByIdView;

/**
 * Created by wnw on 2017/4/11.
 */

public class FindOrderByIdPresenter {
    private Context context;
    //view
    private IFindOrderByIdView findOrderByIdView;
    //model
    private IFindOrderByIdModel findOrderByIdModel = new FindOrderByIdModelImpl();
    //ͨ通过构造函数传入view

    public FindOrderByIdPresenter(Context context,IFindOrderByIdView findOrderByIdView) {
        this.context = context;
        this.findOrderByIdView = findOrderByIdView;
    }

    public void setFindOrderByIdView(IFindOrderByIdView findOrderByIdView){
        this.findOrderByIdView = findOrderByIdView;
    }

    //加载数据
    public void findOrderById(int id) {
        //加载进度条
        findOrderByIdView.showDialog();
        //model进行数据获取
        if(findOrderByIdModel != null) {
            findOrderByIdModel.findOrderById(context, id, new IFindOrderByIdModel.OrderFindByIdListener() {
                @Override
                public void complete(Order order) {
                    findOrderByIdView.showOrderById(order);
                }
            });
        }
    }
}
