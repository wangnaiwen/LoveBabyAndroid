package com.wnw.lovebaby.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.ShoppingCarAdapter;
import com.wnw.lovebaby.bean.ShoppingCarItem;
import com.wnw.lovebaby.domain.ShoppingCar;
import com.wnw.lovebaby.domain.User;
import com.wnw.lovebaby.net.NetBroadcastReceiver;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.DeleteShoppingCarPresenter;
import com.wnw.lovebaby.presenter.FindShoppingCarByUserIdPresenter;
import com.wnw.lovebaby.presenter.UpdaterShoppingCarProductCountPresenter;
import com.wnw.lovebaby.util.LogUtil;
import com.wnw.lovebaby.util.TypeConverters;
import com.wnw.lovebaby.view.activity.MainActivity;
import com.wnw.lovebaby.view.activity.OrderConfirmationActivity;
import com.wnw.lovebaby.view.viewInterface.IDeleteShoppingCarView;
import com.wnw.lovebaby.view.viewInterface.IFindShoppingCarByUserIdView;
import com.wnw.lovebaby.view.viewInterface.IUpdateShoppingCarProductCountView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by wnw on 2016/12/1.
 */

public class ShoppingCarFragment extends Fragment implements View.OnClickListener, IFindShoppingCarByUserIdView,
        IUpdateShoppingCarProductCountView,IDeleteShoppingCarView,SwipeRefreshLayout.OnRefreshListener{
    private View view;
    private Context context;
    private LayoutInflater inflater;
    private SwipeRefreshLayout shoppingCarSrlayout;

    private ListView shoppingCarListView;
    private ShoppingCarAdapter shoppingCarAdapter;
    private List<ShoppingCarItem> shoppingCarItemList;

    private LinearLayout sumBtn;        //if shopping car have nothing , hide it
    private RelativeLayout nothingView;   // if shopping car have nothing , display it
    private ImageView allChecked;
    private TextView amountsPayable;
    private TextView closeAnAccount;

    private boolean allCheckedState = true;  //当前是否是全部选中
    private int sumPrice = 0;                // 当前选中的全部价格

    //从购物车中查询，更新产品数量，删除产品
    private FindShoppingCarByUserIdPresenter findShoppingCarByUserIdPresenter;
    private UpdaterShoppingCarProductCountPresenter updaterShoppingCarProductCountPresenter;
    private DeleteShoppingCarPresenter deleteShoppingCarPresenter;

    private List<ShoppingCar> shoppingCarList;   //从网络查询出来的ShoppingCar List

    private User user;  //当前的用户

    private boolean isFirstFind  = true; //是否是第一次查找,是就setAdapter，不是就update Adapter

    private int deletePosition = 0;  //要删除的位置
    private int updatePosition = 0;  //要更新的位置

    private boolean isOnResumeLoad = false;  //是否是偷偷的加载

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shopping_car, container, false);
        this.context = inflater.getContext();
        this.inflater = inflater;
        getUser();
        initView();
        initPresenter();
        startFindShoppingCarPresenter();
        return view;
    }

    //get user from share sharePrefrences
    //获取本地保存的登录用户信息
    private void getUser(){
        user = new User();
        SharedPreferences preferences = context.getSharedPreferences("account", MODE_PRIVATE);
        user.setId(preferences.getInt("id", 0));
        user.setPhone(preferences.getString("phone", ""));
        user.setType(preferences.getInt("type",0));
    }

    //init view
    private void initView(){
        shoppingCarSrlayout = (SwipeRefreshLayout)view.findViewById(R.id.shopping_car_swiperefresh_layout);
        shoppingCarListView = (ListView)view.findViewById(R.id.lv_shopping_car);
        allChecked = (ImageView)view.findViewById(R.id.all_checked);
        amountsPayable = (TextView)view.findViewById(R.id.amounts_payable);
        closeAnAccount = (TextView)view.findViewById(R.id.btn_close_an_account);
        sumBtn = (LinearLayout)view.findViewById(R.id.btn_sum_price);
        nothingView = (RelativeLayout)view.findViewById(R.id.shopping_car_nothing);

        shoppingCarSrlayout.setColorSchemeResources(R.color.colorIconSelected);
        shoppingCarListView.setDivider(null);
        allChecked.setOnClickListener(this);
        closeAnAccount.setOnClickListener(this);
        shoppingCarSrlayout.setOnRefreshListener(this);
    }

    //init presenter
    private void initPresenter(){
        findShoppingCarByUserIdPresenter = new FindShoppingCarByUserIdPresenter(context,this);
        deleteShoppingCarPresenter = new DeleteShoppingCarPresenter(context, this);
        updaterShoppingCarProductCountPresenter = new UpdaterShoppingCarProductCountPresenter(context,this);
    }

    //start the findShoppingCarByUserPresenter
    private void startFindShoppingCarPresenter(){
        if(NetUtil.getNetworkState(context) == NetUtil.NETWORN_NONE){
            Toast.makeText(context, "网络不可用",Toast.LENGTH_SHORT).show();
            sumBtn.setVisibility(View.GONE);
            shoppingCarSrlayout.setRefreshing(false);
        }else{
            sumBtn.setVisibility(View.VISIBLE);
            findShoppingCarByUserIdPresenter.findShoppingCarByUserId(user.getId());
        }
    }

    //start the deleteShoppingCarPresenter
    private void startDeleteShoppingCarPresenter(int id){
        if(NetUtil.getNetworkState(context) == NetUtil.NETWORN_NONE){
            Toast.makeText(context, "网络不可用",Toast.LENGTH_SHORT).show();
            sumBtn.setVisibility(View.GONE);
        }else{
            sumBtn.setVisibility(View.VISIBLE);
            deleteShoppingCarPresenter.deleteShoppingCar(id);
        }
    }

    //update the ShoppingCarPresenter
    private void startUpdateShoppingCarPresenter(int id, int count){
        if(NetUtil.getNetworkState(context) == NetUtil.NETWORN_NONE){
            Toast.makeText(context, "网络不可用",Toast.LENGTH_SHORT).show();
            sumBtn.setVisibility(View.GONE);
        }else{
            sumBtn.setVisibility(View.VISIBLE);
            updaterShoppingCarProductCountPresenter.updateShoppingCarCount(id, count);
        }
    }

    int goodsCount = 0;           //要更新后的Product数量和要更新的id
    int shoppingCarId = 0;
    //use handler to get the message from adapter
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
                    updatePosition = index;
                    goodsCount = shoppingCarList.get(index).getProductCount() + 1;
                    shoppingCarId = shoppingCarList.get(index).getId();
                    startUpdateShoppingCarPresenter(shoppingCarId,goodsCount);
                    break;
                case ShoppingCarAdapter.GOODS_SUB:
                    int goodsNum = shoppingCarItemList.get(index).getGoodsNum();
                    if(goodsNum == 1){
                        showDeleteAddressDialog(index);
                    }else{
                        updatePosition = index;
                        goodsCount = shoppingCarList.get(index).getProductCount() - 1;
                        shoppingCarId = shoppingCarList.get(index).getId();
                        startUpdateShoppingCarPresenter(shoppingCarId,goodsCount);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void showDialog() {
        showDialogs();
    }

    //查询返回的数据
    @Override
    public void showResult(List<ShoppingCar> shoppingCars) {
        dismissDialogs();
        if(shoppingCarSrlayout.isRefreshing()){
            shoppingCarSrlayout.setRefreshing(false);
        }
        shoppingCarList = shoppingCars;
        if (shoppingCars == null){
            nothingView.setVisibility(View.VISIBLE);
            sumBtn.setVisibility(View.GONE);
            shoppingCarListView.setVisibility(View.GONE);
        }else{
            nothingView.setVisibility(View.GONE);
            sumBtn.setVisibility(View.VISIBLE);
            shoppingCarListView.setVisibility(View.VISIBLE);
            setShoppingCarAdapter();
        }
    }

    //update 和 delete返回的数据
    @Override
    public void showDeleteResult(boolean isSuccess) {
        dismissDialogs();
        if(isSuccess){
            shoppingCarList.remove(deletePosition);
            shoppingCarItemList.remove(deletePosition);
            reSetShoppingCarAdapter(); //更新Adapter
            setSumPrice();            //重新计算总价

        }else {
            Toast.makeText(context, "删除失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showUpdateResult(boolean isSuccess) {
        dismissDialogs();
        if(isSuccess){
            //更新List和Adapter
            shoppingCarList.get(updatePosition).setProductCount(goodsCount);
            shoppingCarItemList.get(updatePosition).setGoodsNum(goodsCount);
            reSetShoppingCarAdapter();
            setSumPrice();          //重新计算总价

        }else {
            Toast.makeText(context, "更新失败",Toast.LENGTH_SHORT).show();
        }
    }


    ProgressDialog dialog = null;
    private void showDialogs(){
        if(dialog == null){
            dialog = new ProgressDialog(context);
            dialog.setMessage("正在努力中...");
        }

        if(isOnResumeLoad){  //是偷偷的加载，就不要显示Dialog
            isOnResumeLoad = false;
        }else{
            dialog.show();
        }
    }

    private void dismissDialogs(){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在这里重新查询数据,偷偷的加载，不要showDialog
        isOnResumeLoad = true;
        findShoppingCarByUserIdPresenter.findShoppingCarByUserId(user.getId());
    }

    //查询到的数据，转换成ShoppingCatItem去显示，并且设置或者更新Adapter
    private void setShoppingCarAdapter(){
        //转化成ShoppingCarItem
        shoppingCarItemList = null;
        shoppingCarItemList = new ArrayList<>();
        int length = shoppingCarList.size();
        for (int i = 0; i < length; i++){
            ShoppingCarItem shoppingCarItem = new ShoppingCarItem();
            ShoppingCar car = shoppingCarList.get(i);
            shoppingCarItem.setId(car.getId());
            shoppingCarItem.setProductId(car.getProductId());
            shoppingCarItem.setChecked(true);
            shoppingCarItem.setGoodsImg(car.getProductCover());
            shoppingCarItem.setGoodsNum(car.getProductCount());
            shoppingCarItem.setGoodsPrice(car.getRetailPrice());
            shoppingCarItem.setGoodsTitle(car.getProductName());
            shoppingCarItemList.add(shoppingCarItem);
        }

        //set or update shoppingcar adapter
        if(isFirstFind){
            shoppingCarAdapter = new ShoppingCarAdapter(context, handler,shoppingCarItemList);
            shoppingCarListView.setAdapter(shoppingCarAdapter);
            isFirstFind = false;
        }else {
            reSetShoppingCarAdapter();
        }

        //计算总价格，并且设置全选按钮为选中状态
        setSumPrice();
        setAllCheckedState();
    }

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
            sumBtn.setVisibility(View.GONE);
            nothingView.setVisibility(View.VISIBLE);
        }else {
            nothingView.setVisibility(View.GONE);
            sumBtn.setVisibility(View.VISIBLE);
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
        amountsPayable.setText(TypeConverters.IntConvertToString(sumPrice));
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

    @Override
    public void onRefresh() {
        startFindShoppingCarPresenter();
    }

    /***
     * get the list of checked
     * start the order confirmation activity
     * */
    private void startConfirmationActivity(){
        Intent intent = new Intent(context, OrderConfirmationActivity.class);
        List<ShoppingCar> checkedShoppingCarList = new ArrayList<>();
        List<ShoppingCarItem> checkedShoppingCatItemList = new ArrayList<>();
        int length = shoppingCarItemList.size();
        for(int i = 0; i < length; i++){
            if(shoppingCarItemList.get(i).isChecked()){
                checkedShoppingCarList.add(shoppingCarList.get(i));
                checkedShoppingCatItemList.add(shoppingCarItemList.get(i));
            }
        }
        intent.putExtra("checkedShoppingCarItemList", (Serializable)checkedShoppingCatItemList);
        intent.putExtra("checkedShoppingCarList",(Serializable)checkedShoppingCarList);
        intent.putExtra("sumPrice", sumPrice);
        startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    /**
     * show the dialog of delete the goods from shopping car
     * */
    private void showDeleteAddressDialog(final int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("删除宝贝");
        builder.setMessage("是否从购物车中移除这个宝贝？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteShoppingCar(index);
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

    //delete product from shopping car list
    private void deleteShoppingCar(int i){
        deletePosition = i;
        ShoppingCar shoppingCar = shoppingCarList.get(i);
        startDeleteShoppingCarPresenter(shoppingCar.getId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        updaterShoppingCarProductCountPresenter.setView(null);
        updaterShoppingCarProductCountPresenter = null;
        findShoppingCarByUserIdPresenter.setView(null);
        findShoppingCarByUserIdPresenter = null;
        deleteShoppingCarPresenter.setView(null);
        deleteShoppingCarPresenter = null;
    }
}
