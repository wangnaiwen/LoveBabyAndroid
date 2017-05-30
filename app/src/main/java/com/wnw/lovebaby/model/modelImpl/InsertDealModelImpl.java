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
import com.wnw.lovebaby.model.modelInterface.IInsertDealModel;
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by wnw on 2017/4/11.
 */

public class InsertDealModelImpl implements IInsertDealModel{

    private Context context;
    private DealInsertListener dealInsertListener;
    private boolean returnData;

    @Override
    public void insertDeal(Context context, Deal deal, DealInsertListener dealInsertListener) {
        this.context = context;
        this.dealInsertListener = dealInsertListener;
        sendRequestWithVolley(deal);
    }


    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(Deal deal){
        String productName = "";
        try{
            productName = URLEncoder.encode(deal.getProductName(), "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        String url = NetConfig.SERVICE + NetConfig.INSERT_DEAL
                + "orderId="+deal.getOrderId()
                +"&productId="+ deal.getProductId()
                +"&productName="+ productName
                +"&productCount="+ deal.getProductCount()
                +"&sumPrice=" + deal.getSumPrice()
                +"&price=" + deal.getPrice();
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
            returnData = jsonObject.getBoolean("insertDeal");
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(dealInsertListener != null){
            dealInsertListener.complete(returnData);
        }
    }
}
