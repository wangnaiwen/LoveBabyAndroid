package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Withdraw;
import com.wnw.lovebaby.model.modelImpl.InsertWithdrawImpl;
import com.wnw.lovebaby.model.modelInterface.IInsertWithdrawModel;
import com.wnw.lovebaby.view.viewInterface.IInsertWithdrawView;

/**
 * Created by wnw on 2017/4/26.
 */

public class InsertWithdrawPresenter {
    private Context context;
    //view
    private IInsertWithdrawView iInsertWithdrawView;
    //model
    private IInsertWithdrawModel insertWithdrawModel = new InsertWithdrawImpl();
    //ͨ通过构造函数传入view

    public InsertWithdrawPresenter(Context context, IInsertWithdrawView iInsertWithdrawView) {
        this.context = context;
        this.iInsertWithdrawView = iInsertWithdrawView;
    }

    public void setView(IInsertWithdrawView iInsertWithdrawView){
        this.iInsertWithdrawView = iInsertWithdrawView;
    }

    //加载数据
    public void insertWithdraw(Withdraw withdraw) {
        //加载进度条
        iInsertWithdrawView.showDialog();
        //model进行数据获取
        if(insertWithdrawModel != null) {
            insertWithdrawModel.insertWithdraw(context, withdraw, new IInsertWithdrawModel.InsertWithdrawListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if(iInsertWithdrawView != null){
                        iInsertWithdrawView.showInsertWithdrawResult(isSuccess);
                    }
                }
            });
        }
    }
}
