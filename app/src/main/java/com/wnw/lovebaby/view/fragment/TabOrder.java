package com.wnw.lovebaby.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.MyOrderAdapter;
import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.FindOrderByUserIdPresenter;
import com.wnw.lovebaby.util.LogUtil;
import com.wnw.lovebaby.view.activity.MyOrderDetailActivity;
import com.wnw.lovebaby.view.viewInterface.IFindOrderByIdView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/16.
 */

public class TabOrder extends Fragment implements AdapterView.OnItemClickListener{

    private Context context;
    private View view;
    private ListView myOrderLv;
    private TextView noOrder;
    private MyOrderAdapter myOrderAdapter;
    private List<Order> orderList = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = inflater.getContext();
        view = inflater.inflate(R.layout.tab_all_order,container, false);
        initView();
        initAdapter();
        return view;
    }

    private void initView(){
        myOrderLv = (ListView)view.findViewById(R.id.lv_my_order);
        noOrder = (TextView)view.findViewById(R.id.null_order);

        if(orderList.size() == 0){
            noOrder.setVisibility(View.VISIBLE);
            myOrderLv.setVisibility(View.GONE);
        }else{
            noOrder.setVisibility(View.GONE);
            myOrderLv.setVisibility(View.VISIBLE);
        }

        myOrderLv.setOnItemClickListener(this);
    }

    private void initAdapter(){
        myOrderAdapter = new MyOrderAdapter(context, orderList, nameList);
        myOrderLv.setAdapter(myOrderAdapter);
    }

    public void setOrderList(List<Order> orderList, List<String> names){
        this.orderList = orderList;
        this.nameList = names;
        if(myOrderAdapter != null){
            myOrderAdapter.setOrderList(orderList);
            myOrderAdapter.setNameList(nameList);
            myOrderAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.lv_my_order:
                if(NetUtil.getNetworkState(context) == NetUtil.NETWORN_NONE){
                    Toast.makeText(context, "当前无网络", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(context, MyOrderDetailActivity.class);
                    intent.putExtra("order", orderList.get(i));
                    startActivity(intent);
                    ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;
        }
    }
}
