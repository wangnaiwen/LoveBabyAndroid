package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;


/**
 * Created by wnw on 2017/4/16.
 */

public interface ISubWalletMoneyModel {
    /**
     * 加载数据
     * */
    void subWalletMoney(Context context, int userId, int money, WalletMoneySubListener walletMoneySubListener);

    /**
     * 加载数据完成的回调
     * */
    interface WalletMoneySubListener{
        void complete(boolean isSuccess);
    }
}
