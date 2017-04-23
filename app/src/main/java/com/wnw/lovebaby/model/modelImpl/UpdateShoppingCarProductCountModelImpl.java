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
import com.wnw.lovebaby.model.modelInterface.IUpdateShoppingCarProductCountModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/4.
 */

public class UpdateShoppingCarProductCountModelImpl implements IUpdateShoppingCarProductCountModel {

    private Context context;
    private ShoppingCarProductCountUpdateListener shoppingCarProductCountUpdateListener;
    private boolean returnData = false;

   @Override
    public void updateShoppingCarProductCount(Context context, int id, int count, ShoppingCarProductCountUpdateListener shoppingCarProductCountUpdateListener) {
       this.context = context;
       this.shoppingCarProductCountUpdateListener = shoppingCarProductCountUpdateListener;
       sendRequestWithVolley(id, count);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(int id, int count){
        String url = NetConfig.SERVICE + NetConfig.UPDATE_SHOPPING_CAR_PRODUCT_COUNT;
        url = url + "id=" +id +"&count="+count;
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
            returnData = jsonObject.getBoolean("updateShoppingCarProductCount");
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(shoppingCarProductCountUpdateListener != null){
            shoppingCarProductCountUpdateListener.complete(returnData);
        }
    }
}
