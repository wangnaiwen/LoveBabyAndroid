package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Sc;
import com.wnw.lovebaby.model.modelImpl.FindScByIdImpl;
import com.wnw.lovebaby.model.modelInterface.IFindScByIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindScByIdView;

/**
 * Created by wnw on 2017/4/20.
 */

public class FindScByIdPresenter {
    private Context context;
    private IFindScByIdView findScByIdView;
    private IFindScByIdModel findScByIdModel = new FindScByIdImpl();

    public FindScByIdPresenter(Context context, IFindScByIdView findScByIdView) {
        this.context = context;
        this.findScByIdView = findScByIdView;
    }

    public void View(IFindScByIdView findScByIdView){
        this.findScByIdView = findScByIdView;
    }

    //加载数据
    public void findScbyId(int id) {
        //加载进度条
        findScByIdView.showDialog();
        //model进行数据获取
        if(findScByIdModel != null) {
            findScByIdModel.findScById(context, id, new IFindScByIdModel.FindScByIdListener() {
                @Override
                public void complete(Sc sc) {
                    if(findScByIdView != null){
                        findScByIdView.showSc(sc);
                    }
                }
            });
        }
    }
}
