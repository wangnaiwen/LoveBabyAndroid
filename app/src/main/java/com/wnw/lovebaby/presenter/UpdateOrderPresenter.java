package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.model.modelImpl.UpdateOrderModelImpl;
import com.wnw.lovebaby.model.modelInterface.IUpdateOrderModel;
import com.wnw.lovebaby.view.viewInterface.IUpdateOrderView;

/**
 * Created by wnw on 2017/4/11.
 */

public class UpdateOrderPresenter {
    private Context context;
    //view
    private IUpdateOrderView updateOrderView;
    //model
    private IUpdateOrderModel updateOrderModel = new UpdateOrderModelImpl();
    //ͨ通过构造函数传入view

    public UpdateOrderPresenter(Context context,IUpdateOrderView updateOrderView) {
        super();
        this.context = context;
        this.updateOrderView = updateOrderView;
    }

    public void setUpdateOrderView(IUpdateOrderView updateOrderView){
        this.updateOrderView = updateOrderView;
    }

    //加载数据
    public void updateOrder(Order order) {
        //加载进度条
        updateOrderView.showDialog();
        //model进行数据获取
        if(updateOrderModel != null){
            updateOrderModel.updateOrder(context, order, new IUpdateOrderModel.OrderUpdateListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if(updateOrderView != null){
                        updateOrderView.showUpdateOrderResult(isSuccess);
                    }
                }
            });
        }
    }
}
