package com.wnw.lovebaby.model.modelImpl;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wnw.lovebaby.config.NetConfig;
import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.model.modelInterface.IUpdateOrderModel;
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/11.
 */

public class UpdateOrderModelImpl implements IUpdateOrderModel {

    private Context context;
    private OrderUpdateListener orderUpdateListener;
    private boolean returnData;

    @Override
    public void updateOrder(Context context, Order order, OrderUpdateListener orderUpdateListener) {
        this.context = context;
        this.orderUpdateListener = orderUpdateListener;
        sendRequestWithVolley(order);
    }
    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(Order order){
        String url = NetConfig.SERVICE + NetConfig.UPDATE_ORDER
                +"id="+order.getId()
                +"&userId="+order.getUserId()
                +"&shopId="+ order.getShopId()
                +"&addressId="+ order.getAddressId()
                +"&orderType="+ order.getOrderType()
                +"&createTime=" + order.getCreateTime()
                +"&payTime="+ order.getPayTime()
                +"&finishTime="+ order.getFinishTime()
                +"&orderNumber="+order.getOrderNumber();
        LogUtil.d("url", url);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseWithJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("wnw", "Error:" + error.getNetworkTimeMs() + error.getMessage());
            }
        });
        queue.add(request);
    }

    private void parseWithJSON(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            returnData = jsonObject.getBoolean("updateOrder");
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(orderUpdateListener != null){
            orderUpdateListener.complete(returnData);
        }
    }
}
