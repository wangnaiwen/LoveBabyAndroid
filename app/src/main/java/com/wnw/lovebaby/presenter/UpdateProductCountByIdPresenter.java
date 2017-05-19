package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.model.modelImpl.UpdateProductCountByIdImp;
import com.wnw.lovebaby.model.modelInterface.IUpdateProductCountByIdModel;
import com.wnw.lovebaby.view.viewInterface.IUpdateProductCountByIdView;

/**
 * Created by wnw on 2017/5/19.
 */

public class UpdateProductCountByIdPresenter {
    private Context context;
    //view
    private IUpdateProductCountByIdView updateProductCountByIdView;
    //model
    private IUpdateProductCountByIdModel updateProductCountByIdModel = new UpdateProductCountByIdImp();
    //ͨ通过构造函数传入view

    public UpdateProductCountByIdPresenter(Context context,IUpdateProductCountByIdView updateProductCountByIdView) {
        this.context = context;
        this.updateProductCountByIdView = updateProductCountByIdView;
    }

    public void setView(IUpdateProductCountByIdView updateProductCountByIdView){
        this.updateProductCountByIdView = updateProductCountByIdView;
    }

    //加载数据
    public void updateProductCountById(int id, int count) {
        //加载进度条
        updateProductCountByIdView.showDialog();
        //model进行数据获取
        if(updateProductCountByIdModel != null){
            updateProductCountByIdModel.updateProductCountById(context, id, count, new IUpdateProductCountByIdModel.UpdateProductCountByIdListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (updateProductCountByIdView != null){
                        updateProductCountByIdView.showUpdateProductCountResult(isSuccess);
                    }
                }
            });
        }
    }
}
