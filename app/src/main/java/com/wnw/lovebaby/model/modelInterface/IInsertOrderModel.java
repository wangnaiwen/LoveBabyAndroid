package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Order;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IInsertOrderModel {
    /**
     * 加载数据
     * */
    void insertOrder(Context context, Order order,OrderInsertListener orderInsertListener);

    /**
     * 加载数据完成的回调
     * */
    interface OrderInsertListener{
        void complete(boolean isSuccess,int id);
    }
}
