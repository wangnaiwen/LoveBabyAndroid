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
import com.wnw.lovebaby.model.modelInterface.IFindShopByUserIdModel;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by wnw on 2017/4/11.
 */

public class FindShopByUserIdModelImpl implements IFindShopByUserIdModel {

    private Context context;
    private ShopFindByUserIdListener shopFindByUserIdListener;
    private Shop returnData;

    @Override
    public void findShopByUserId(Context context, int userId, ShopFindByUserIdListener shopFindByUserIdListener) {
        this.context = context;
        this.shopFindByUserIdListener = shopFindByUserIdListener;
        sendRequestWithVolley(userId);
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(int userId){
        String url = NetConfig.SERVICE+NetConfig.FIND_SHOP_BY_USER_ID
                +"userId="+userId;

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
            JSONObject resultObject = jsonObject.getJSONObject("findShopByUserId");
            if(resultObject != null){
                returnData = new Shop();
                returnData.setId(resultObject.getInt("id"));
                returnData.setUserId(resultObject.getInt("userId"));
                returnData.setIdCard(resultObject.getString("idCard"));
                returnData.setInvitee(resultObject.getInt("invitee"));
                returnData.setMoney(resultObject.getLong("money"));
                returnData.setName(resultObject.getString("name"));
                returnData.setOwner(resultObject.getString("owner"));
                returnData.setReviewType(resultObject.getInt("reviewType"));
                returnData.setWechat(resultObject.getString("wechat"));
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
        if(shopFindByUserIdListener != null){
            shopFindByUserIdListener.complete(returnData);
        }
    }
}
