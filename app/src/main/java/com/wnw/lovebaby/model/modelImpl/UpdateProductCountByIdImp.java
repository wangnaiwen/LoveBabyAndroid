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
import com.wnw.lovebaby.model.modelInterface.IFindProductCountByIdModel;
import com.wnw.lovebaby.model.modelInterface.IUpdateProductCountByIdModel;
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/5/19.
 */

public class UpdateProductCountByIdImp implements IUpdateProductCountByIdModel{
    private Context context;
    private UpdateProductCountByIdListener updateProductCountByIdListener;
    private boolean isSuccess;

    @Override
    public void updateProductCountById(Context context, int id, int count, UpdateProductCountByIdListener updateProductCountByIdListener) {
        this.context = context;
        this.updateProductCountByIdListener = updateProductCountByIdListener;
        sendRequestWithVolley(id, count);
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(int id, int count){
        String url = NetConfig.SERVICE+NetConfig.UPDATE_PRODUCT_COUONT_BY_ID
                +"id="+id
                +"&count=" + count;
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
            isSuccess = jsonObject.getBoolean("updateProductCount");
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
        if(updateProductCountByIdListener != null){
            updateProductCountByIdListener.complete(isSuccess);
        }
    }

}
