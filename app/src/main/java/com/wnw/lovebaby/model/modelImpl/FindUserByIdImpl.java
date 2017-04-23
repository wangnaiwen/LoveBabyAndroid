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
import com.wnw.lovebaby.domain.User;
import com.wnw.lovebaby.model.modelInterface.IFindUserByIdModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/4/23.
 */

public class FindUserByIdImpl implements IFindUserByIdModel {
    private Context context;
    private FindUserByIdListener findUserByIdListener;
    private User user;

    @Override
    public void findUserById(Context context, int id, FindUserByIdListener findUserByIdListener) {
        this.context = context;
        this.findUserByIdListener = findUserByIdListener;
        sendRequestWithVolley(id);
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(int id){
        String url = NetConfig.SERVICE + NetConfig.FIND_USER_BY_ID;
        url = url + "id=" + id;
        Log.d("wnwUser",url );
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
            JSONObject resultObject = jsonObject.getJSONObject("user");
            if(resultObject != null){
                user = new User();
                user.setId(resultObject.getInt("id"));
                user.setPhone(resultObject.getString("phone"));
                user.setPassword(resultObject.getString("password"));
                user.setType(resultObject.getInt("type"));
            }
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
        if(findUserByIdListener != null){
            findUserByIdListener.complete(user);
        }
    }
}
