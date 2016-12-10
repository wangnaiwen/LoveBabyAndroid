package com.wnw.lovebaby.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.bean.ShoppingCarItem;
import com.wnw.lovebaby.util.TypeConverters;

import java.util.List;

/**
 * Created by wnw on 2016/12/10.
 */

public class ShoppingCarAdapter extends BaseAdapter {

    public final static int CHECKED = 0;
    public final static int GOODS_IMG = 1;
    public final static int GOODS_TITLE = 2;
    public final static int GOODS_PLUS = 3;
    public final static int GOODS_SUB = 4;

    private List<ShoppingCarItem> shoppingCarItemList;
    private Context context;
    private Handler handler;


    public ShoppingCarAdapter(Context context,Handler handler, List<ShoppingCarItem> shoppingCarItems){
        this.context = context;
        this.handler = handler;
        this.shoppingCarItemList = shoppingCarItems;
    }

    public void setShoppingCarItemList(List<ShoppingCarItem> shoppingCarItems){
        this.shoppingCarItemList = shoppingCarItems;
    }

    @Override
    public int getCount() {
        return shoppingCarItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return shoppingCarItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ShoppingCarHolder shoppingCarHolder = null;
        if(view == null){
            shoppingCarHolder = new ShoppingCarHolder();
            view = LayoutInflater.from(context).inflate(R.layout.shopping_car_item, null);
            shoppingCarHolder.goodsChecked = (ImageView)view.findViewById(R.id.spc_goods_checked);
            shoppingCarHolder.goodsIcon = (ImageView)view.findViewById(R.id.spc_goods_cover_img);
            shoppingCarHolder.goodsTitle = (TextView)view.findViewById(R.id.spc_goods_title);
            shoppingCarHolder.goodsNum = (TextView)view.findViewById(R.id.spc_goods_num);
            shoppingCarHolder.goodsPrice = (TextView)view.findViewById(R.id.spc_goods_sum_price);
            shoppingCarHolder.goodsPlus = (ImageView)view.findViewById(R.id.spc_plus_one);
            shoppingCarHolder.goodsSub = (ImageView)view.findViewById(R.id.spc_sub_one);
            view.setTag(shoppingCarHolder);
        }else {
            shoppingCarHolder = (ShoppingCarHolder)view.getTag();
        }

        final ShoppingCarItem shoppingCarItem = shoppingCarItemList.get(i);
        final int index = i;
        if(shoppingCarItem.isChecked()){
            shoppingCarHolder.goodsChecked.setImageResource(R.drawable.checkbox_pressed);
        }else{
            shoppingCarHolder.goodsChecked.setImageResource(R.drawable.checkbox_normal);
        }
        shoppingCarHolder.goodsIcon.setImageResource(shoppingCarItem.getGoodsImg());
        shoppingCarHolder.goodsTitle.setText(shoppingCarItem.getGoodsTitle());
        TypeConverters converters = new TypeConverters();
        shoppingCarHolder.goodsPrice.setText(converters.IntConvertToString(shoppingCarItem.getGoodsPrice()));
        shoppingCarHolder.goodsNum.setText(shoppingCarItem.getGoodsNum()+"");

        shoppingCarHolder.goodsChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = CHECKED;
                        message.arg1 = index;
                        message.arg2 = shoppingCarItem.getId();
                        handler.sendMessage(message);                    }
                }).start();
            }
        });

        shoppingCarHolder.goodsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = GOODS_IMG;
                        message.arg1 = index;
                        message.arg2 = shoppingCarItem.getId();
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });

        shoppingCarHolder.goodsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = GOODS_TITLE;
                        message.arg1 = index;
                        message.arg2 = shoppingCarItem.getId();
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });

        shoppingCarHolder.goodsPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = GOODS_PLUS;
                        message.arg1 = index;
                        message.arg2 = shoppingCarItem.getId();
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });

        shoppingCarHolder.goodsSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = GOODS_SUB;
                        message.arg1 = index;
                        message.arg2 = shoppingCarItem.getId();
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
        return view;
    }

    private class ShoppingCarHolder{
        ImageView goodsChecked;
        ImageView goodsIcon;
        TextView goodsTitle;
        TextView goodsPrice;
        TextView goodsNum;
        ImageView goodsPlus;
        ImageView goodsSub;
    }
}
