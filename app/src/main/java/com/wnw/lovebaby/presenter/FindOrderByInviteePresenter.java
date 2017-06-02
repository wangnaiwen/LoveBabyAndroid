package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.model.modelImpl.FindOrderByInviteeImpl;
import com.wnw.lovebaby.model.modelInterface.IFindOrderByInviteeModel;
import com.wnw.lovebaby.view.viewInterface.IFindOrderByInviteeView;

import java.util.List;

/**
 * Created by wnw on 2017/4/23.
 */

public class FindOrderByInviteePresenter {
    private Context context;
    //view
    private IFindOrderByInviteeView findOrderByInviteeView;
    //model
    private IFindOrderByInviteeModel findOrderByInviteeModel = new FindOrderByInviteeImpl();
    //ͨ通过构造函数传入view

    public FindOrderByInviteePresenter(Context context, IFindOrderByInviteeView findOrderByInviteeView) {
        this.context = context;
        this.findOrderByInviteeView = findOrderByInviteeView;
    }

    public void setFindOrderByShopIdView(IFindOrderByInviteeView findOrderByInviteeView){
        this.findOrderByInviteeView = findOrderByInviteeView;
    }

    //加载数据
    public void findOrderByInvitee(int invitee, int number) {
        //加载进度条
        findOrderByInviteeView.showDialog();
        //model进行数据获取
        if(findOrderByInviteeModel != null) {
            findOrderByInviteeModel.findOrderByInvitee(context, invitee, number, new IFindOrderByInviteeModel.OrderFindByInviteeListener() {
                @Override
                public void complete(List<Order> orders, List<String> names) {
                    if(findOrderByInviteeView != null){
                        findOrderByInviteeView.showOrdersByInvitee(orders,names);
                    }
                }
            });
        }
    }
}
