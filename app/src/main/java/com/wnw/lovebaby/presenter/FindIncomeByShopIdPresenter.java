package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.model.modelImpl.FindIncomeByShopIdImpl;
import com.wnw.lovebaby.model.modelInterface.IFindIncomeByShopIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindIncomeByShopIdView;

/**
 * Created by wnw on 2017/4/22.
 */

public class FindIncomeByShopIdPresenter{
    private Context context;
    //view
    private IFindIncomeByShopIdView findIncomeByShopIdView;
    //model
    private IFindIncomeByShopIdModel findIncomeByShopIdModel = new FindIncomeByShopIdImpl();
    //ͨ通过构造函数传入view

    public FindIncomeByShopIdPresenter(Context context, IFindIncomeByShopIdView findIncomeByShopIdView) {
        super();
        this.context = context;
        this.findIncomeByShopIdView = findIncomeByShopIdView;
    }

    public void setView(IFindIncomeByShopIdView findIncomeByShopIdView){
        this.findIncomeByShopIdView = findIncomeByShopIdView;
    }

    //加载数据
    public void load(int shopId) {
        //加载进度条
        findIncomeByShopIdView.showDialog();
        //model进行数据获取
        if(findIncomeByShopIdModel != null){
            findIncomeByShopIdModel.findIncomeByShopId(context, shopId, new IFindIncomeByShopIdModel.FindIncomeByShopIdListener() {
                @Override
                public void complete(int income) {
                    if(findIncomeByShopIdView != null){
                        findIncomeByShopIdView.showIncomeByShopId(income);
                    }
                }
            });
        }
    }
}
