package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Mc;
import com.wnw.lovebaby.model.modelImpl.FindMcByIdImpl;
import com.wnw.lovebaby.model.modelInterface.IFindMcByIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindMcByIdView;

/**
 * Created by wnw on 2017/4/20.
 */

public class FindMcByIdPresenter {
    private Context context;
    private IFindMcByIdView findMcByIdView;
    private IFindMcByIdModel findMcByIdModel = new FindMcByIdImpl();

    public FindMcByIdPresenter(Context context, IFindMcByIdView findMcByIdView) {
        this.context = context;
        this.findMcByIdView = findMcByIdView;
    }

    public void View(IFindMcByIdView findMcByIdView){
        this.findMcByIdView = findMcByIdView;
    }

    //加载数据
    public void findMcbyId(int id) {
        //加载进度条
        findMcByIdView.showDialog();
        //model进行数据获取
        if(findMcByIdModel != null) {
            findMcByIdModel.findMcById(context, id, new IFindMcByIdModel.FindMcByIdListener() {
                @Override
                public void complete(Mc mc) {
                    if(findMcByIdView != null){
                        findMcByIdView.showMc(mc);
                    }
                }
            });
        }
    }
}
