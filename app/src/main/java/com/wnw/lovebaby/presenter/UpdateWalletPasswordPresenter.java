package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.model.modelImpl.UpdateWalletPasswordModelImpl;
import com.wnw.lovebaby.model.modelInterface.IUpdateWalletPasswordModel;
import com.wnw.lovebaby.view.viewInterface.IUpdateWalletPasswordView;

/**
 * Created by wnw on 2017/4/16.
 */

public class UpdateWalletPasswordPresenter {
    private Context context;
    private IUpdateWalletPasswordView updateWalletPasswordView;
    private IUpdateWalletPasswordModel updateWalletPasswordModel = new UpdateWalletPasswordModelImpl();

    public UpdateWalletPasswordPresenter(Context context,IUpdateWalletPasswordView updateWalletPasswordView){
        this.context = context;
        this.updateWalletPasswordView = updateWalletPasswordView;
    }

    public void setUpdateWalletPasswordView(){
        this.updateWalletPasswordView = updateWalletPasswordView;
    }

    //加载数据
    public void updateWalletPassword(int userId, int password) {
        //加载进度条
        updateWalletPasswordView.showDialog();
        //model进行数据获取
        if(updateWalletPasswordModel != null) {
            updateWalletPasswordModel.updateWalletPassword(context, userId, password, new IUpdateWalletPasswordModel.WalletPasswordUpdateListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if(updateWalletPasswordView != null){
                        updateWalletPasswordView.updateWalletPasswordResult(isSuccess);
                    }
                }
            });
        }
    }

}
