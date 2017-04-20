package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Mc;

import java.util.List;

/**
 * Created by wnw on 2017/4/20.
 */

public interface IFindMcsModel {
    /**
     * find data
     * */
    void findMcs(Context context, FindMcsListener findMcsListener );

    /**
     * completed
     * */
    interface FindMcsListener{
        void complete(List<Mc> mcs);
    }
}
