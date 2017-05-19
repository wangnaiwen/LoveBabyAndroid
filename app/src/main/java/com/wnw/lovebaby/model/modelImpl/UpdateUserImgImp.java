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
import com.wnw.lovebaby.model.modelInterface.IUpdateUserImgModel;
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by wnw on 2017/5/19.
 */

public class UpdateUserImgImp implements IUpdateUserImgModel{

    private Context context;
    private UserImgUpdateListener userImgUpdateListener;
    private boolean reData;

    @Override
    public void updateUserImg(Context context, int userId, String image, UserImgUpdateListener userImgUpdateListener) {
        this.context = context;
        this.userImgUpdateListener = userImgUpdateListener;
        sendRequestWithVolley(userId, image);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(int userId, String image){
        String url = NetConfig.SERVICE + NetConfig.UPDATE_USER_IMG;
        String img = "";
        try{
            img = URLEncoder.encode(image, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        url = url + "userId="+userId
                + "&image="+ img;

        LogUtil.d("url", url);
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
            reData = jsonObject.getBoolean("updateUserImg");

        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(userImgUpdateListener != null){
            userImgUpdateListener.complete(reData);
        }
    }
}
