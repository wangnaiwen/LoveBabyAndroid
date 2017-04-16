package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

/**
 * Created by wnw on 2017/4/16.
 */

public interface IValiteWalletModel {
    /**
     * 加载数据
     * */
    void valiteWallet(Context context, int userId, int password, WalletValiteListener walletValiteListener);

    /**
     * 加载数据完成的回调
     * */
    interface WalletValiteListener{
        void complete(boolean isSuccess);
    }
}
