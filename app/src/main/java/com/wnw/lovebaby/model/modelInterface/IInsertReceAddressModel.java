package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.ReceAddress;

/**
 * Created by wnw on 2017/3/9.
 */

public interface IInsertReceAddressModel {
    /**
     * insert data
     * */
    void insertReceAddress(Context context, ReceAddress address, IInsertReceAddressModel.ReceAddressInsertListener receAddressInsertListener);

    /**
     * completed
     * */
    interface ReceAddressInsertListener{
        void complete(boolean isSuccess);
    }
}
