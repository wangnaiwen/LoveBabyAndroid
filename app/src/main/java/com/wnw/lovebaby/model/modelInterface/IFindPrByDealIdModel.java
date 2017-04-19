package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Pr;

import java.util.List;

/**
 * Created by wnw on 2017/4/18.
 */

public interface IFindPrByDealIdModel {
    /**
     * find data
     * */
    void FindPrByDealId(Context context, int dealId, PrFindBydealIdListener prFindBydealIdListener);

    /**
     * completed
     * */
    interface PrFindBydealIdListener{
        void complete(List<Pr> prList);
    }
}
