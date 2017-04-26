package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.Search;

import java.util.List;

/**
 * Created by wnw on 2017/4/26.
 */

public interface IFindSearchByUserIdView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showSearchByUserId(List<Search> searches);
}
