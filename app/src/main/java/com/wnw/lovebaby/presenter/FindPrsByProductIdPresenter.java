package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Pr;
import com.wnw.lovebaby.model.modelImpl.FindPrsByProductIdModelImpl;
import com.wnw.lovebaby.model.modelInterface.IFindPrsByProductIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindPrsByProductIdView;

import java.util.List;

/**
 * Created by wnw on 2017/4/5.
 */

public class FindPrsByProductIdPresenter {
    private Context context;
    //view
    private IFindPrsByProductIdView findPrsByProductIdView;
    //model
    private IFindPrsByProductIdModel findPrsByProductIdModel = new FindPrsByProductIdModelImpl();
    //ͨ通过构造函数传入view

    public FindPrsByProductIdPresenter(Context context, IFindPrsByProductIdView findPrsByProductIdView) {
        this.context = context;
        this.findPrsByProductIdView = findPrsByProductIdView;
    }

    //将最新的View传递过来，在Destroy被销毁时，将setView(null)
    public void setView(IFindPrsByProductIdView findPrsByProductIdView){
        this.findPrsByProductIdView = findPrsByProductIdView;
    }

    //加载数据
    public void findPrsByProductId(int productId, int number) {
        //加载进度条
        findPrsByProductIdView.showDialog();
        //model进行数据获取
        if(findPrsByProductIdModel != null){
            findPrsByProductIdModel.FindPrByProductId(context, productId, number, new IFindPrsByProductIdModel.PrFindByProductIdListener() {
                @Override
                public void complete(List<Pr> prList) {
                    if(findPrsByProductIdView != null){
                        findPrsByProductIdView.showPrs(prList);
                    }
                }
            });
        }
    }
}
