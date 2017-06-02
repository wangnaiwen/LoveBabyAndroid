package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.Product;
import com.wnw.lovebaby.model.modelImpl.RecommendProductImp;
import com.wnw.lovebaby.model.modelInterface.IRecommendProduct;
import com.wnw.lovebaby.view.viewInterface.IRecommendProductView;

import java.util.List;

/**
 * Created by wnw on 2017/6/2.
 */

public class RecommendProductPresenter {
    private Context context;
    //view
    private IRecommendProductView recommendProductView;
    //model
    private IRecommendProduct recommendProduct = new RecommendProductImp();
    //ͨ通过构造函数传入view

    public RecommendProductPresenter(Context context,IRecommendProductView recommendProductView) {
        this.context = context;
        this.recommendProductView = recommendProductView;
    }

    //将最新的View传递过来，在Destroy被销毁时，将setView(null)
    public void setView(IRecommendProductView recommendProductView){
        this.recommendProductView = recommendProductView;
    }

    //加载数据
    public void findRecommendProducr(int userId) {
        //加载进度条
        recommendProductView.showDialog();
        //model进行数据获取
        if(recommendProduct != null){
            recommendProduct.findRecommendProduct(context, userId, new IRecommendProduct.RecommendProductListener() {
                @Override
                public void complete(List<Product> products) {
                    if (recommendProductView != null){
                        recommendProductView.showRecommendProducts(products);
                    }
                }
            });
        }
    }
}
