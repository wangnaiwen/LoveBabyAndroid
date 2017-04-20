package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Mc;


/**
 * Created by wnw on 2017/4/20.
 */

public interface IFindMcByIdModel {
    /**
     * find data
     * */
    void findMcById(Context context,int id, FindMcByIdListener findMcByIdListener );

    /**
     * completed
     * */
    interface FindMcByIdListener{
        void complete(Mc mc);
    }
}
