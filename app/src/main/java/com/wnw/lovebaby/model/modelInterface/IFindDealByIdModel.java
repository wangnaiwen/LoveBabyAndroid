package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Deal;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IFindDealByIdModel {
    /**
     * 加载数据
     * */
    void findDealById(Context context, int id, DealFindByIdListener dealFindByIdListener);

    /**
     * 加载数据完成的回调
     * */
    interface DealFindByIdListener{
        void complete(Deal deal);
    }
}
