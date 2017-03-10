package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.ReceAddress;

import java.util.List;

/**
 * Created by wnw on 2017/3/10.
 */

public interface IFindReceAddressModel {
    /**
     * find data
     * */
    void findReceAddress(Context context, int userId, IFindReceAddressModel.ReceAddressFindListener receAddressFindListener);

    /**
     * completed
     * */
    interface ReceAddressFindListener{
        void complete(List<ReceAddress> receAddressList);
    }
}
