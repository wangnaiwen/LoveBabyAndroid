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
import com.wnw.lovebaby.model.modelInterface.IValiteWalletModel;
import com.wnw.lovebaby.util.LogUtil;
import com.wnw.lovebaby.util.Md5Encode;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/16.
 */

public class ValiteWalletModelImpl implements IValiteWalletModel{
    private Context context;
    private WalletValiteListener walletValiteListener;
    private boolean returnData;


    @Override
    public void valiteWallet(Context context, int userId, String password, WalletValiteListener walletValiteListener) {
        this.context = context;
        this.walletValiteListener = walletValiteListener;
        sendRequestWithVolley(userId, password);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(int userId, String password){
        String url = NetConfig.SERVICE
                + NetConfig.VALITE_WALLET
                +"userId=" + userId
                +"&password=" + Md5Encode.getEd5EncodePassword(password);
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
            returnData = jsonObject.getBoolean("valiteWallet");
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(walletValiteListener != null){
            walletValiteListener.complete(returnData);
        }
    }
}
