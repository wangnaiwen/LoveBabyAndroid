package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

/**
 * Created by wnw on 2017/3/9.
 */

public interface IDeleteReceAddressModel {
    /**
     * delete data
     * */
    void deleteReceAddress(Context context, int id, IDeleteReceAddressModel.ReceAddressDeleteListener receAddressDeleteListener);

    /**
     * completed
     * */
    interface ReceAddressDeleteListener{
        void complete(boolean isSuccess);
    }
}
