package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Product;
import com.wnw.lovebaby.model.modelImpl.FindProductByScIdImpl;
import com.wnw.lovebaby.model.modelInterface.IFindProductByScIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindProductByScIdView;

import java.util.List;

/**
 * Created by wnw on 2017/4/20.
 */

public class FindProductByScIdPresenter {
    private Context context;
    //view
    private IFindProductByScIdView findProductByScIdView;
    //model
    private IFindProductByScIdModel findProductByScIdModel = new FindProductByScIdImpl();
    //ͨ通过构造函数传入view

    public FindProductByScIdPresenter(Context context,IFindProductByScIdView findProductByScIdView) {
        this.context = context;
        this.findProductByScIdView = findProductByScIdView;
    }

    //将最新的View传递过来，在Destroy被销毁时，将setView(null)
    public void setView(IFindProductByScIdView findProductByScIdView){
        this.findProductByScIdView = findProductByScIdView;
    }

    //加载数据
    public void findProductByScId(int scId, int number) {
        //加载进度条
        findProductByScIdView.showDialog();
        //model进行数据获取
        if(findProductByScIdModel != null){
            findProductByScIdModel.findProductByScId(context, scId, number, new IFindProductByScIdModel.ProductFindByScIdListener() {
                @Override
                public void complete(List<Product> products) {
                    if(findProductByScIdView != null){
                        findProductByScIdView.showProducts(products);
                    }
                }
            });
        }
    }
}
