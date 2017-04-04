package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Pr;

/**
 * Created by wnw on 2017/4/4.
 */

public interface IInsertPrModel {
    /**
     * insert data
     * */
    void insertPr(Context context, Pr pr,PrInsertListener prInsertListener);

    /**
     * completed
     * */
    interface PrInsertListener{
        void complete(boolean isSuccess);
    }
}
