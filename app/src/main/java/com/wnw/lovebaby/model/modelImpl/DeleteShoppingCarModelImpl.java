package com.wnw.lovebaby.model.modelImpl;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wnw.lovebaby.model.modelInterface.IDeleteShoppingCarModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/4.
 */

public class DeleteShoppingCarModelImpl implements IDeleteShoppingCarModel {

    private Context context;
    private ShoppingCarDeleteListener shoppingCarDeleteListener;
    private boolean returnData = false;
    @Override
    public void deleteShoppingCar(Context context, int id, ShoppingCarDeleteListener shoppingCarDeleteListener) {
        this.context = context;
        this.shoppingCarDeleteListener = shoppingCarDeleteListener;
        sendRequestWithVolley(id);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(int id){
        String url = "http://119.29.182.235:8080/babyTest/deleteShoppingCar?";
        url = url + "id=" +id;
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
            returnData = jsonObject.getBoolean("deleteShoppingCar");
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(shoppingCarDeleteListener != null){
            shoppingCarDeleteListener.complete(returnData);
        }
    }
}
