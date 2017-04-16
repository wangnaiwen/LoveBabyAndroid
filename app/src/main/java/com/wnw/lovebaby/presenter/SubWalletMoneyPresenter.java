package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.model.modelImpl.SubWalletMoneyModelImpl;
import com.wnw.lovebaby.model.modelInterface.ISubWalletMoneyModel;
import com.wnw.lovebaby.view.viewInterface.ISubWalletMoneyView;

/**
 * Created by wnw on 2017/4/16.
 */

public class SubWalletMoneyPresenter {
    private Context context;
    private ISubWalletMoneyView subWalletMoneyView;
    private ISubWalletMoneyModel subWalletMoneyModel = new SubWalletMoneyModelImpl();

    public SubWalletMoneyPresenter(Context context, ISubWalletMoneyView subWalletMoneyView){
        this.context = context;
        this.subWalletMoneyView = subWalletMoneyView;
    }

    public void setSubWalletMoneyView(ISubWalletMoneyView subWalletMoneyView){
        this.subWalletMoneyView = subWalletMoneyView;
    }

    //加载数据
    public void subWalletMoney(int userId, int money) {
        //加载进度条
        subWalletMoneyView.showDialog();
        //model进行数据获取
        if(subWalletMoneyModel != null) {
            subWalletMoneyModel.subWalletMoney(context, userId, money, new ISubWalletMoneyModel.WalletMoneySubListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if(subWalletMoneyView != null){
                        subWalletMoneyView.subWalletMoneyResult(isSuccess);
                    }
                }
            });
        }
    }
}
