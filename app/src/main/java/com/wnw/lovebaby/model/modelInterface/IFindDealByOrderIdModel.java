package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Deal;

import java.util.List;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IFindDealByOrderIdModel {
    /**
     * 加载数据
     * */
    void findDealByOrderId(Context context, int orderId, DealFindByOrderIdListener dealFindByOrderIdListener);

    /**
     * 加载数据完成的回调
     * */
    interface DealFindByOrderIdListener{
        void complete(List<Deal> deals);
    }
}
