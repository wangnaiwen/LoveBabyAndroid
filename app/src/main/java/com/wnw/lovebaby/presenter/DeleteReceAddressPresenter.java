package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.model.modelImpl.DeleteReceAddressModelImp;
import com.wnw.lovebaby.model.modelInterface.IDeleteReceAddressModel;
import com.wnw.lovebaby.presenter.base.BasePresenter;
import com.wnw.lovebaby.view.viewInterface.IDeleteReceAddressView;

/**
 * Created by wnw on 2017/3/11.
 */

public class DeleteReceAddressPresenter extends BasePresenter<IDeleteReceAddressView> {

    IDeleteReceAddressModel deleteReceAddressModel = new DeleteReceAddressModelImp();

    //加载数据
    public void deleteReceAddress(Context context, int id) {
        //加载进度条

        getView().showDialog();
        //model进行数据获取
        if (deleteReceAddressModel != null) {
            deleteReceAddressModel.deleteReceAddress(context, id, new IDeleteReceAddressModel.ReceAddressDeleteListener() {
                @Override
                public void complete(boolean isSuccess) {
                    getView().deleteReceAddress(isSuccess);
                }
            });
        }
    }
}
