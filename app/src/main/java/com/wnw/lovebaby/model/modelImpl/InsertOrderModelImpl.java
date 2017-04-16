package com.wnw.lovebaby.model.modelImpl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wnw.lovebaby.config.NetConfig;
import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.model.modelInterface.IInsertOrderModel;
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/11.
 */

public class InsertOrderModelImpl implements IInsertOrderModel {

    private Context context;
    private  OrderInsertListener orderInsertListener;
    private boolean returnData;
    private int returnId;

    @Override
    public void insertOrder(Context context, Order order, OrderInsertListener orderInsertListener) {
        this.context = context;
        this.orderInsertListener = orderInsertListener;
        sendRequestWithVolley(order);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(Order order){
        String url = NetConfig.SERVICE + NetConfig.INSERT_ORDER
                + "userId="+order.getUserId()
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
            returnData = jsonObject.getBoolean("insertOrder");

            if(!returnData){
                Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
            }else {
                returnId = jsonObject.getInt("id");
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(orderInsertListener != null){
            orderInsertListener.complete(returnData,returnId);
        }
    }
}
