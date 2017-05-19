package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

/**
 * Created by wnw on 2017/5/19.
 */

public interface IUpdateProductCountByIdModel {
    void updateProductCountById(Context context, int id, int count, UpdateProductCountByIdListener updateProductCountByIdListener);

    interface UpdateProductCountByIdListener{
        void complete(boolean isSuccess);
    }
}
