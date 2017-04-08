package com.wnw.lovebaby.view.viewInterface;

import com.wnw.lovebaby.domain.Pr;

import java.util.List;

/**
 * Created by wnw on 2017/4/5.
 */

public interface IFindPrsByProductIdView {
    void showDialog();
    void showPrs(List<Pr> prList);
}
