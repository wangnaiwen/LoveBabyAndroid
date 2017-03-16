package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.ReceAddress;
import com.wnw.lovebaby.domain.User;
import com.wnw.lovebaby.model.modelImpl.DeleteReceAddressModelImp;
import com.wnw.lovebaby.model.modelImpl.FindReceAddressModelImp;
import com.wnw.lovebaby.model.modelInterface.IDeleteReceAddressModel;
import com.wnw.lovebaby.model.modelInterface.IFindReceAddressModel;
import com.wnw.lovebaby.model.modelInterface.IUpdatePasswordModel;
import com.wnw.lovebaby.presenter.base.BasePresenter;
import com.wnw.lovebaby.view.viewInterface.IFindReceAddressView;

import java.util.List;

/**
 * Created by wnw on 2017/3/11.
 */

public class FindReceAddressPresenter extends BasePresenter<IFindReceAddressView>{
    IFindReceAddressModel findReceAddressModel = new FindReceAddressModelImp();
    IDeleteReceAddressModel deleteReceAddressModel = new DeleteReceAddressModelImp();
    //加载数据
    public void findReceAddress(Context context, int userId) {
        //加载进度条

        getView().showDialog();
        //model进行数据获取
        if (findReceAddressModel != null) {

            findReceAddressModel.findReceAddress(context, userId, new IFindReceAddressModel.ReceAddressFindListener() {
                @Override
                public void complete(List<ReceAddress> receAddressList) {
                    getView().findReceAdress(receAddressList);
                }
            });
        }
    }
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
