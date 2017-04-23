package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

/**
 * Created by wnw on 2017/4/22.
 */

public interface IFindIncomeByInviteeModel {
    /**
     * find data
     * */
    void findIncomeByShopId(Context context, int invitee, FindIncomeByInviteeListener findIncomeByInviteeListener);

    /**
     * completed
     * */
    interface FindIncomeByInviteeListener {
        void complete(int income);
    }
}
