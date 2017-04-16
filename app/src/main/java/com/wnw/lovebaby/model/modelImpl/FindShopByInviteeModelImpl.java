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
import com.wnw.lovebaby.model.modelInterface.IFindShopByInviteeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/11.
 */

public class FindShopByInviteeModelImpl implements IFindShopByInviteeModel {

    private Context context;
    private ShopFindByInviteeListener shopFindByInviteeListener;
    private List<Shop> returnData;

    @Override
    public void findShopByInvitee(Context context, int invitee, ShopFindByInviteeListener shopFindByInviteeListener) {
        this.context = context;
        this.shopFindByInviteeListener = shopFindByInviteeListener;
        sendRequestWithVolley(invitee);
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(int invitee){
        String url = NetConfig.SERVICE+NetConfig.FIND_SHOP_BY_INVITEE
                +"invitee="+invitee;

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
            JSONArray jsonArray = jsonObject.getJSONArray("findShopByInvitee");
            if(jsonArray != null){
                returnData = new ArrayList<>();
                int length = jsonArray.length();
                for(int i = 0 ; i < length; i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Shop shop = new Shop();
                    shop.setId(object.getInt("id"));
                    shop.setUserId(object.getInt("userId"));
                    shop.setIdCard(object.getString("idCard"));
                    shop.setInvitee(object.getInt("invitee"));
                    shop.setMoney(object.getLong("money"));
                    shop.setName(object.getString("name"));
                    shop.setOwner(object.getString("owner"));
                    shop.setReviewType(object.getInt("reviewType"));
                    shop.setWechat(object.getString("wechat"));
                    returnData.add(shop);
                }
            }else {
                //Toast.makeText(context, "找不到收货地址", Toast.LENGTH_SHORT).show();
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
        if(shopFindByInviteeListener != null){
            shopFindByInviteeListener.complete(returnData);
        }
    }
}
