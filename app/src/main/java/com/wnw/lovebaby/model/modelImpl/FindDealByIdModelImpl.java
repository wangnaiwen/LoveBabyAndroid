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
import com.wnw.lovebaby.domain.Deal;
import com.wnw.lovebaby.model.modelInterface.IFindDealByIdModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/11.
 */

public class FindDealByIdModelImpl implements IFindDealByIdModel{

    private Context context;
    private DealFindByIdListener dealFindByIdListener;
    private Deal returnData;

    @Override
    public void findDealById(Context context, int id, DealFindByIdListener dealFindByIdListener) {
        this.context = context;
        this.dealFindByIdListener = dealFindByIdListener;
        sendRequestWithVolley(id);
    }

    private void sendRequestWithVolley(int id){
        String url = NetConfig.SERVICE+NetConfig.FIND_DEAL_BY_ID
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
            JSONObject resultObject = jsonObject.getJSONObject("findDealById");
            if(resultObject != null){
                returnData = new Deal();
                returnData.setId(resultObject.getInt("id"));
                returnData.setOrderId(resultObject.getInt("orderId"));
                returnData.setProductId(resultObject.getInt("productId"));
                returnData.setProductCount(resultObject.getInt("productCount"));
                returnData.setSumPrice(resultObject.getLong("sumPrice"));
                returnData.setPrice(resultObject.getLong("price"));
                returnData.setProductName(resultObject.getString("productName"));
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
        if(dealFindByIdListener != null){
            dealFindByIdListener.complete(returnData);
        }
    }
}

