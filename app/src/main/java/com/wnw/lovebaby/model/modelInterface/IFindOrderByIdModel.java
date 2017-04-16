package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Order;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IFindOrderByIdModel {
    /**
     * 加载数据
     * */
    void findOrderById(Context context, int id, OrderFindByIdListener orderFindByIdListener);

    /**
     * 加载数据完成的回调
     * */
    interface OrderFindByIdListener{
        void complete(Order order);
    }
}
