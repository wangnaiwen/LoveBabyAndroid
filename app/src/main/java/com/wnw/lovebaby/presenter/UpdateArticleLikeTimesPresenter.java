package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.model.modelImpl.UpdateArticleLikeTimes;
import com.wnw.lovebaby.model.modelInterface.IUpdateArticleLikeTimes;
import com.wnw.lovebaby.view.viewInterface.IUpdateArticleLikeTimesView;

/**
 * Created by wnw on 2017/5/12.
 */

public class UpdateArticleLikeTimesPresenter {
    private Context context;
    //view
    private IUpdateArticleLikeTimesView updateArticleLikeTimesView;
    //model
    private IUpdateArticleLikeTimes updateArticleLikeTimes = new UpdateArticleLikeTimes();
    //ͨ通过构造函数传入view

    public UpdateArticleLikeTimesPresenter(Context context, IUpdateArticleLikeTimesView updateArticleLikeTimesView) {
        super();
        this.context = context;
        this.updateArticleLikeTimesView = updateArticleLikeTimesView;
    }

    public void setView(IUpdateArticleLikeTimesView updateArticleLikeTimesView){
        this.updateArticleLikeTimesView = updateArticleLikeTimesView;
    }

    //加载数据
    public void updateLikeTimes(int userId, int articleId) {
        //加载进度条
        updateArticleLikeTimesView.showDialog();
        //model进行数据获取
        if (updateArticleLikeTimes != null){
            updateArticleLikeTimes.updateArticle(context, userId,articleId, new IUpdateArticleLikeTimes.ArticleUpdateLikeTimesListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (updateArticleLikeTimesView != null){
                        updateArticleLikeTimesView.showUpdateLikeTimesResult(isSuccess);
                    }
                }
            });
        }
    }
}
