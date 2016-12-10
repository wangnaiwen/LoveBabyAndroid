package com.wnw.lovebaby.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.ShoppingCarAdapter;
import com.wnw.lovebaby.bean.ShoppingCarItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2016/12/1.
 */

public class ShoppingCarFragment extends Fragment {
    private View view;
    private Context context;
    private LayoutInflater inflater;

    private ListView shoppingCarListView;
    private ShoppingCarAdapter shoppingCarAdapter;
    private List<ShoppingCarItem> shoppingCarItemList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shopping_car, container, false);
        this.context = inflater.getContext();
        this.inflater = inflater;
        initView();
        return view;
    }

    private void initView(){
        shoppingCarListView = (ListView)view.findViewById(R.id.lv_shopping_car);
        setShoppingCar();
        shoppingCarAdapter = new ShoppingCarAdapter(context, handler,shoppingCarItemList);
        shoppingCarListView.setAdapter(shoppingCarAdapter);
        shoppingCarListView.setDivider(null);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int index = msg.arg1;
            int id = msg.arg2;
            switch (msg.what){
                case ShoppingCarAdapter.CHECKED:
                    if(shoppingCarItemList.get(index).isChecked()){
                        shoppingCarItemList.get(index).setChecked(false);
                    }else {
                        shoppingCarItemList.get(index).setChecked(true);
                    }
                    reSetShoppingCarAdapter();
                    break;
                case ShoppingCarAdapter.GOODS_IMG:
                    Log.d("wnw", "You click the goods img"+index+id);
                    break;
                case ShoppingCarAdapter.GOODS_TITLE:
                    Log.d("wnw", "You click the goods title"+index+id);
                    break;
                case ShoppingCarAdapter.GOODS_PLUS:
                    int goodsCount = shoppingCarItemList.get(index).getGoodsNum();
                    shoppingCarItemList.get(index).setGoodsNum(goodsCount+1);
                    reSetShoppingCarAdapter();
                    break;
                case ShoppingCarAdapter.GOODS_SUB:
                    int goodsNum = shoppingCarItemList.get(index).getGoodsNum();
                    if(goodsNum == 1){
                        shoppingCarItemList.remove(index);
                    }else{
                        shoppingCarItemList.get(index).setGoodsNum(goodsNum-1);
                    }
                    reSetShoppingCarAdapter();
                    break;
                default:
                    break;
            }
        }
    };

    private void reSetShoppingCarAdapter(){
        shoppingCarAdapter.setShoppingCarItemList(shoppingCarItemList);
        shoppingCarAdapter.notifyDataSetChanged();
    }

    private void setShoppingCar(){
        shoppingCarItemList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            ShoppingCarItem shoppingCarItem = new ShoppingCarItem();
            shoppingCarItem.setId(i);
            shoppingCarItem.setChecked(false);
            shoppingCarItem.setGoodsImg(R.mipmap.b1);
            shoppingCarItem.setGoodsNum(1);
            shoppingCarItem.setGoodsPrice(400000);
            shoppingCarItem.setGoodsTitle("特价宝宝，冬天免费出租，免费暖被窝，免费到洗脚水");
            shoppingCarItemList.add(shoppingCarItem);
        }
    }
}
