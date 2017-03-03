package com.wnw.lovebaby.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.bean.SecondLevelItem;
import com.wnw.lovebaby.view.costom.GoodsSquareLayout;

import java.util.List;

/**
 * Created by wnw on 2016/12/21.
 */

public class SecondLevelAdapter extends BaseAdapter {

    private Context context;
    private List<SecondLevelItem> secondLevelItemList;

    public SecondLevelAdapter(Context context, List<SecondLevelItem> secondLevelItems){
        this.context = context;
        this.secondLevelItemList = secondLevelItems;
    }

    public void setSecondLevelItemList(List<SecondLevelItem> secondLevelItemList){
        this.secondLevelItemList = secondLevelItemList;
    }

    @Override
    public int getCount() {
        return secondLevelItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return secondLevelItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SecondLevelHolder secondLevelHolder = null;
        if(view == null){
            secondLevelHolder = new SecondLevelHolder();
            view = LayoutInflater.from(context).inflate(R.layout.gv_second_level_item, viewGroup, false);
            secondLevelHolder.squareLayout = (GoodsSquareLayout)view.findViewById(R.id.second_level_square_layout);
            secondLevelHolder.title = (TextView)view.findViewById(R.id.second_level_cover_title);
            secondLevelHolder.titleImg = (ImageView) view.findViewById(R.id.second_level_cover_icon);
            view.setTag(secondLevelHolder);
        }else {
            secondLevelHolder = (SecondLevelHolder)view.getTag();
        }
        secondLevelHolder.titleImg.setImageResource(secondLevelItemList.get(i).getTitleImg());
        secondLevelHolder.title.setText(secondLevelItemList.get(i).getTitle());

        return view;
    }

    private class SecondLevelHolder{
        GoodsSquareLayout squareLayout;
        ImageView titleImg;
        TextView title;
    }
}
