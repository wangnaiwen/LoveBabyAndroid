package com.wnw.lovebaby.view.viewInterface;

/**
 * Created by wnw on 2017/5/12.
 */

public interface IUpdateArticleLikeTimesView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showUpdateLikeTimesResult(boolean isSuccess);
}
