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
import com.wnw.lovebaby.domain.ShoppingCar;
import com.wnw.lovebaby.model.modelInterface.IInsertShoppingCarModel;
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/4.
 */

public class InsertShoppingCarModelImpl implements IInsertShoppingCarModel{
    private Context context;
    private ShoppingCarInsertListener shoppingCarInsertListener;
    private boolean returnData = false;

    @Override
    public void insertShoppingCar(Context context, ShoppingCar car, ShoppingCarInsertListener shoppingCarInsertListener) {
        this.context = context;
        this.shoppingCarInsertListener = shoppingCarInsertListener;
        sendRequestWithVolley(car);
    }
    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(ShoppingCar car){
        String url = NetConfig.SERVICE + NetConfig.INSERT_SHOPPING_CAR;
        url = url
                +"userId="+car.getUserId()
                +"&productId="+car.getProductId()
                +"&productName="+car.getProductName()
                +"&productCover="+car.getProductCover()
                +"&retailPrice="+car.getRetailPrice()
                +"&productCount="+car.getProductCount();
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
            returnData = jsonObject.getBoolean("insertShoppingCar");
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(shoppingCarInsertListener != null){
            shoppingCarInsertListener.complete(returnData);
        }
    }
}
