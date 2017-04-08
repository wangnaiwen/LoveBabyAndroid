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
import com.wnw.lovebaby.domain.ShoppingCar;
import com.wnw.lovebaby.model.modelInterface.IFindShoppingByUserIdModel;
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/4.
 */

public class FindShoppingCarByUserIdModelImpl implements IFindShoppingByUserIdModel{
    private Context context;
    private ShoppingFindByUserIdListener shoppingFindByUserIdListener;
    private List<ShoppingCar> shoppingCarList;

    @Override
    public void findShoppingCarByUserId(Context context, int userId, ShoppingFindByUserIdListener shoppingFindByUserIdListener) {
        this.context = context;
        this.shoppingFindByUserIdListener = shoppingFindByUserIdListener;
        sendRequestWithVolley(userId);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(int userId){
        String url = "http://119.29.182.235:8080/babyTest/findShoppingCarByUserId?";
        url = url + "userId="+userId;
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
            JSONArray jsonArray = jsonObject.getJSONArray("findShoppingCarByUserId");
            if(jsonArray != null){
                shoppingCarList = new ArrayList<>();
                int length = jsonArray.length();

                //数据翻过来装，这样显示最新的数据就可以放在最前面了
                for(int i = length-1 ; i >= 0; i--){
                    JSONObject object = jsonArray.getJSONObject(i);
                    ShoppingCar car = new ShoppingCar();
                    car.setId(object.getInt("id"));
                    car.setUserId(object.getInt("userId"));
                    car.setProductId(object.getInt("productId"));
                    car.setRetailPrice(object.getInt("retailPrice"));
                    car.setProductCover(object.getString("productCover"));
                    car.setProductName(object.getString("productName"));
                    car.setProductCount(object.getInt("productCount"));
                    shoppingCarList.add(car);
                }
            }else {
                Toast.makeText(context, "加载失败",Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(shoppingFindByUserIdListener != null){
            shoppingFindByUserIdListener.complete(shoppingCarList);
        }
    }
}
