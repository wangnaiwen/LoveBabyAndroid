package com.wnw.lovebaby.presenter;

import android.content.Context;
import com.wnw.lovebaby.domain.UserInfo;
import com.wnw.lovebaby.model.modelImpl.UpdateUserInfoMoelImp;
import com.wnw.lovebaby.model.modelInterface.IUpdateUserInfoModel;
import com.wnw.lovebaby.presenter.base.BasePresenter;
import com.wnw.lovebaby.view.viewInterface.IUpdateUserInfoView;

/**
 * Created by wnw on 2017/3/11.
 */

public class UpdateUserInfoPresenter extends BasePresenter<IUpdateUserInfoView> {

    IUpdateUserInfoModel updateUserInfoModel = new UpdateUserInfoMoelImp();
    //加载数据
    public void updateUserInfo(Context context, UserInfo userInfo) {

        //加载进度条
        getView().showDialog();
        //model进行数据获取
        if(updateUserInfoModel != null){
            updateUserInfoModel.updateUserInfo(context, userInfo, new IUpdateUserInfoModel.UserInfoUpdateListener() {
                @Override
                public void complete(boolean isSuccess) {
                    getView().updateUserInfo(isSuccess);
                }
            });
        }
    }
}
