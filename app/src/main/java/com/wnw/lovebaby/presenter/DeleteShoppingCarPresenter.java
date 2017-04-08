package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.model.modelImpl.DeleteShoppingCarModelImpl;
import com.wnw.lovebaby.model.modelInterface.IDeleteShoppingCarModel;
import com.wnw.lovebaby.view.viewInterface.IDeleteShoppingCarView;
import com.wnw.lovebaby.view.viewInterface.IFindPrsByProductIdView;

/**
 * Created by wnw on 2017/4/5.
 */

public class DeleteShoppingCarPresenter {
    private Context context;
    //view
    private IDeleteShoppingCarView deleteShoppingCarView;
    //model
    private IDeleteShoppingCarModel deleteShoppingCarModel = new DeleteShoppingCarModelImpl();
    //ͨ通过构造函数传入view

    public DeleteShoppingCarPresenter(Context context, IDeleteShoppingCarView deleteShoppingCarView) {
        this.context = context;
        this.deleteShoppingCarView = deleteShoppingCarView;
    }
    //将最新的View传递过来，在Destroy被销毁时，将setView(null)
    public void setView(IDeleteShoppingCarView deleteShoppingCarView ){
        this.deleteShoppingCarView = deleteShoppingCarView;
    }

    //加载数据
    public void deleteShoppingCar(int id) {
        //加载进度条
        deleteShoppingCarView.showDialog();
        //model进行数据获取
        if(deleteShoppingCarModel != null){
            deleteShoppingCarModel.deleteShoppingCar(context, id, new IDeleteShoppingCarModel.ShoppingCarDeleteListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (deleteShoppingCarView != null){
                        deleteShoppingCarView.showDeleteResult(isSuccess);
                    }
                }
            });
        }
    }
}
