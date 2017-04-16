package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Shop;

import java.util.List;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IFindShopByInviteeModel {
    /**
     * 加载数据
     * */
    void findShopByInvitee(Context context, int invitee, ShopFindByInviteeListener shopFindByInviteeListener);

    /**
     * 加载数据完成的回调
     * */
    interface ShopFindByInviteeListener{
        void complete(List<Shop> shops);
    }
}
