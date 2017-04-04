package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Pr;

import java.util.List;

/**
 * Created by wnw on 2017/4/4.
 */

public interface IFindPrsByProductIdModel {
    /**
     * find data
     * */
    void FindPrByProductId(Context context, int productId,int number, PrFindByProductIdListener prFindByProductIdListener);

    /**
     * completed
     * */
    interface PrFindByProductIdListener{
        void complete(List<Pr> prList);
    }
}
