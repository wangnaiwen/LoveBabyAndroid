package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Order;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IUpdateOrderModel {
    /**
     * 加载数据
     * */
    void updateOrder(Context context, Order order,OrderUpdateListener orderUpdateListener);

    /**
     * 加载数据完成的回调
     * */
    interface OrderUpdateListener{
        void complete(boolean isSuccess);
    }
}
