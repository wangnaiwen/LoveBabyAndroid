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
import com.wnw.lovebaby.model.modelInterface.IUpdateShopModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by wnw on 2017/4/11.
 */

public class UpdateShopModelImpl implements IUpdateShopModel{

    private Context context;
    private ShopUpdateListener shopUpdateListener;
    private boolean returnData;

    @Override
    public void updateShop(Context context, Shop shop, ShopUpdateListener shopUpdateListener) {
        this.context = context;
        this.shopUpdateListener = shopUpdateListener;
        sendRequestWithVolley(shop);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(Shop shop){
        String name = "";
        String idCard = "";
        String owner = "";
        String wechat = "";
        try{
            name = URLEncoder.encode(shop.getName(), "UTF-8");
            idCard = URLEncoder.encode(shop.getIdCard(), "UTF-8");
            owner = URLEncoder.encode(shop.getOwner(), "UTF-8");
            wechat = URLEncoder.encode(shop.getWechat(), "UTF-8");

        }catch (Exception e){
            e.printStackTrace();
        }
        String url = NetConfig.SERVICE + NetConfig.UPDATE_SHOP
                +"id="+shop.getId()
                +"&userId="+shop.getUserId()
                +"&name="+ name
                +"&idCard="+ idCard
                +"&owner="+ owner
                +"&wechat=" + wechat
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
            returnData = jsonObject.getBoolean("updateShop");
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(shopUpdateListener != null){
            shopUpdateListener.complete(returnData);
        }
    }
}
