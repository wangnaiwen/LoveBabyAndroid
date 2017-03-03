package com.wnw.lovebaby.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.view.activity.SortListActivity;

/**
 * Created by wnw on 2016/12/20.
 */

public class SortListAdapter extends BaseAdapter {
    private Context mContext;
    private String[] sortTitles;
    public static int mPosition;


    public SortListAdapter(Context context, String[] titles){
        this.mContext = context;
        this.sortTitles = titles;
    }

    @Override
    public int getCount() {
        return sortTitles.length;
    }

    @Override
    public Object getItem(int i) {
        return sortTitles[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SortListLvHolder sortListLvHolder = null;

        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.sort_list_lv_item, viewGroup,false);
            sortListLvHolder = new SortListLvHolder();
            sortListLvHolder.lvTitle = (TextView)view.findViewById(R.id.sort_list_lv_title);

            view.setTag(sortListLvHolder);
        }else {
            sortListLvHolder = (SortListLvHolder)view.getTag();
        }
        mPosition = i;
        sortListLvHolder.lvTitle.setText(sortTitles[i]);
        if(i == SortListActivity.mPosition){
            sortListLvHolder.lvTitle.setBackgroundColor(Color.parseColor("#b1f1efef"));
            sortListLvHolder.lvTitle.setTextColor(Color.parseColor("#fd4a7d"));
        }else{
            sortListLvHolder.lvTitle.setBackgroundColor(Color.WHITE);
            sortListLvHolder.lvTitle.setTextColor(Color.parseColor("#6b6868"));
        }
        return view;
    }

    private class SortListLvHolder{
        TextView lvTitle;
    }
}
