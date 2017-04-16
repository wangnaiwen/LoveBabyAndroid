package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.model.modelImpl.InsertOrderModelImpl;
import com.wnw.lovebaby.model.modelInterface.IInsertOrderModel;
import com.wnw.lovebaby.view.viewInterface.IInsertOrderView;

/**
 * Created by wnw on 2017/4/11.
 */

public class InsertOrderPresenter {
    private Context context;
    //view
    private IInsertOrderView insertOrderView;
    //model
    private IInsertOrderModel insertOrderModel = new InsertOrderModelImpl();
    //ͨ通过构造函数传入view

    public InsertOrderPresenter(Context context, IInsertOrderView insertOrderView) {
        super();
        this.context = context;
        this.insertOrderView = insertOrderView;
    }

    public void setInsertOrderView(IInsertOrderView insertOrderView){
        this.insertOrderView = insertOrderView;
    }

    //加载数据
    public void insertOrder(Order order) {
        //加载进度条
        insertOrderView.showDialog();
        //model进行数据获取
        if(insertOrderModel != null) {
            insertOrderModel.insertOrder(context, order, new IInsertOrderModel.OrderInsertListener() {
                @Override
                public void complete(boolean isSuccess, int id) {
                    if (insertOrderView != null){
                        insertOrderView.showInsertOrderResult(isSuccess, id);
                    }
                }
            });
        }
    }
}
