package com.wnw.lovebaby.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.ShoppingCarAdapter;
import com.wnw.lovebaby.bean.ShoppingCarItem;
import com.wnw.lovebaby.util.TypeConverters;
import com.wnw.lovebaby.view.activity.OrderConfirmationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2016/12/1.
 */

public class ShoppingCarFragment extends Fragment implements View.OnClickListener{
    private View view;
    private Context context;
    private LayoutInflater inflater;

    private ListView shoppingCarListView;
    private ShoppingCarAdapter shoppingCarAdapter;
    private List<ShoppingCarItem> shoppingCarItemList;

    private LinearLayout linearLayout;        //if shopping car have nothing , hide it
    private RelativeLayout relativeLayout;   // if shopping car have nothing , display it
    private ImageView allChecked;
    private TextView amountsPayable;
    private TextView closeAnAccount;

    private boolean allCheckedState = true;  //当前是否是全部选中
    private int sumPrice = 0;                // 当前选中的全部价格

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
        allChecked = (ImageView)view.findViewById(R.id.all_checked);
        amountsPayable = (TextView)view.findViewById(R.id.amounts_payable);
        closeAnAccount = (TextView)view.findViewById(R.id.btn_close_an_account);
        linearLayout = (LinearLayout)view.findViewById(R.id.btn_sum_price);
        relativeLayout = (RelativeLayout)view.findViewById(R.id.shopping_car_nothing);

        setShoppingCar();
        shoppingCarAdapter = new ShoppingCarAdapter(context, handler,shoppingCarItemList);
        shoppingCarListView.setAdapter(shoppingCarAdapter);
        shoppingCarListView.setDivider(null);
        allChecked.setOnClickListener(this);
        closeAnAccount.setOnClickListener(this);
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
                    updateData();
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
                    updateData();
                    break;
                case ShoppingCarAdapter.GOODS_SUB:
                    int goodsNum = shoppingCarItemList.get(index).getGoodsNum();
                    if(goodsNum == 1){
                        showDeleteAddressDialog(index);
                    }else{
                        shoppingCarItemList.get(index).setGoodsNum(goodsNum-1);
                        updateData();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * reset the shopping car ui
     * */
    private void reSetShoppingCarAdapter(){
        shoppingCarAdapter.setShoppingCarItemList(shoppingCarItemList);
        shoppingCarAdapter.notifyDataSetChanged();
    }

    /**
     * update the data:
     * 1. set all checked button state
     * 2. set sum price
     * 3. reset adapter
     * */
    private void updateData(){
        int num = shoppingCarItemList.size();
        if(num == 0 ){
            linearLayout.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        }else {
            relativeLayout.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            setAllCheckedState();
            reSetShoppingCarAdapter();
            setSumPrice();
        }
    }

    /***
     * set all checked btn state
     */
    private void setAllCheckedState(){
        allCheckedState = true;
        int num = shoppingCarItemList.size();
        for(int i = 0; i < num; i++){
            if(!shoppingCarItemList.get(i).isChecked()){
                allCheckedState = false;
            }
        }

        if(allCheckedState){
            allChecked.setImageResource(R.drawable.checkbox_pressed);
        }else {
            allChecked.setImageResource(R.drawable.checkbox_normal);
        }
    }
    /**
     * set sum price
     * */
    private void setSumPrice(){
        sumPrice = 0;
        int num  = shoppingCarItemList.size();
        for(int i = 0 ;i < num; i ++){
            if(shoppingCarItemList.get(i).isChecked()){
                sumPrice += shoppingCarItemList.get(i).getGoodsPrice() * shoppingCarItemList.get(i).getGoodsNum();
            }
        }
        TypeConverters converters = new TypeConverters();
        amountsPayable.setText(converters.IntConvertToString(sumPrice));
    }

    private void setShoppingCar(){
        shoppingCarItemList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            ShoppingCarItem shoppingCarItem = new ShoppingCarItem();
            shoppingCarItem.setId(i);
            shoppingCarItem.setChecked(true);
            shoppingCarItem.setGoodsImg(R.mipmap.b1);
            shoppingCarItem.setGoodsNum(1);
            shoppingCarItem.setGoodsPrice(400000);
            shoppingCarItem.setGoodsTitle("特价宝宝，冬天免费出租，免费暖被窝，免费倒洗脚水");
            shoppingCarItemList.add(shoppingCarItem);
        }
        setSumPrice();
        setAllCheckedState();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.all_checked:
                if(allCheckedState){
                    int num = shoppingCarItemList.size();
                    for(int i = 0; i < num; i++){
                        if(shoppingCarItemList.get(i).isChecked()){
                            shoppingCarItemList.get(i).setChecked(false);
                        }
                    }
                }else {
                    int num = shoppingCarItemList.size();
                    for(int i = 0; i < num; i++){
                        if(!shoppingCarItemList.get(i).isChecked()){
                            shoppingCarItemList.get(i).setChecked(true);
                        }
                    }
                }
                updateData();
                break;
            case R.id.btn_close_an_account:
                if(sumPrice == 0){
                    Toast.makeText(context, "你没有可支付的账单", Toast.LENGTH_SHORT).show();
                }else{
                    startConfirmationActivity();
                }
                break;
        }
    }

    /***
     * start the order confirmation activity
     * */
    private void startConfirmationActivity(){
        Intent intent = new Intent(context, OrderConfirmationActivity.class);
        startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    /**
     * show the dialog of delete the address
     * */
    private void showDeleteAddressDialog(final int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("删除地址");
        builder.setMessage("是否删除这个收货地址？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("wnw","index=" + index);
                shoppingCarItemList.remove(index);
                updateData();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog deleteDialog = builder.create();
        deleteDialog.show();
    }
}
