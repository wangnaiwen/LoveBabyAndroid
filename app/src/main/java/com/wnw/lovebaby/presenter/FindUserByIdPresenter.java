package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.User;
import com.wnw.lovebaby.model.modelImpl.FindUserByIdImpl;
import com.wnw.lovebaby.model.modelInterface.IFindUserByIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindUserByIdView;


/**
 * Created by wnw on 2017/4/23.
 */

public class FindUserByIdPresenter {
    private Context context;
    //view
    private IFindUserByIdView findUserByIdView;
    //model
    private IFindUserByIdModel findUserByIdModel = new FindUserByIdImpl();
    //ͨ通过构造函数传入view

    public FindUserByIdPresenter(Context context,IFindUserByIdView findUserByIdView) {
        super();
        this.context = context;
        this.findUserByIdView = findUserByIdView;
    }

    //将最新的View传递过来，在Destroy被销毁时，将setView(null)
    public void setView(IFindUserByIdView findUserByIdView){
        this.findUserByIdView = findUserByIdView;
    }

    //加载数据
    public void findUserById(int id) {
        //加载进度条
        findUserByIdView.showDialog();
        //model进行数据获取
        if(findUserByIdModel != null){
            findUserByIdModel.findUserById(context, id, new IFindUserByIdModel.FindUserByIdListener() {
                @Override
                public void complete(User user) {
                    if(findUserByIdView != null){
                        findUserByIdView.showUserById(user);
                    }
                }
            });
        }
    }
}
