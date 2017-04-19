package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Pr;
import com.wnw.lovebaby.model.modelImpl.FindPrsByUserIdModelImpl;
import com.wnw.lovebaby.model.modelInterface.IFindPrsByUserIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindPrsByUserIdView;

import java.util.List;

/**
 * Created by wnw on 2017/4/5.
 */

public class FindPrsByUserIdPresenter {
    private Context context;
    //view
    private IFindPrsByUserIdView findPrsByUserIdView;
    //model
    private IFindPrsByUserIdModel findPrsByUserIdModel = new FindPrsByUserIdModelImpl();
    //ͨ通过构造函数传入view

    public FindPrsByUserIdPresenter(Context context,IFindPrsByUserIdView findPrsByUserIdView) {
        super();
        this.context = context;
        this.findPrsByUserIdView = findPrsByUserIdView;
    }

    //将最新的View传递过来，在Destroy被销毁时，将setView(null)
    public void setView(IFindPrsByUserIdView findPrsByUserIdView){
        this.findPrsByUserIdView = findPrsByUserIdView;
    }

    //加载数据
    public void findPrsByUserId(int userId, int number) {
        //加载进度条
        findPrsByUserIdView.showDialog();
        //model进行数据获取
        if(findPrsByUserIdModel != null){
            findPrsByUserIdModel.FindPrByUserId(context, userId, number, new IFindPrsByUserIdModel.PrFindByUserIdListener() {
                @Override
                public void complete(List<Pr> prList) {
                    if(findPrsByUserIdView != null){
                        findPrsByUserIdView.showPrs(prList);
                    }
                }
            });
        }
    }
}
