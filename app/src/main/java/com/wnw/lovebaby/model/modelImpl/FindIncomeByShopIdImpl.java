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
import com.wnw.lovebaby.model.modelInterface.IFindIncomeByShopIdModel;
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/22.
 */

public class FindIncomeByShopIdImpl implements IFindIncomeByShopIdModel{

    private Context context;
    private FindIncomeByShopIdListener findIncomeByShopIdListener;
    private int income;

    @Override
    public void findIncomeByShopId(Context context, int shopId, FindIncomeByShopIdListener findIncomeByShopIdListener) {
        this.context = context;
        this.findIncomeByShopIdListener = findIncomeByShopIdListener;
        sendRequestWithVolley(shopId);
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(int shopId){
        String url = NetConfig.SERVICE + NetConfig.FIND_INCOME_BY_SHOP_ID + "shopId="+shopId;
        LogUtil.d("url", url);
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
            income = jsonObject.getInt("findIncomeByShopId");
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
        if(findIncomeByShopIdListener != null){
            findIncomeByShopIdListener.complete(income);
        }
    }
}
