package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Withdraw;

/**
 * Created by wnw on 2017/4/26.
 */

public interface IInsertWithdrawModel {

    /**
    * insert data
    * */
    void insertWithdraw(Context context, Withdraw withdraw, InsertWithdrawListener insertWithdrawListener );

    /**
     * completed
     * */
    interface InsertWithdrawListener{
        void complete(boolean isSuccess);
    }
}
