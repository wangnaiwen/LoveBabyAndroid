package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Order;

import java.util.List;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IFindOrderByUserIdModel {
    /**
     * 加载数据
     * */
    void findOrderByUserId(Context context, int userId, OrderFindByUserIdListener orderFindByUserIdListener);

    /**
     * 加载数据完成的回调
     * */
    interface OrderFindByUserIdListener{
        void complete(List<Order> orders, List<String> names);
    }
}
