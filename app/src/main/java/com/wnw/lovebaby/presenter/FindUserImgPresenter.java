package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.UserInfo;
import com.wnw.lovebaby.model.modelImpl.FindUserInfoModelImp;
import com.wnw.lovebaby.model.modelInterface.IFindUserInfoModel;
import com.wnw.lovebaby.view.viewInterface.IFindUserImgView;

/**
 * Created by wnw on 2017/5/20.
 */

public class FindUserImgPresenter  {
    private Context context;
    //view
    private IFindUserImgView findUserImgView;
    //model
    private IFindUserInfoModel findUserInfoModel = new FindUserInfoModelImp();
    //ͨ通过构造函数传入view

    public FindUserImgPresenter(Context context,IFindUserImgView findUserImgView) {
        super();
        this.context = context;
        this.findUserImgView = findUserImgView;
    }

    //将最新的View传递过来，在Destroy被销毁时，将setView(null)
    public void setView(IFindUserImgView findUserImgView){
        this.findUserImgView = findUserImgView;
    }

    //加载数据
    public void findUserInfo(int userId) {
        //加载进度条
        findUserImgView.showDialog();
        //model进行数据获取
        if(findUserInfoModel != null){
            findUserInfoModel.FindUserInfo(context, userId, new IFindUserInfoModel.UserInfoLoadingListener() {
                @Override
                public void complete(UserInfo userInfo) {
                    if (findUserImgView != null){
                        findUserImgView.showUserInfo(userInfo);
                    }
                }
            });
        }
    }
}
