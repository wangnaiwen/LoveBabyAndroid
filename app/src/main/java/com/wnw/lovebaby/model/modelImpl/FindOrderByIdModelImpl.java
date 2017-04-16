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
import com.wnw.lovebaby.model.modelInterface.IFindOrderByIdModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/11.
 */

public class FindOrderByIdModelImpl implements IFindOrderByIdModel {

    private Context context;
    private OrderFindByIdListener orderFindByIdListener;
    private Order returnData;

    @Override
    public void findOrderById(Context context, int id, OrderFindByIdListener orderFindByIdListener) {
        this.context = context;
        this.orderFindByIdListener = orderFindByIdListener;
        sendRequestWithVolley(id);
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(int id){
        String url = NetConfig.SERVICE+NetConfig.FIND_ORDER_BY_ID
                +"id="+id;

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                parseNetUserWithJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("wnw", "Error:" + error.getNetworkTimeMs() + error.getMessage());
            }
        });
        queue.add(request);
    }

    private void parseNetUserWithJSON(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONObject resultObject = jsonObject.getJSONObject("findOrderById");
            if(resultObject != null){
                returnData = new Order();
                returnData.setId(resultObject.getInt("id"));
                returnData.setShopId(resultObject.getInt("shopId"));
                returnData.setAddressId(resultObject.getInt("addressId"));
                returnData.setUserId(resultObject.getInt("userId"));
                returnData.setCreateTime(resultObject.getString("createTime"));
                returnData.setPayTime(resultObject.getString("payTime"));
                returnData.setFinishTime(resultObject.getString("finishTime"));
                returnData.setOrderNumber(resultObject.getString("orderNumber"));
                returnData.setOrderType(resultObject.getInt("orderType"));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        /**
         * 解析完后返回数据
         * */
        retData();
    }

    /***
     * return data
     */

    private void retData(){
        if(orderFindByIdListener != null){
            orderFindByIdListener.complete(returnData);
        }
    }
}
