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
import com.wnw.lovebaby.model.modelInterface.ISubWalletMoneyModel;
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/16.
 */

public class SubWalletMoneyModelImpl implements ISubWalletMoneyModel{
    private Context context;
    private WalletMoneySubListener walletMoneySubListener;
    private boolean returnData;

    @Override
    public void subWalletMoney(Context context, int userId, int money, WalletMoneySubListener walletMoneySubListener) {
        this.context = context;
        this.walletMoneySubListener = walletMoneySubListener;
        sendRequestWithVolley(userId, money);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(int userId, int money){
        String url = NetConfig.SERVICE
                + NetConfig.SUB_WALLET_MONEY
                +"userId=" + userId
                +"&money=" + money;
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
            returnData = jsonObject.getBoolean("subWalletMoney");
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(walletMoneySubListener != null){
            walletMoneySubListener.complete(returnData);
        }
    }
}
