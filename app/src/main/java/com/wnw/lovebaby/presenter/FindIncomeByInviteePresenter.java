package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.model.modelImpl.FindIncomeByInviteeImpl;
import com.wnw.lovebaby.model.modelInterface.IFindIncomeByInviteeModel;
import com.wnw.lovebaby.view.viewInterface.IFindIncomeByInviteeView;


/**
 * Created by wnw on 2017/4/22.
 */

public class FindIncomeByInviteePresenter {
    private Context context;
    //view
    private IFindIncomeByInviteeView findIncomeByInviteeView;
    //model
    private IFindIncomeByInviteeModel findIncomeByInviteeModel = new FindIncomeByInviteeImpl();
    //ͨ通过构造函数传入view

    public FindIncomeByInviteePresenter(Context context,IFindIncomeByInviteeView findIncomeByInviteeView) {
        super();
        this.context = context;
        this.findIncomeByInviteeView = findIncomeByInviteeView;
    }

    public void setView(IFindIncomeByInviteeView findIncomeByInviteeView){
        this.findIncomeByInviteeView = findIncomeByInviteeView;
    }

    //加载数据
    public void load(int invitee) {
        //加载进度条
        findIncomeByInviteeView.showDialog();
        //model进行数据获取
        findIncomeByInviteeModel.findIncomeByShopId(context, invitee, new IFindIncomeByInviteeModel.FindIncomeByInviteeListener() {
            @Override
            public void complete(int income) {
                if(findIncomeByInviteeView != null){
                    findIncomeByInviteeView.showIncomeByInvitee(income);
                }
            }
        });
    }
}
