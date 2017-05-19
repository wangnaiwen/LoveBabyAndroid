package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.model.modelImpl.UpdateUserImgImp;
import com.wnw.lovebaby.model.modelInterface.IUpdateUserImgModel;
import com.wnw.lovebaby.view.viewInterface.IUpdateUserImgView;

/**
 * Created by wnw on 2017/5/19.
 */

public class UpdateUserImgPresenter {
    private Context context;
    //view
    private IUpdateUserImgView iUpdateUserImgView;
    //model
    private IUpdateUserImgModel updateUserImgModel = new UpdateUserImgImp();
    //ͨ通过构造函数传入view

    public UpdateUserImgPresenter(Context context,IUpdateUserImgView iUpdateUserImgView) {
        this.context = context;
        this.iUpdateUserImgView = iUpdateUserImgView;
    }

    public void setView(IUpdateUserImgView iUpdateUserImgView){
        this.iUpdateUserImgView = iUpdateUserImgView;
    }

    //加载数据
    public void updateUserImg(int userId, String image) {
        //加载进度条
        iUpdateUserImgView.showDialog();
        //model进行数据获取
        if(updateUserImgModel != null){
            updateUserImgModel.updateUserImg(context, userId, image, new IUpdateUserImgModel.UserImgUpdateListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (iUpdateUserImgView != null){
                        iUpdateUserImgView.updateUserImg(isSuccess);
                    }
                }
            });
        }
    }
}
