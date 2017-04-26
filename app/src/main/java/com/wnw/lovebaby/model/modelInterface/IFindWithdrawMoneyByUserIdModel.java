package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

/**
 * Created by wnw on 2017/4/26.
 */

public interface IFindWithdrawMoneyByUserIdModel {
    /**
     * 加载数据
     * */
    void findWithdrawMoneyByUserId(Context context, int userId, FindWithdrawMoneyByUserIdListener findWithdrawMoneyByUserIdListener);

    /**
     * 加载数据完成的回调
     * */
    interface FindWithdrawMoneyByUserIdListener{
        void complete(int money);
    }
}
