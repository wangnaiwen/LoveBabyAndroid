package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.model.modelImpl.UpdateArticleReadTimes;
import com.wnw.lovebaby.model.modelInterface.IUpdateArticleReadTimes;
import com.wnw.lovebaby.view.viewInterface.IUpdateArticleReadTimesView;

/**
 * Created by wnw on 2017/5/12.
 */

public class UpdateArticleReadTimesPresenter {
    private Context context;
    //view
    private IUpdateArticleReadTimesView updateArticleReadTimesView;
    //model
    private IUpdateArticleReadTimes updateArticleReadTimes = new UpdateArticleReadTimes();
    //ͨ通过构造函数传入view

    public UpdateArticleReadTimesPresenter(Context context, IUpdateArticleReadTimesView updateArticleReadTimesView) {
        super();
        this.context = context;
        this.updateArticleReadTimesView = updateArticleReadTimesView;
    }

    public void setView(IUpdateArticleReadTimesView updateArticleReadTimesView){
        this.updateArticleReadTimesView = updateArticleReadTimesView;
    }

    //加载数据
    public void updateReadTimes(int id) {
        //加载进度条
        updateArticleReadTimesView.showDialog();
        //model进行数据获取
        if (updateArticleReadTimes != null){
            updateArticleReadTimes.updateArticle(context, id, new IUpdateArticleReadTimes.ArticleUpdateReadTimesListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (updateArticleReadTimesView != null){
                        updateArticleReadTimesView.showUpdateReadTimesResult(isSuccess);
                    }
                }
            });
        }
    }
}
