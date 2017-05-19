package com.wnw.lovebaby.view.viewInterface;

/**
 * Created by wnw on 2017/5/19.
 */

public interface IFindProductCountByIdView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showProductCount(int count);

}
