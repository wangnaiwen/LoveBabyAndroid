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
import com.wnw.lovebaby.model.modelInterface.IFindIncomeByInviteeModel;
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/22.
 */

public class FindIncomeByInviteeImpl implements IFindIncomeByInviteeModel{

    private Context context;
    private FindIncomeByInviteeListener findIncomeByInviteeListener;
    private int income;
    @Override
    public void findIncomeByShopId(Context context, int invitee, FindIncomeByInviteeListener findIncomeByInviteeListener) {
        this.context = context;
        this.findIncomeByInviteeListener = findIncomeByInviteeListener;
        sendRequestWithVolley(invitee);
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(int invitee){
        String url = NetConfig.SERVICE + NetConfig.FIND_INCOME_BY_INVITEE + "invitee="+invitee;
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
            income = jsonObject.getInt("findIncomeByInvitee");
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
        if(findIncomeByInviteeListener != null){
            findIncomeByInviteeListener.complete(income);
        }
    }
}
