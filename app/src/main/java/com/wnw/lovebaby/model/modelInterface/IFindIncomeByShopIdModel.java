package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;


/**
 * Created by wnw on 2017/4/22.
 */

public interface IFindIncomeByShopIdModel {
    /**
     * find data
     * */
    void findIncomeByShopId(Context context,int shopId, FindIncomeByShopIdListener findIncomeByShopIdListener);

    /**
     * completed
     * */
    interface FindIncomeByShopIdListener {
        void complete(int income);
    }
}
