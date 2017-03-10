package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.User;
import com.wnw.lovebaby.model.modelInterface.IRegisterModel;
import com.wnw.lovebaby.model.modelImpl.RegisterModelImp;
import com.wnw.lovebaby.presenter.base.RegisterBasePresenter;
import com.wnw.lovebaby.view.viewInterface.IRegisterView;

/**
 * Created by wnw on 2016/10/17.
 */

public class RegisterPresenter extends RegisterBasePresenter<IRegisterView> {
    IRegisterModel mRegisterModel = new RegisterModelImp();


    //加载数据
    public void register(Context context, User user) {
        //加载进度条

        getView().showDialog();
        //model进行数据获取
        if (mRegisterModel != null) {
            mRegisterModel.registerNetUser(context, user, new IRegisterModel.UserRegisterListener(){
                @Override
                public void complete(boolean isSuccess) {
                    //返回给view
                    getView().register(isSuccess);
                }
            });
        }
    }
}
