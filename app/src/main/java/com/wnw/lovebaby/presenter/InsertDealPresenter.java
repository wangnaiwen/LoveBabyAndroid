package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Deal;
import com.wnw.lovebaby.model.modelImpl.InsertDealModelImpl;

import com.wnw.lovebaby.model.modelInterface.IInsertDealModel;
import com.wnw.lovebaby.view.viewInterface.IInsertDealView;

/**
 * Created by wnw on 2017/4/11.
 */

public class InsertDealPresenter {
    private Context context;
    //view
    private IInsertDealView insertDealView;
    //model
    private IInsertDealModel insertDealModel = new InsertDealModelImpl();
    //ͨ通过构造函数传入view

    public InsertDealPresenter(Context context, IInsertDealView insertDealView) {
        super();
        this.context = context;
        this.insertDealView = insertDealView;
    }

    public void setInsertDealView(IInsertDealView insertDealView){
        this.insertDealView = insertDealView;
    }

    //加载数据
    public void insertDeal(Deal deal) {
        //加载进度条
        insertDealView.showDialog();
        //model进行数据获取
        if(insertDealModel != null) {
            insertDealModel.insertDeal(context, deal, new IInsertDealModel.DealInsertListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if(insertDealView != null){
                        insertDealView.showInsertDealResult(isSuccess);
                    }
                }
            });
        }
    }
}
