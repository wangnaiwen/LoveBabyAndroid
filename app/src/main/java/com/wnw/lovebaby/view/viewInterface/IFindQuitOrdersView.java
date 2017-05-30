package com.wnw.lovebaby.view.viewInterface;


import com.wnw.lovebaby.domain.Order;

import java.util.List;

/**
 * Created by wnw on 2017/5/8.
 */

public interface IFindQuitOrdersView {
    void showDialog();
    void showOrders(List<Order> orders);
}
