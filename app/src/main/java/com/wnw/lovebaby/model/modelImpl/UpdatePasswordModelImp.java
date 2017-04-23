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
import com.wnw.lovebaby.model.modelInterface.IUpdatePasswordModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/3/7.
 */

public class UpdatePasswordModelImp implements IUpdatePasswordModel {
    private Context context;
    private boolean updateSuccess;
    private User user;
    private UpdateUserPasswordListener updateUserPasswordListener;
    private String phone;

    @Override
    public void updateUserPassword(Context context, String phone, String password, UpdateUserPasswordListener updateUserPasswordListener) {
        this.context = context;
        this.updateUserPasswordListener = updateUserPasswordListener;
        this.phone = phone;
        sendRequestWithVolley(phone, password);
    }

    private void retData(){
        if(updateUserPasswordListener != null){
            updateUserPasswordListener.complete(user);
        }
    }

    /**
     * use volley to update the data
     * */
    private void sendRequestWithVolley(String phone, String password){
        String url = NetConfig.SERVICE + NetConfig.UPDATE_USER_PASSWORD;
        url = url + "phone="+ phone +"&password=" + password;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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

    /**
     * use volley to find the data
     * */
    private void sendRequestWithVolley(String phone){
        String url = "http://119.29.182.235:8080/babyTest/findUser?phone="+ phone;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseUserWithJSON(response);
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
            boolean isSuccess = jsonObject.getBoolean("updateUserPassword");
            if(isSuccess){
                sendRequestWithVolley(phone);
            }else {
                retData();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void parseUserWithJSON(String response){
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
}
