package com.wnw.lovebaby.model.modelImpl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wnw.lovebaby.domain.Product;
import com.wnw.lovebaby.model.modelInterface.IFindSpecialPriceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/3.
 */

public class FindSpecialPriceModelImpl implements IFindSpecialPriceModel {

    private Context context;
    private FindSpecialPriceListener findSpecialPriceListener;
    private List<Product> returnData;

    @Override
    public void findSpecialPrice(Context context, FindSpecialPriceListener findSpecialPriceListener) {
        this.context = context;
        this.findSpecialPriceListener = findSpecialPriceListener;
        sendRequestWithVolley();
    }

    private void sendRequestWithVolley(){
        String url = "http://119.29.182.235:8080/babyTest/findSpecialPrice";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
            JSONArray jsonArray = jsonObject.getJSONArray("specialPrice");
            if(jsonArray != null){
                returnData = new ArrayList<>();
                int length = jsonArray.length();
                for(int i = 0 ; i < length; i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Product product = new Product();
                    product.setId(object.getInt("id"));
                    product.setScId(object.getInt("scId"));
                    product.setNumbering(object.getString("numbering"));
                    product.setBrand(object.getString("brand"));
                    product.setCoverImg(object.getString("coverImg"));
                    product.setName(object.getString("name"));
                    product.setDescription(object.getString("description"));
                    product.setRetailPrice(object.getLong("retailPrice"));
                    product.setStandardPrice(object.getLong("standardPrice"));
                    returnData.add(product);
                }
            }else {
                Toast.makeText(context, "找不到", Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(findSpecialPriceListener != null){
            findSpecialPriceListener.complete(returnData);
        }
    }
}
