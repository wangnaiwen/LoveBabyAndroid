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

import com.bumptech.glide.Glide;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.bean.ShoppingCarItem;
import com.wnw.lovebaby.util.TypeConverters;

import java.util.List;

/**
 * Created by wnw on 2017/4/18.
 */

public class InsertPrAdapter extends BaseAdapter {

    private Context context;
    private Handler handler;
    private List<ShoppingCarItem> shoppingCarItemList;

    public InsertPrAdapter(Context context, Handler handler ,List<ShoppingCarItem> shoppingCarItemList){
        this.context = context;
        this.handler = handler;
        this.shoppingCarItemList = shoppingCarItemList;
    }

    public void setShoppingCarItemList(List<ShoppingCarItem> shoppingCarItemList){
        this.shoppingCarItemList = shoppingCarItemList;
    }

    @Override
    public int getCount() {
        return shoppingCarItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return shoppingCarItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        InsertPrHolder insertPrHolder = null;
        if(convertView == null) {
            insertPrHolder = new InsertPrHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_insert_pr, null);
            insertPrHolder.coverImg = (ImageView)convertView.findViewById(R.id.pr_order_cover);
            insertPrHolder.nameTv = (TextView)convertView.findViewById(R.id.pr_order_title);
            insertPrHolder.priceTv = (TextView)convertView.findViewById(R.id.pr_order_unit_price);
            insertPrHolder.numberTv = (TextView)convertView.findViewById(R.id.pr_goods_num);
            insertPrHolder.evaluationTv = (TextView)convertView.findViewById(R.id.pr_insert_pr);
            convertView.setTag(insertPrHolder);
        }else{
            insertPrHolder = (InsertPrHolder)convertView.getTag();
        }
        insertPrHolder.nameTv.setText(shoppingCarItemList.get(position).getGoodsTitle());
        insertPrHolder.numberTv.setText(shoppingCarItemList.get(position).getGoodsNum()+"");
        insertPrHolder.priceTv.setText(TypeConverters.IntConvertToString(shoppingCarItemList.get(position).getGoodsPrice()));
        Glide.with(context).load(shoppingCarItemList.get(position).getGoodsImg()).into(insertPrHolder.coverImg);

        insertPrHolder.evaluationTv.setOnClickListener(new View.OnClickListener() {
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
        return convertView;
    }

    private class InsertPrHolder{
        ImageView coverImg;
        TextView nameTv;
        TextView priceTv;
        TextView numberTv;
        TextView evaluationTv;
    }
}
