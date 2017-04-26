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
import com.wnw.lovebaby.model.modelInterface.IFindWithdrawMoneyByUserIdModel;
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/26.
 */

public class FindWithdrawMoneyByUserIdImpl implements IFindWithdrawMoneyByUserIdModel{

    private Context context;
    private FindWithdrawMoneyByUserIdListener findWithdrawMoneyByUserIdListener;
    private int returnData;
    @Override
    public void findWithdrawMoneyByUserId(Context context, int userId, FindWithdrawMoneyByUserIdListener findWithdrawMoneyByUserIdListener) {
        this.context = context;
        this.findWithdrawMoneyByUserIdListener = findWithdrawMoneyByUserIdListener;
        sendRequestWithVolley(userId);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(int userId){
        String url = NetConfig.SERVICE + NetConfig.FIND_WITHDRAW_MONEY_BY_USER_ID
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
            returnData = jsonObject.getInt("findWithdrawMoneyByUserId");
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(findWithdrawMoneyByUserIdListener != null){
            findWithdrawMoneyByUserIdListener.complete(returnData);
        }
    }
}
