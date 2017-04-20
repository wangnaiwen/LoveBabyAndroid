package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.Sc;

import java.util.List;

/**
 * Created by wnw on 2017/4/20.
 */

public interface IFindScByMcIdView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showScs(List<Sc> scs);
}
