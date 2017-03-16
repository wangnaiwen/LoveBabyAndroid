package com.wnw.lovebaby.presenter;
import android.content.Context;
import com.wnw.lovebaby.domain.ReceAddress;
import com.wnw.lovebaby.model.modelImpl.InsertReceAddressModelImp;
import com.wnw.lovebaby.model.modelInterface.IInsertReceAddressModel;
import com.wnw.lovebaby.presenter.base.BasePresenter;
import com.wnw.lovebaby.util.LogUtil;
import com.wnw.lovebaby.view.viewInterface.IInsertReceAddressView;

/**
 * Created by wnw on 2017/3/11.
 */

public class InsertReceAddressPresenter extends BasePresenter<IInsertReceAddressView> {

    IInsertReceAddressModel insertReceAddressModel = new InsertReceAddressModelImp();

    //加载数据
    public void insertReceAddresss(Context context, ReceAddress receAddress) {

        LogUtil.d("wnwI", "presenter:"+receAddress.getUserId()+receAddress.getPhone()+receAddress.getProvince());

        //加载进度条
        getView().showDialog();
        //model进行数据获取
        if (insertReceAddressModel != null) {
            insertReceAddressModel.insertReceAddress(context, receAddress, new IInsertReceAddressModel.ReceAddressInsertListener() {
                @Override
                public void complete(ReceAddress address) {
                    getView().insertReceAddress(address);
                }
            });
        }
    }
}
