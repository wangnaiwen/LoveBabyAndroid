package com.wnw.lovebaby.model;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wnw.lovebaby.domain.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2016/10/17.
 */

public class LoginModelImp implements ILoginModel{

    private Context context;
    private User user;
    private UserLoadingListener userLoadingListener;

    @Override
    public void loadUser(Context context, User user, UserLoadingListener loadingListener) {
        this.context = context;
        sendRequestWithVolley(user.getPhone(), user.getPassword());
        this.userLoadingListener = loadingListener;
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(String phone, String password){
        String url = "http://119.29.182.235:8080/babyTest/login?phone=" + phone+"&password="+password;
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
        if(userLoadingListener != null){
            userLoadingListener.complete(user);
        }
    }
}
