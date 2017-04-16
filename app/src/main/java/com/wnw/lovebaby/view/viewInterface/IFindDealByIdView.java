package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.Deal;

/**
 * Created by wnw on 2017/4/11.
 */

public interface IFindDealByIdView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showDealById(Deal deal);

}
