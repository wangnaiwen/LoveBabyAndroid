package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Deal;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IUpdateDealModel {
    /**
     * 加载数据
     * */
    void updateDeal(Context context, Deal deal,DealUpdateListener dealUpdateListener);

    /**
     * 加载数据完成的回调
     * */
    interface DealUpdateListener{
        void complete(boolean isSuccess);
    }
}
