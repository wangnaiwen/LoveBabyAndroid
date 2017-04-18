package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.ReceAddress;
import com.wnw.lovebaby.model.modelImpl.FindReceAddressByIdImpl;
import com.wnw.lovebaby.model.modelInterface.IFindReceAddressByIdModel;
import com.wnw.lovebaby.view.viewInterface.IFindReceAddressByIdView;


/**
 * Created by wnw on 2017/4/18.
 */

public class FindReceAddressByIdPresenter {

    private Context context;
    private IFindReceAddressByIdView findReceAddressByIdView;
    private IFindReceAddressByIdModel findReceAddressByIdModel = new FindReceAddressByIdImpl();

    public FindReceAddressByIdPresenter(Context context,IFindReceAddressByIdView findReceAddressByIdView){
        this.context = context;
        this.findReceAddressByIdView = findReceAddressByIdView;
    }

    public void setFindReceAddressByIdView(IFindReceAddressByIdView findReceAddressByIdView){
        this.findReceAddressByIdView = findReceAddressByIdView;
    }
    //加载数据
    public void findReceAddresById(int id) {
        //加载进度条
        findReceAddressByIdView.showDialog();
        //model进行数据获取
        if(findReceAddressByIdModel != null){
            findReceAddressByIdModel.findReceAddressById(context, id, new IFindReceAddressByIdModel.ReceAddressFindByIdListener() {
                @Override
                public void complete(ReceAddress address) {
                    if(findReceAddressByIdView != null){
                        findReceAddressByIdView.showReceAddressFindById(address);
                    }
                }
            });
        }
    }

}
