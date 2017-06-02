package com.wnw.lovebaby.view.viewInterface;

/**
 * Created by wnw on 2017/6/1.
 */

public interface IValidateArticleLikeView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showValidateArticleLikeResult(boolean isSuccess);
}
