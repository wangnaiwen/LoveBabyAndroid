package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.model.modelImpl.FindWalletMoneyByUserIdImpl;
import com.wnw.lovebaby.model.modelInterface.IFindWalletMoneyByUserIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindWalletMoneyByUserIdView;

/**
 * Created by wnw on 2017/4/16.
 */

public class FindWalletMoneyByUserIdPresenter {

    private Context context;
    private IFindWalletMoneyByUserIdView findWalletMoneyByUserIdView;
    private IFindWalletMoneyByUserIdModel findWalletMoneyByUserIdModel = new FindWalletMoneyByUserIdImpl();

    public FindWalletMoneyByUserIdPresenter(Context context, IFindWalletMoneyByUserIdView findWalletMoneyByUserIdView){
        this.context = context;
        this.findWalletMoneyByUserIdView = findWalletMoneyByUserIdView;
    }

    public void setFindWalletMoneyByUserIdView(IFindWalletMoneyByUserIdView findWalletMoneyByUserIdView){
        this.findWalletMoneyByUserIdView = findWalletMoneyByUserIdView;
    }

    //加载数据
    public void findWalletMoneyByUserId(int userId) {
        //加载进度条
        findWalletMoneyByUserIdView.showDialog();
        //model进行数据获取
        if(findWalletMoneyByUserIdModel != null) {
            findWalletMoneyByUserIdModel.findWalletMoneyByUserId(context, userId, new IFindWalletMoneyByUserIdModel.WalletMoneyFindByUserIdListener() {
                @Override
                public void complete(int money) {
                    if(findWalletMoneyByUserIdView != null){
                        findWalletMoneyByUserIdView.showFindWalletMoneyByUserIdResult(money);
                    }
                }
            });

        }
    }

}
