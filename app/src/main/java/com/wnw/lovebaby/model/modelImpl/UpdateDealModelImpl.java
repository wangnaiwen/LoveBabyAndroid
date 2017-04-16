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
import com.wnw.lovebaby.model.modelInterface.IUpdateDealModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/11.
 */

public class UpdateDealModelImpl implements IUpdateDealModel {

    private Context context;
    private DealUpdateListener dealUpdateListener;
    private boolean returnData;

    @Override
    public void updateDeal(Context context, Deal deal, DealUpdateListener dealUpdateListener) {
        this.context = context;
        this.dealUpdateListener = dealUpdateListener;
        sendRequestWithVolley(deal);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(Deal deal){
        String url = NetConfig.SERVICE + NetConfig.UPDATE_DEAL
                +"id=" + deal.getId()
                +"&orderId="+deal.getOrderId()
                +"&productId="+ deal.getProductId()
                +"&productName="+ deal.getProductName()
                +"&productCount="+ deal.getProductCount()
                +"&sumPrice=" + deal.getSumPrice();
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
            returnData = jsonObject.getBoolean("updateDeal");
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(dealUpdateListener != null){
            dealUpdateListener.complete(returnData);
        }
    }
}
