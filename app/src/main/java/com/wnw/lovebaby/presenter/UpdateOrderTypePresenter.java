package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.model.modelImpl.UpdateOrderTypeImp;
import com.wnw.lovebaby.model.modelInterface.IUpdateOrderType;
import com.wnw.lovebaby.view.viewInterface.IUpdateOrderTypeView;


/**
 * Created by wnw on 2017/5/8.
 */

public class UpdateOrderTypePresenter {
    private Context context;
    //view
    private IUpdateOrderTypeView updateOrderTypeView;
    //model
    private IUpdateOrderType updateOrderType = new UpdateOrderTypeImp();
    //ͨ通过构造函数传入view

    public UpdateOrderTypePresenter(Context context, IUpdateOrderTypeView updateOrderTypeView) {
        this.context = context;
        this.updateOrderTypeView = updateOrderTypeView;
    }

    public void setView(IUpdateOrderTypeView updateOrderTypeView) {
        this.updateOrderTypeView = updateOrderTypeView;
    }

    //加载数据
    public void updateOrderType(int id, int type) {
        //加载进度条
        updateOrderTypeView.showDialog();
        //model进行数据获取
        if (updateOrderType != null) {
            updateOrderType.updateOrderType(context, id, type, new IUpdateOrderType.OrderUpdateListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if(updateOrderTypeView != null){
                        updateOrderTypeView.showUpdateQuitOrderResult(isSuccess);
                    }
                }
            });
        }
    }
}
