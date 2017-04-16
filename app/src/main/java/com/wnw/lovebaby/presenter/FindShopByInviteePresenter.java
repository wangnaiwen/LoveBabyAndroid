package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Shop;
import com.wnw.lovebaby.model.modelImpl.FindShopByInviteeModelImpl;
import com.wnw.lovebaby.model.modelInterface.IFindShopByInviteeModel;
import com.wnw.lovebaby.view.viewInterface.IFindShopByInviteeView;

import java.util.List;

/**
 * Created by wnw on 2017/4/11.
 */

public class FindShopByInviteePresenter {
    private Context context;
    //view
    private IFindShopByInviteeView findShopByInviteeView;
    //model
    private IFindShopByInviteeModel findShopByInviteeModel = new FindShopByInviteeModelImpl();
    //ͨ通过构造函数传入view

    public FindShopByInviteePresenter(Context context, IFindShopByInviteeView findShopByInviteeView) {
        this.context = context;
        this.findShopByInviteeView = findShopByInviteeView;
    }

    public void setFindShopByInviteeView(IFindShopByInviteeView findShopByInviteeView){
        this.findShopByInviteeView = findShopByInviteeView;
    }

    //加载数据
    public void findShopByInvitee(int invitee) {
        //加载进度条
        findShopByInviteeView.showDialog();
        //model进行数据获取
        if(findShopByInviteeModel != null) {
            findShopByInviteeModel.findShopByInvitee(context, invitee, new IFindShopByInviteeModel.ShopFindByInviteeListener() {
                @Override
                public void complete(List<Shop> shops) {
                    findShopByInviteeView.showShopsByInvitee(shops);
                }
            });
        }
    }
}
