package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.ReceAddress;
import com.wnw.lovebaby.model.modelImpl.UpdateReceAddressModelImp;
import com.wnw.lovebaby.model.modelInterface.IUpdateReceAddressModel;
import com.wnw.lovebaby.presenter.base.BasePresenter;
import com.wnw.lovebaby.view.viewInterface.IUpdateReceAddressView;

/**
 * Created by wnw on 2017/3/11.
 */

public class UpdateReceAddressPresenter extends BasePresenter<IUpdateReceAddressView> {
    IUpdateReceAddressModel updateReceAddressModel = new UpdateReceAddressModelImp();
    //加载数据
    public void updateReceAddresss(Context context, ReceAddress receAddress) {

        //加载进度条
        getView().showDialog();
        //model进行数据获取
        if (updateReceAddressModel != null) {
            updateReceAddressModel.updateReceAddress(context, receAddress, new IUpdateReceAddressModel.ReceAddressUpdateListener() {
                @Override
                public void complete(boolean isSuccess) {
                    getView().updateReceAddress(isSuccess);
                }
            });
        }
    }
}
