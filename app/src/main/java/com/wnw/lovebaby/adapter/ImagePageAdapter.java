package com.wnw.lovebaby.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.Toast;

import com.wnw.lovebaby.view.activity.ProductDetailActivity;

import java.util.List;

/**
 * Created by wnw on 2016/12/6.
 */

public class ImagePageAdapter extends PagerAdapter{

    private List<ImageView> imageViewList;
    private Context context;
    private Handler handler;

    public ImagePageAdapter(Context context,Handler handler, List<ImageView> list){
        this.context = context;
        this.handler = handler;
        this.imageViewList = list;
    }

    public void setImageViewList(List<ImageView> imageViews){
        this.imageViewList = imageViews;
    }
    @Override
    public int getCount() {
        return imageViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        //Warning：不要在这里调用removeView
    }

    /**
     * 在这里实现图片的单击事件
     * */
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        ImageView view = imageViewList.get(position) ;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = 1;
                        message.arg1 = position;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
        ViewParent vp =  view.getParent();
        if(vp != null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }
        //上面这些语句必须加上，如果不加的话，就会产生则当用户滑到第四个的时候就会触发这个异常
        //原因是我们试图把一个有父组件的View添加到另一个组件。
        ((ViewPager)container).addView(imageViewList.get(position));
        return imageViewList.get(position);
    }
}
