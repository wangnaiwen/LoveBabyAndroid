package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.ReceAddress;

/**
 * Created by wnw on 2017/3/10.
 */

public interface IUpdateReceAddressModel {
    /**
     * update data
     * */
    void updateReceAddress(Context context, ReceAddress receAddress, IUpdateReceAddressModel.ReceAddressUpdateListener receAddressUpdateListener);

    /**
     * completed
     * */
    interface ReceAddressUpdateListener{
        void complete(boolean isSuccess);
    }
}
