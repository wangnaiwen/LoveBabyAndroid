package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.User;
import com.wnw.lovebaby.model.modelInterface.ILoginModel;
import com.wnw.lovebaby.model.modelImpl.LoginModelImp;
import com.wnw.lovebaby.presenter.base.BasePresenter;
import com.wnw.lovebaby.view.viewInterface.ILoginView;


/**
 * Created by wnw on 2016/10/17.
 */

public class LoginPresenter extends BasePresenter<ILoginView> {

    ILoginModel mLoginModel = new LoginModelImp();


    //加载数据
    public void validate(Context context, User user) {
        //加载进度条

        getView().showDialog();
        //model进行数据获取
        if (mLoginModel != null) {
            mLoginModel.loadUser(context, user, new ILoginModel.UserLoadingListener() {
                @Override
                public void complete(User user) {
                    //返回给view
                    getView().validate(user);
                }
            });
        }
    }
}