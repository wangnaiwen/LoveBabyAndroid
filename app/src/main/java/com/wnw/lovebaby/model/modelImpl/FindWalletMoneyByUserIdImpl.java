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
import com.wnw.lovebaby.config.NetConfig;
import com.wnw.lovebaby.model.modelInterface.IFindWalletMoneyByUserIdModel;
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/16.
 */

public class FindWalletMoneyByUserIdImpl implements IFindWalletMoneyByUserIdModel{

    private Context context;
    private WalletMoneyFindByUserIdListener walletMoneyFindByUserIdListener;
    private int returnData;

    @Override
    public void findWalletMoneyByUserId(Context context, int userId, WalletMoneyFindByUserIdListener walletMoneyFindByUserIdListener) {
        this.context = context;
        this.walletMoneyFindByUserIdListener = walletMoneyFindByUserIdListener;
        sendRequestWithVolley(userId);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(int userId){
        String url = NetConfig.SERVICE + NetConfig.FIND_WALLET_MONEY_BY_USERID
                + "userId="+userId;
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
            returnData = jsonObject.getInt("findWalleMoneyByUserId");
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(walletMoneyFindByUserIdListener != null){
            walletMoneyFindByUserIdListener.complete(returnData);
        }
    }
}
