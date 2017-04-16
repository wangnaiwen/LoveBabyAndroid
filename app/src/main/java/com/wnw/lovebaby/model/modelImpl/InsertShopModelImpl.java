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
import com.wnw.lovebaby.domain.Shop;
import com.wnw.lovebaby.model.modelInterface.IInsertShopModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/11.
 */

public class InsertShopModelImpl implements IInsertShopModel{

    private Context context;
    private ShopInsertListener shopInsertListener;
    private boolean returnData;

    @Override
    public void insertShop(Context context, Shop shop, ShopInsertListener shopInsertListener) {
        this.context = context;
        this.shopInsertListener = shopInsertListener;
        sendRequestWithVolley(shop);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(Shop shop){
        String url = NetConfig.SERVICE + NetConfig.INSERT_SHOP
                + "userId="+shop.getUserId()
                +"&name="+ shop.getName()
                +"&idCard="+ shop.getIdCard()
                +"&owner="+ shop.getOwner()
                +"&wechat=" + shop.getWechat()
                +"&money="+ shop.getMoney()
                +"&reviewType="+ shop.getReviewType()
                +"&invitee="+shop.getInvitee();

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
            returnData = jsonObject.getBoolean("insertShop");
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(shopInsertListener != null){
            shopInsertListener.complete(returnData);
        }
    }
}
