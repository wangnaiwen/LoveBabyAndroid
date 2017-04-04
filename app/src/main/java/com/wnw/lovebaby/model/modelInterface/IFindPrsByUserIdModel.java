package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Pr;

import java.util.List;

/**
 * Created by wnw on 2017/4/4.
 */

public interface IFindPrsByUserIdModel {
    /**
     * insert data
     * */
    void FindPrByUserId(Context context,int userId,int number, PrFindByUserIdListener prFindByUserIdListener);

    /**
     * completed
     * */
    interface PrFindByUserIdListener{
        void complete(List<Pr> prList);
    }
}
