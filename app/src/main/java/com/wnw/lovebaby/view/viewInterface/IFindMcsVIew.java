package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.Mc;

import java.util.List;

/**
 * Created by wnw on 2017/4/20.
 */

public interface IFindMcsVIew {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showMcs(List<Mc> mcs) ;
}
