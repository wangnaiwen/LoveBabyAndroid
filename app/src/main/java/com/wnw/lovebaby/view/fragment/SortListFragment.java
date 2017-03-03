package com.wnw.lovebaby.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.SecondLevelAdapter;
import com.wnw.lovebaby.bean.SecondLevelItem;
import com.wnw.lovebaby.view.costom.GoodsGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2016/12/20.
 */

public class SortListFragment extends Fragment {
    public static final String TAG = "MyFragment";
    private String str;

    private View mView;
    private LayoutInflater mInflater;

    private GridView secondLevelView;
    private SecondLevelAdapter secondLevelAdapter;
    private List<SecondLevelItem> secondLevelItemList;

    private String[] titles = {"贝贝奶粉","北北奶粉", "北鼻奶粉","蓓蓓奶粉", "北鼻奶粉","蓓蓓奶粉"};
    private int[] titleImg = {R.mipmap.b1, R.mipmap.b2, R.mipmap.b3, R.mipmap.b4, R.mipmap.b5, R.mipmap.b5};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sort_list, container, false);
        //TextView textView = (TextView) view.findViewById(R.id.fag_text);
        //得到数据
        this.mInflater = inflater;

        str = getArguments().getString(TAG);
        initView();
        //textView.setText(str);
        return mView;
    }

    private void initView(){
        secondLevelView = (GridView)mView.findViewById(R.id.gv_second_level);
        secondLevelItemList = new ArrayList<>();
        int length = titleImg.length;

        for(int i = 0; i < length; i++){
            SecondLevelItem secondLevelItem = new SecondLevelItem();
            secondLevelItem.setTitle(titles[i]);
            secondLevelItem.setTitleImg(titleImg[i]);
            secondLevelItemList.add(secondLevelItem);
        }
        secondLevelAdapter = new SecondLevelAdapter(mInflater.getContext(), secondLevelItemList);
        secondLevelView.setAdapter(secondLevelAdapter);
    }
}
