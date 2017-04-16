package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Deal;
import com.wnw.lovebaby.model.modelImpl.FindDealByIdModelImpl;
import com.wnw.lovebaby.model.modelInterface.IFindDealByIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindDealByIdView;

/**
 * Created by wnw on 2017/4/11.
 */

public class FindDealByIdPresenter {
    private Context context;
    //view
    private IFindDealByIdView findDealByIdView;
    //model
    private IFindDealByIdModel findDealByIdModel = new FindDealByIdModelImpl();
    //ͨ通过构造函数传入view

    public FindDealByIdPresenter(Context context,IFindDealByIdView findDealByIdView) {
        this.context = context;
        this.findDealByIdView = findDealByIdView;
    }

    public void setFindDealByIdView(IFindDealByIdView findDealByIdView){
        this.findDealByIdView = findDealByIdView;
    }

    //加载数据
    public void findDealById(int id) {
        //加载进度条
        findDealByIdView.showDialog();
        //model进行数据获取
        if(findDealByIdModel != null) {
            findDealByIdModel.findDealById(context, id, new IFindDealByIdModel.DealFindByIdListener() {
                @Override
                public void complete(Deal deal) {
                    findDealByIdView.showDealById(deal);
                }
            });
        }
    }
}
