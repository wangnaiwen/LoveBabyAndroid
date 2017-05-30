package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Order;

import java.util.List;


/**
 * Created by wnw on 2017/5/8.
 */

public interface IFindQuitOrders {
    /**
     * 加载数据
     * */
    void findQuitOrders(Context context, int userId,  FindQuitOrdersListener findQuitOrdersListener);

    /**
     * 加载数据完成的回调
     * */
    interface FindQuitOrdersListener{
        void complete(List<Order> orders);
    }
}
