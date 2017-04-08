package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Pr;
import com.wnw.lovebaby.model.modelImpl.InsertPrModelImpl;
import com.wnw.lovebaby.model.modelInterface.IInsertPrModel;
import com.wnw.lovebaby.view.viewInterface.IInsertPrView;

/**
 * Created by wnw on 2017/4/5.
 */

public class InsertPrPresenter {
    private Context context;
    //view
    private IInsertPrView insertPrView;
    //model
    private IInsertPrModel insertPrModel = new InsertPrModelImpl();
    //ͨ通过构造函数传入view

    public InsertPrPresenter(Context context, IInsertPrView insertPrView) {
        this.context = context;
        this.insertPrView = insertPrView;
    }
    //将最新的View传递过来，在Destroy被销毁时，将setView(null)
    public void setView(IInsertPrView insertPrView){
        this.insertPrView = insertPrView;
    }

    //加载数据
    public void deleteShoppingCar(Pr pr) {
        //加载进度条
        insertPrView.showDialog();
        //model进行数据获取
        if(insertPrModel != null){
            insertPrModel.insertPr(context, pr, new IInsertPrModel.PrInsertListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if(insertPrView != null){
                        insertPrView.showResult(isSuccess);
                    }
                }
            });
        }
    }
}
