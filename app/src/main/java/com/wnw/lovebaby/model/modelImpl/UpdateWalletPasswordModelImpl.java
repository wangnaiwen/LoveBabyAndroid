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
import com.wnw.lovebaby.model.modelInterface.IUpdateWalletPasswordModel;
import com.wnw.lovebaby.util.LogUtil;
import com.wnw.lovebaby.util.Md5Encode;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/16.
 */

public class UpdateWalletPasswordModelImpl implements IUpdateWalletPasswordModel{
    private Context context;
    private WalletPasswordUpdateListener walletPasswordUpdateListener;
    private boolean returnData;

    @Override
    public void updateWalletPassword(Context context, int userId, int password, WalletPasswordUpdateListener walletPasswordUpdateListener) {
        this.context = context;
        this.walletPasswordUpdateListener = walletPasswordUpdateListener;
        sendRequestWithVolley(userId, password);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(int userId, int password){
        String url = NetConfig.SERVICE
                + NetConfig.UPDATE_WALLET_PASSWORD
                +"userId=" + userId
                +"&password=" + Md5Encode.getEd5EncodePassword(password+"");
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
            returnData = jsonObject.getBoolean("updateWalletPassword");
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(walletPasswordUpdateListener != null){
            walletPasswordUpdateListener.complete(returnData);
        }
    }
}
