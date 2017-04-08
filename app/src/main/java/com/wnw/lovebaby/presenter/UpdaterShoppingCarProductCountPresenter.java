package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.model.modelImpl.UpdateShoppingCarProductCountModelImpl;
import com.wnw.lovebaby.model.modelInterface.IUpdateShoppingCarProductCountModel;
import com.wnw.lovebaby.view.viewInterface.IUpdateShoppingCarProductCountView;

/**
 * Created by wnw on 2017/4/5.
 */

public class UpdaterShoppingCarProductCountPresenter {
    private Context context;
    //view
    private IUpdateShoppingCarProductCountView updateShoppingCarProductCountView;
    //model
    private IUpdateShoppingCarProductCountModel updateShoppingCarProductCountModel = new UpdateShoppingCarProductCountModelImpl();
    //ͨ通过构造函数传入view

    public UpdaterShoppingCarProductCountPresenter(Context context, IUpdateShoppingCarProductCountView updateShoppingCarProductCountView) {
        this.context = context;
        this.updateShoppingCarProductCountView = updateShoppingCarProductCountView;
    }
    //将最新的View传递过来，在Destroy被销毁时，将setView(null)
    public void setView(IUpdateShoppingCarProductCountView updateShoppingCarProductCountView){
        this.updateShoppingCarProductCountView = updateShoppingCarProductCountView;
    }

    //加载数据
    public void updateShoppingCarCount(int id, int count) {
        //加载进度条
        updateShoppingCarProductCountView.showDialog();
        //model进行数据获取
        if(updateShoppingCarProductCountModel != null){
            updateShoppingCarProductCountModel.updateShoppingCarProductCount(context, id, count, new IUpdateShoppingCarProductCountModel.ShoppingCarProductCountUpdateListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if(updateShoppingCarProductCountView != null){
                        updateShoppingCarProductCountView.showUpdateResult(isSuccess);
                    }
                }
            });
        }
    }
}
