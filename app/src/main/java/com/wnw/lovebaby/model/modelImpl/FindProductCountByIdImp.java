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
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by wnw on 2017/5/19.
 */

public class FindProductCountByIdImp implements IFindProductCountByIdModel{

    private Context context;
    private FindProductCountByIdListener findProductCountByIdListener;
    private int count = 0;

    @Override
    public void findProductCountById(Context context, int id, FindProductCountByIdListener findProductCountByIdListener) {
        this.context = context;
        this.findProductCountByIdListener = findProductCountByIdListener;
        sendRequestWithVolley(id);
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(int id){
        String url = NetConfig.SERVICE+NetConfig.FIND_PRODUCT_COUONT_BY_ID
                +"id="+id;
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
            count = jsonObject.getInt("findProductCountsById");
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
        if(findProductCountByIdListener != null){
            findProductCountByIdListener.complete(count);
        }
    }
}
