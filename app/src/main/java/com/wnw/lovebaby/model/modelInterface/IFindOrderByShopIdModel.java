package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Order;

import java.util.List;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IFindOrderByShopIdModel {
    /**
     * 加载数据
     * */
    void findOrderByShopId(Context context, int shopId,int number, OrderFindByShopIdListener orderFindByShopIdListener);

    /**
     * 加载数据完成的回调
     * */
    interface OrderFindByShopIdListener{
        void complete(List<Order> orders, List<String> names);
    }
}
