package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Sc;
import com.wnw.lovebaby.model.modelImpl.FindScByMcIdImpl;
import com.wnw.lovebaby.model.modelInterface.IFindScByMcIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindScByMcIdView;

import java.util.List;

/**
 * Created by wnw on 2017/4/20.
 */

public class FindScByMcIdPresenter {
    private Context context;
    private IFindScByMcIdView findScByMcIdView;
    private IFindScByMcIdModel findScByMcIdModel = new FindScByMcIdImpl();

    public FindScByMcIdPresenter(Context context, IFindScByMcIdView findScByMcIdView) {
        this.context = context;
        this.findScByMcIdView = findScByMcIdView;
    }

    public void View(IFindScByMcIdView iFindScByMcIdView){
        this.findScByMcIdView = findScByMcIdView;
    }

    //加载数据
    public void findScbyMcId(int mcId) {
        //加载进度条
        findScByMcIdView.showDialog();
        //model进行数据获取
        if(findScByMcIdModel != null) {
            findScByMcIdModel.findScByMcId(context, mcId, new IFindScByMcIdModel.FindScByMcIdListener() {
                @Override
                public void complete(List<Sc> scs) {
                    if(findScByMcIdView != null){
                        findScByMcIdView.showScs(scs);
                    }
                }
            });
        }
    }
}
