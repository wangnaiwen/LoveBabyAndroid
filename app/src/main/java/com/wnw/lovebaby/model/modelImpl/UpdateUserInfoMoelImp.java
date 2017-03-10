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
import com.wnw.lovebaby.domain.ReceAddress;
import com.wnw.lovebaby.domain.UserInfo;
import com.wnw.lovebaby.model.modelInterface.IUpdateUserInfoModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/3/10.
 */

public class UpdateUserInfoMoelImp implements IUpdateUserInfoModel{

    private Context context;
    private UserInfoUpdateListener userInfoUpdateListener;
    private boolean reData;

   private String url = "http://119.29.182.235:8080/babyTest/updateUserInfo?";

    @Override
    public void updateUserInfo(Context context, UserInfo userInfo, UserInfoUpdateListener userInfoUpdateListener) {
        this.context = context;
        this.userInfoUpdateListener = userInfoUpdateListener;
        sendRequestWithVolley(userInfo);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(UserInfo userInfo){
        url = url + "id="+userInfo.getId() + "userId="+ userInfo.getUserId() +"&sex="+ userInfo.getSex() +"&userImg="+ userInfo.getUserImg()
                +"&nickName=" + userInfo.getNickName()+"&birthday="+ userInfo.getBirthday() +"&email="+ userInfo.getEmail()
                +"&hobby=" + userInfo.getHobby() +"&personalizedSignature="+ userInfo.getPersonalizedSignature();
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
            boolean isSuccess = jsonObject.getBoolean("updateUserInfo");
            if(isSuccess){
                Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show();
                reData = true;
            }else {
                Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();

    }

    private void retData(){
        if(userInfoUpdateListener != null){
            userInfoUpdateListener.complete(reData);
        }
    }
}
