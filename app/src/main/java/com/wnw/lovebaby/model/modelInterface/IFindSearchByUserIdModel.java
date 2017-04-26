package com.wnw.lovebaby.model.modelInterface;

import android.content.Context;

import com.wnw.lovebaby.domain.Search;

import java.util.List;

/**
 * Created by wnw on 2017/4/26.
 */

public interface IFindSearchByUserIdModel {
    /**
     * find data
     * */
    void findSearchByUserId(Context context, int userId,FindSearchByUserIdListener findSearchByUserIdListener);

    /**
     * completed
     * */
    interface FindSearchByUserIdListener {
        void complete(List<Search> searches);
    }
}
