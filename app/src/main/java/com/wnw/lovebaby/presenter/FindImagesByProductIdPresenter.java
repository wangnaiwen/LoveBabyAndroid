package com.wnw.lovebaby.presenter;

import android.content.Context;

import com.wnw.lovebaby.domain.ProductImage;
import com.wnw.lovebaby.model.modelImpl.FindImagesByProductIdImp;
import com.wnw.lovebaby.model.modelInterface.IFindProductImagesByProductId;
import com.wnw.lovebaby.view.viewInterface.IFindImagesByProductIdView;

import java.util.List;

/**
 * Created by wnw on 2017/5/25.
 */

public class FindImagesByProductIdPresenter {
    private Context context;
    //view
    private IFindImagesByProductIdView findImagesByProductIdView;
    //model
    private IFindProductImagesByProductId findProductImagesByProductId = new FindImagesByProductIdImp();
    //ͨ通过构造函数传入view

    public FindImagesByProductIdPresenter(Context context, IFindImagesByProductIdView findImagesByProductIdView) {
        super();
        this.context = context;
        this.findImagesByProductIdView = findImagesByProductIdView;
    }

    public void setView(IFindImagesByProductIdView findImagesByProductIdView){
        this.findImagesByProductIdView = findImagesByProductIdView;
    }

    //加载数据
    public void findImagesByProductId(int productId) {
        //加载进度条
        findImagesByProductIdView.showDialog();
        //model进行数据获取
        if(findProductImagesByProductId != null){
            findProductImagesByProductId.findImagesByProductId(context, productId, new IFindProductImagesByProductId.FindImagesByProductIdListener() {
                @Override
                public void complete(List<ProductImage> images) {
                    if (findImagesByProductIdView != null){
                        findImagesByProductIdView.showProductImages(images);
                    }
                }
            });
        }
    }
}
