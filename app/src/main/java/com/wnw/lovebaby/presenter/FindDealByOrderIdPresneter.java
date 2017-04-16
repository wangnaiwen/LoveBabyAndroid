package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Deal;
import com.wnw.lovebaby.model.modelImpl.FindDealByOrderIdModelImpl;
import com.wnw.lovebaby.model.modelInterface.IFindDealByOrderIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindDealByOrderIdView;

import java.util.List;

/**
 * Created by wnw on 2017/4/11.
 */

public class FindDealByOrderIdPresneter {
    private Context context;
    //view
    private IFindDealByOrderIdView findDealByOrderIdView;
    //model
    private IFindDealByOrderIdModel findDealByOrderIdModel = new FindDealByOrderIdModelImpl();
    //ͨ通过构造函数传入view

    public FindDealByOrderIdPresneter(Context context,IFindDealByOrderIdView findDealByOrderIdView) {
        this.context = context;
        this.findDealByOrderIdView = findDealByOrderIdView;
    }

    public void setFindDealByOrderIdView(IFindDealByOrderIdView findDealByOrderIdView){
        this.findDealByOrderIdView = findDealByOrderIdView;
    }

    //加载数据
    public void findDealByOrderId(int orderId) {
        //加载进度条
        findDealByOrderIdView.showDialog();
        //model进行数据获取
        if(findDealByOrderIdModel != null) {
            findDealByOrderIdModel.findDealByOrderId(context, orderId, new IFindDealByOrderIdModel.DealFindByOrderIdListener() {
                @Override
                public void complete(List<Deal> deals) {
                    findDealByOrderIdView.showDealsByOrderId(deals);
                }
            });
        }
    }
}
