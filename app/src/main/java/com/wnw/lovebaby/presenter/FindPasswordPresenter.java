package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.User;
import com.wnw.lovebaby.model.modelInterface.IUpdatePasswordModel;
import com.wnw.lovebaby.model.modelImpl.UpdatePasswordModelImp;
import com.wnw.lovebaby.presenter.base.FindPasswordBasePresenter;
import com.wnw.lovebaby.view.viewInterface.IFindPasswordView;

/**
 * Created by wnw on 2017/3/7.
 */

public class FindPasswordPresenter extends FindPasswordBasePresenter<IFindPasswordView> {
    IUpdatePasswordModel updatePasswordModel = new UpdatePasswordModelImp();

    //加载数据
    public void findPassword(Context context, String phone, String password) {
        //加载进度条

        getView().showDialog();
        //model进行数据获取
        if (updatePasswordModel != null) {
            updatePasswordModel.updateUserPassword(context, phone, password, new IUpdatePasswordModel.UpdateUserPasswordListener() {
                @Override
                public void complete(User user) {
                    getView().findPassword(user);
                }
            });
        }
    }
}
