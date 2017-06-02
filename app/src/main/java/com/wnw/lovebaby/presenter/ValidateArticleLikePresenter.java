package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.model.modelImpl.ValidateArticleLikeImp;
import com.wnw.lovebaby.model.modelInterface.IValidateArticleLike;
import com.wnw.lovebaby.view.viewInterface.IValidateArticleLikeView;

/**
 * Created by wnw on 2017/6/1.
 */

public class ValidateArticleLikePresenter {
    private Context context;
    private IValidateArticleLikeView validateArticleLikeView;
    private IValidateArticleLike validateArticleLike = new ValidateArticleLikeImp();

    public ValidateArticleLikePresenter(Context context,IValidateArticleLikeView validateArticleLikeView){
        this.context = context;
        this.validateArticleLikeView = validateArticleLikeView;
    }

    public void setView(IValidateArticleLikeView validateArticleLikeView){
        this.validateArticleLikeView = validateArticleLikeView;
    }

    //加载数据
    public void validateArticleLike(int userId, int articleId) {
        //加载进度条
        validateArticleLikeView.showDialog();
        //model进行数据获取
        if(validateArticleLike != null) {
            validateArticleLike.validateArticleLike(context, userId, articleId, new IValidateArticleLike.ValidateArticleLikeListener() {
                @Override
                public void complete(boolean isSuccess) {
                    if (validateArticleLikeView != null){
                        validateArticleLikeView.showValidateArticleLikeResult(isSuccess);
                    }
                }
            });
        }
    }

}
