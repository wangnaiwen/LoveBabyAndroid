package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Mc;
import com.wnw.lovebaby.model.modelImpl.FindMcsImpl;
import com.wnw.lovebaby.model.modelInterface.IFindMcsModel;
import com.wnw.lovebaby.view.viewInterface.IFindMcsVIew;

import java.util.List;

/**
 * Created by wnw on 2017/4/20.
 */

public class FindMcsPresenter {
    private Context context;
    private IFindMcsVIew findMcsVIew;
    private IFindMcsModel findMcsModel = new FindMcsImpl();

    public FindMcsPresenter(Context context, IFindMcsVIew findMcsVIew) {
        this.context = context;
        this.findMcsVIew = findMcsVIew;
    }

    public void View(IFindMcsVIew findMcsVIew){
        this.findMcsVIew = findMcsVIew;
    }

    //加载数据
    public void findMcs() {
        //加载进度条
        findMcsVIew.showDialog();
        //model进行数据获取
        if(findMcsModel != null) {
            findMcsModel.findMcs(context, new IFindMcsModel.FindMcsListener() {
                @Override
                public void complete(List<Mc> mcs) {
                    if(findMcsVIew != null){
                        findMcsVIew.showMcs(mcs);
                    }
                }
            });
        }
    }
}
