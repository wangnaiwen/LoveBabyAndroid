package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Sc;

import java.util.List;

/**
 * Created by wnw on 2017/4/20.
 */

public interface IFindScByMcIdModel {
    /**
     * find data
     * */
    void findScByMcId(Context context, int mcId, FindScByMcIdListener findScByMcIdListener);

    /**
     * completed
     * */
    interface FindScByMcIdListener{
        void complete(List<Sc> scs);
    }
}
