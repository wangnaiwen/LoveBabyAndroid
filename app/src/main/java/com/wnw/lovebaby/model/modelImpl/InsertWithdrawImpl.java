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
import com.wnw.lovebaby.domain.Withdraw;
import com.wnw.lovebaby.model.modelInterface.IInsertWithdrawModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/26.
 */

public class InsertWithdrawImpl implements IInsertWithdrawModel{

    private Context context;
    private InsertWithdrawListener insertWithdrawListener;
    private boolean result;


    @Override
    public void insertWithdraw(Context context, Withdraw withdraw, InsertWithdrawListener insertWithdrawListener) {
        this.context = context;
        this.insertWithdrawListener = insertWithdrawListener;
        sendRequestWithVolley(withdraw);
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(Withdraw withdraw){
        String url = NetConfig.SERVICE + NetConfig.INSERT_WITHDRAW;
        url = url
                + "userId=" +withdraw.getUserId()
                +"&money=" + withdraw.getMoney();
        Log.d("url",url );
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
            result = jsonObject.getBoolean("insertWithdraw");
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
        if(insertWithdrawListener != null){
            insertWithdrawListener.complete(result);
        }
    }
}
