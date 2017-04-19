package com.wnw.lovebaby.view.viewInterface;
import com.wnw.lovebaby.domain.Pr;

import java.util.List;

/**
 * Created by wnw on 2017/4/18.
 */

public interface IFindPrByDealIdView {
    /**
     * 显示进度条
     * */
    void showDialog();

    /**
     * 返回数据
     * */
    void showPrs(List<Pr> prs);
}
