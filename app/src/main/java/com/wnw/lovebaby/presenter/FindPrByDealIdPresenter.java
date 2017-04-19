package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Pr;
import com.wnw.lovebaby.model.modelImpl.FindPrByDealIdImpl;
import com.wnw.lovebaby.model.modelInterface.IFindPrByDealIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindPrByDealIdView;

import java.util.List;

/**
 * Created by wnw on 2017/4/18.
 */

public class FindPrByDealIdPresenter {

    private Context context;
    //view
    private IFindPrByDealIdView IFindPrByDealIdView;
    //model
    private IFindPrByDealIdModel findPrByDealIdModel = new FindPrByDealIdImpl();
    //ͨ通过构造函数传入view

    public FindPrByDealIdPresenter(Context context,IFindPrByDealIdView IFindPrByDealIdView) {
        this.context = context;
        this.IFindPrByDealIdView = IFindPrByDealIdView;
    }

    //将最新的View传递过来，在Destroy被销毁时，将setView(null)
    public void setView(IFindPrByDealIdView IFindPrByDealIdView){
        this.IFindPrByDealIdView = IFindPrByDealIdView;
    }

    //加载数据
    public void findPrsByDealId(int dealId) {
        //加载进度条
        IFindPrByDealIdView.showDialog();
        //model进行数据获取
        if(findPrByDealIdModel != null){
            findPrByDealIdModel.FindPrByDealId(context, dealId, new IFindPrByDealIdModel.PrFindBydealIdListener() {
                @Override
                public void complete(List<Pr> prList) {
                    if(IFindPrByDealIdView != null){
                        IFindPrByDealIdView.showPrs(prList);
                    }
                }
            });
        }
    }

}
