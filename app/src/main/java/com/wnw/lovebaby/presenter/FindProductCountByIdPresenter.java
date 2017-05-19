package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.model.modelImpl.FindProductCountByIdImp;
import com.wnw.lovebaby.model.modelInterface.IFindProductCountByIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindProductCountByIdView;

/**
 * Created by wnw on 2017/5/19.
 */

public class FindProductCountByIdPresenter {
    private Context context;
    //view
    private IFindProductCountByIdView findProductCountByIdView;
    //model
    private IFindProductCountByIdModel findProductCountByIdModel = new FindProductCountByIdImp();
    //ͨ通过构造函数传入view

    public FindProductCountByIdPresenter(Context context,IFindProductCountByIdView findProductCountByIdView) {
        this.context = context;
        this.findProductCountByIdView = findProductCountByIdView;
    }

    public void setView(IFindProductCountByIdView findProductCountByIdView){
        this.findProductCountByIdView = findProductCountByIdView;
    }

    //加载数据
    public void findProductCountById(final int id) {
        //加载进度条
        findProductCountByIdView.showDialog();
        //model进行数据获取
        if(findProductCountByIdModel != null){
            findProductCountByIdModel.findProductCountById(context, id, new IFindProductCountByIdModel.FindProductCountByIdListener() {
                @Override
                public void complete(int count) {
                    if (findProductCountByIdView != null){
                        findProductCountByIdView.showProductCount(count);
                    }
                }
            });
        }
    }
}
