package com.wnw.lovebaby.login;

import android.app.Activity;
import android.os.Bundle;

import com.wnw.lovebaby.presenter.FindPasswordBasePresenter;

/**
 * Created by wnw on 2017/3/7.
 */

public abstract class FindPasswordBaseActivity<V,T extends FindPasswordBasePresenter<V>> extends Activity {
    protected T mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //创建presenter
        mPresenter = createPresenter();
        //内存泄露
        //关联View
        mPresenter.attachView((V) this);
    }

    protected abstract  T createPresenter();

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mPresenter.detachView();
    }
}
