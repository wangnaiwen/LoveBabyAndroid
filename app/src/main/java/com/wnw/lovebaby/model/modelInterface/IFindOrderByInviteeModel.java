package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Order;

import java.util.List;

/**
 * Created by wnw on 2017/4/23.
 */

public interface IFindOrderByInviteeModel {
    /**
     * 加载数据
     * */
    void findOrderByInvitee(Context context, int invitee, int number, OrderFindByInviteeListener orderFindByInviteeListener);

    /**
     * 加载数据完成的回调
     * */
    interface OrderFindByInviteeListener{
        void complete(List<Order> orders);
    }
}
