package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

/**
 * Created by wnw on 2017/4/16.
 */

public interface IFindWalletMoneyByUserIdModel {
    /**
     * 加载数据
     * */
    void findWalletMoneyByUserId(Context context, int userId,WalletMoneyFindByUserIdListener walletMoneyFindByUserIdListener);

    /**
     * 加载数据完成的回调
     * */
    interface WalletMoneyFindByUserIdListener{
        void complete(int money);
    }
}
