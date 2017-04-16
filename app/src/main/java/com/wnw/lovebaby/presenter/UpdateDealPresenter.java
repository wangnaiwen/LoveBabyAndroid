package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Deal;
import com.wnw.lovebaby.model.modelImpl.UpdateDealModelImpl;
import com.wnw.lovebaby.model.modelInterface.IUpdateDealModel;
import com.wnw.lovebaby.view.viewInterface.IUpdateDealView;

/**
 * Created by wnw on 2017/4/11.
 */

public class UpdateDealPresenter {
    private Context context;
    //view
    private IUpdateDealView updateDealView;
    //model
    private IUpdateDealModel updateDealModel = new UpdateDealModelImpl();
    //ͨ通过构造函数传入view

    public UpdateDealPresenter(Context context, IUpdateDealView updateDealView) {
        super();
        this.context = context;
        this.updateDealView = updateDealView;
    }

    public void setUpdateDealView(IUpdateDealView updateDealView){
        this.updateDealView = updateDealView;
    }

    //加载数据
    public void updateDeal(Deal deal) {
        //加载进度条
        updateDealView.showDialog();
        //model进行数据获取
        if(updateDealModel != null){
            updateDealModel.updateDeal(context, deal, new IUpdateDealModel.DealUpdateListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if(updateDealView != null){
                        updateDealView.showUpdateDealResult(isSuccess);
                    }
                }
            });
        }
    }
}
