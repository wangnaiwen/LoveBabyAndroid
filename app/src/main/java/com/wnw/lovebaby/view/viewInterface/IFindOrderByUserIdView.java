package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.Order;

import java.util.List;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IFindOrderByUserIdView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showOrdersByUserId(List<Order> orders);
}
