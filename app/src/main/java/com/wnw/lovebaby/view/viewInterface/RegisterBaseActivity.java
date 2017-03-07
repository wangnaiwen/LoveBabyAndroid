package com.wnw.lovebaby.view.viewInterface;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wnw.lovebaby.presenter.RegisterBasePresenter;


/**
 * Created by wnw on 2016/10/17.
 */

public abstract class RegisterBaseActivity<V,T extends RegisterBasePresenter<V>> extends Activity {
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
