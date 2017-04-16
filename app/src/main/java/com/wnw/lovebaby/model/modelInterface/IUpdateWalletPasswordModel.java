package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

/**
 * Created by wnw on 2017/4/16.
 */

public interface IUpdateWalletPasswordModel {
    /**
     * 加载数据
     * */
    void updateWalletPassword(Context context, int userId, int password, WalletPasswordUpdateListener walletPasswordUpdateListener);

    /**
     * 加载数据完成的回调
     * */
    interface WalletPasswordUpdateListener{
        void complete(boolean isSuccess);
    }
}
