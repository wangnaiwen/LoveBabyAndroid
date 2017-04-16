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
import com.wnw.lovebaby.domain.User;
import com.wnw.lovebaby.model.modelInterface.IRegisterModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2016/10/17.
 */

public class RegisterModelImp implements IRegisterModel {

    private Context context;
    private UserRegisterListener userRegisterListener;
    private boolean returnData = false;

    @Override
    public void registerNetUser(Context context, User user, UserRegisterListener userRegisterListener) {
        this.context =context;
        this.userRegisterListener = userRegisterListener;
        sendRequestWithVolley(user.getPhone(), user.getPassword());
    }

    private void retData(){
        if(userRegisterListener != null){
            userRegisterListener.complete(returnData);
        }
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(String phone, String password){
        String url = "http://119.29.182.235:8080/babyTest/register?phone="+ phone
               +"&password=" + password;
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

    private void parseNetUserWithJSON(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            boolean isExist = jsonObject.getBoolean("exist");
            if(isExist){
                Toast.makeText(context, "该用户已经存在", Toast.LENGTH_SHORT).show();
            }else {
                returnData = jsonObject.getBoolean("register");
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
