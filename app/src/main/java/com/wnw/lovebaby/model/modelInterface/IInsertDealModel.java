package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Deal;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IInsertDealModel {
    /**
     * 加载数据
     * */
    void insertDeal(Context context, Deal deal, DealInsertListener dealInsertListener);

    /**
     * 加载数据完成的回调
     * */
    interface DealInsertListener{
        void complete(boolean isSuccess);
    }
}
