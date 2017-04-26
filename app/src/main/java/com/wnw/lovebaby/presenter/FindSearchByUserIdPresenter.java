package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Search;
import com.wnw.lovebaby.model.modelImpl.FindSearchByUserIdModel;
import com.wnw.lovebaby.model.modelInterface.IFindSearchByUserIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindSearchByUserIdView;

import java.util.List;

/**
 * Created by wnw on 2017/4/26.
 */

public class FindSearchByUserIdPresenter {
    private Context context;
    //view
    private IFindSearchByUserIdView findSearchByUserIdView;
    //model
    private IFindSearchByUserIdModel findSearchByUserIdModel = new FindSearchByUserIdModel();
    //ͨ通过构造函数传入view

    public FindSearchByUserIdPresenter(Context context, IFindSearchByUserIdView findSearchByUserIdView) {
        this.context = context;
        this.findSearchByUserIdView = findSearchByUserIdView;
    }

    public void setView(IFindSearchByUserIdView findSearchByUserIdView){
        this.findSearchByUserIdView = findSearchByUserIdView;
    }

    //加载数据
    public void findSearchByUserId(int userId) {
        //加载进度条
        findSearchByUserIdView.showDialog();
        //model进行数据获取
        if(findSearchByUserIdModel != null) {
            findSearchByUserIdModel.findSearchByUserId(context, userId, new IFindSearchByUserIdModel.FindSearchByUserIdListener() {
                @Override
                public void complete(List<Search> searches) {
                    if(findSearchByUserIdView != null){
                        findSearchByUserIdView.showSearchByUserId(searches);
                    }
                }
            });
        }
    }
}
