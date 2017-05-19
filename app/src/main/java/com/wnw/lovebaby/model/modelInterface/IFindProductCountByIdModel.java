package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;


/**
 * Created by wnw on 2017/5/19.
 */

public interface IFindProductCountByIdModel {
    void findProductCountById(Context context, int id, FindProductCountByIdListener findProductCountByIdListener);

    interface FindProductCountByIdListener{
        void complete(int count);
    }
}
