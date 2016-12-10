package com.wnw.lovebaby.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wnw.lovebaby.bean.GoodsCoverItem;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.util.TypeConverters;
import com.wnw.lovebaby.view.costom.GoodsSquareLayout;

import java.util.List;

/**
 * Created by wnw on 2016/12/7.
 */

public class GoodsCoverAdapter extends BaseAdapter {
    private List<GoodsCoverItem> goodsCoverItemList;
    private Context mContext;

    public void setDatas(List<GoodsCoverItem> goodsCoverItems){
        this.goodsCoverItemList = goodsCoverItems;
    }

    public GoodsCoverAdapter(Context context, List<GoodsCoverItem> goodsCoverItems) {
        this.mContext = context;
        this.goodsCoverItemList = goodsCoverItems;
    }

    @Override
    public int getCount() {
        return goodsCoverItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsCoverItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodsCoverHolder goodsCoverHolder = null;
        if(convertView == null) {
            goodsCoverHolder = new GoodsCoverHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gv_goods_item, null);
            goodsCoverHolder.squareLayout = (GoodsSquareLayout) convertView.findViewById(R.id.goods_square_layout);
            goodsCoverHolder.goodsCoverIcon = (ImageView)convertView.findViewById(R.id.goods_cover_icon);
            goodsCoverHolder.titleTextView = (TextView)convertView.findViewById(R.id.goods_cover_title);
            goodsCoverHolder.priceTextView = (TextView)convertView.findViewById(R.id.goods_cover_price);
            convertView.setTag(goodsCoverHolder);
        }else{
            goodsCoverHolder = (GoodsCoverHolder) convertView.getTag();
        }
        goodsCoverHolder.goodsCoverIcon.setImageResource(goodsCoverItemList.get(position).getImage());
        goodsCoverHolder.titleTextView.setText(goodsCoverItemList.get(position).getTitle());

        TypeConverters converters = new TypeConverters();
        goodsCoverHolder.priceTextView.setText(converters.IntConvertToString(goodsCoverItemList.get(position).getPrice()));
        return convertView;
    }

    private class GoodsCoverHolder{
        ImageView goodsCoverIcon;
        TextView titleTextView;
        TextView priceTextView;
        GoodsSquareLayout squareLayout;
    }
}
