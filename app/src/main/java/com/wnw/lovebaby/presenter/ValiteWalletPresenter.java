package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.model.modelImpl.ValiteWalletModelImpl;
import com.wnw.lovebaby.model.modelInterface.IValiteWalletModel;
import com.wnw.lovebaby.view.viewInterface.IValiteWalletView;

/**
 * Created by wnw on 2017/4/16.
 */

public class ValiteWalletPresenter {
    private Context context;
    private IValiteWalletView valiteWalletView;
    private IValiteWalletModel valiteWalletModel = new ValiteWalletModelImpl();

    public ValiteWalletPresenter(Context context,IValiteWalletView valiteWalletView){
        this.context = context;
        this.valiteWalletView = valiteWalletView;
    }

    public void setValiteWalletView(IValiteWalletView valiteWalletView){
        this.valiteWalletView = valiteWalletView;
    }

    //加载数据
    public void valiteWallet(int userId, String password) {
        //加载进度条
        valiteWalletView.showDialog();
        //model进行数据获取
        if(valiteWalletModel != null) {
            valiteWalletModel.valiteWallet(context, userId, password, new IValiteWalletModel.WalletValiteListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if(valiteWalletView != null){
                        valiteWalletView.valiteWalletResult(isSuccess);
                    }
                }
            });
        }
    }
}
