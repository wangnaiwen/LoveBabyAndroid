package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.Order;

import java.util.List;

/**
 * Created by wnw on 2017/4/23.
 */

public interface IFindOrderByInviteeView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showOrdersByInvitee(List<Order> orders);
}
