package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.model.modelImpl.FindWithdrawMoneyByUserIdImpl;
import com.wnw.lovebaby.model.modelInterface.IFindWithdrawMoneyByUserIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindWithdrawMoneyByUserIdView;

/**
 * Created by wnw on 2017/4/26.
 */

public class FindWithdrawMoneyByUserIdPresenter {
    private Context context;
    private IFindWithdrawMoneyByUserIdView findWithdrawMoneyByUserIdView;
    private IFindWithdrawMoneyByUserIdModel findWithdrawMoneyByUserIdModel = new FindWithdrawMoneyByUserIdImpl();

    public FindWithdrawMoneyByUserIdPresenter(Context context,IFindWithdrawMoneyByUserIdView findWithdrawMoneyByUserIdView){
        this.context = context;
        this.findWithdrawMoneyByUserIdView = findWithdrawMoneyByUserIdView;
    }

    public void setView(IFindWithdrawMoneyByUserIdView findWithdrawMoneyByUserIdView){
        this.findWithdrawMoneyByUserIdView = findWithdrawMoneyByUserIdView;
    }

    //加载数据
    public void findWithdrawMoneyByUserId(int userId) {
        //加载进度条
        findWithdrawMoneyByUserIdView.showDialog();
        //model进行数据获取
        if(findWithdrawMoneyByUserIdModel != null) {
            findWithdrawMoneyByUserIdModel.findWithdrawMoneyByUserId(context, userId, new IFindWithdrawMoneyByUserIdModel.FindWithdrawMoneyByUserIdListener() {
                @Override
                public void complete(int money) {
                    if(findWithdrawMoneyByUserIdView != null){
                        findWithdrawMoneyByUserIdView.showWithdrawMoney(money);
                    }
                }
            });

        }
    }
}
