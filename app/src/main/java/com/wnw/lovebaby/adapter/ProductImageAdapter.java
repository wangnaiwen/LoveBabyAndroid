package com.wnw.lovebaby.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.domain.ProductImage;

import java.util.List;

/**
 * Created by wnw on 2017/5/25.
 */

public class ProductImageAdapter extends BaseAdapter {

    private Context context;
    private List<ProductImage> imageList;

    public ProductImageAdapter(Context context, List<ProductImage> images) {
        this.context = context;
        this.imageList = images;
    }

    public void setImageList(List<ProductImage> images){
        this.imageList = images;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int i) {
        return imageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageHolder imageHolder;
        if(view == null){
            imageHolder = new ImageHolder();
            view = LayoutInflater.from(context).inflate(R.layout.lv_item_image, null);
            imageHolder.imageView = (ImageView) view.findViewById(R.id.img);
            view.setTag(imageHolder);
        }else {
            imageHolder = (ImageHolder)view.getTag();
        }
        Log.d("www", imageList.size()+"");
        Glide.with(context).load(imageList.get(i).getPath()).into(imageHolder.imageView);
        return view;
    }

    private class ImageHolder{
        ImageView imageView;
    }

}
