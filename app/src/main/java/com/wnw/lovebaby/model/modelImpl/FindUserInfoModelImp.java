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
import com.wnw.lovebaby.domain.User;
import com.wnw.lovebaby.domain.UserInfo;
import com.wnw.lovebaby.model.modelInterface.IFindUserInfoModel;
import com.wnw.lovebaby.model.modelInterface.IUpdateUserInfoModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/3/10.
 */

public class FindUserInfoModelImp implements IFindUserInfoModel{

    private Context context;
    private UserInfoLoadingListener userInfoLoadingListener;
    private UserInfo userInfo;

    @Override
    public void FindUserInfo(Context context, int id, UserInfoLoadingListener userInfoLoadingListener) {
        this.context = context;
        this.userInfoLoadingListener = userInfoLoadingListener;
        sendRequestWithVolley(id);
    }
    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(int userId){
        String url = "http://119.29.182.235:8080/babyTest/findUserInfoByUserId?";
        url = url + "userId="+userId;
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
            JSONObject  object = jsonObject.getJSONObject("findUserInfoByUserId");
            if(object != null){
                userInfo = new UserInfo();
                userInfo.setId(object.getInt("id"));
                userInfo.setUserId(object.getInt("userId"));
                userInfo.setNickName(object.getString("nickName"));
                userInfo.setSex(object.getString("sex"));
                userInfo.setBirthday(object.getString("birthday"));
                userInfo.setUserImg(object.getString("userImg"));
                userInfo.setHobby(object.getString("hobby"));
                userInfo.setEmail(object.getString("email"));
                userInfo.setPersonalizedSignature(object.getString("personalizedSignature"));
            }else {
                Toast.makeText(context, "加载失败",Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(userInfoLoadingListener != null){
            userInfoLoadingListener.complete(userInfo);
        }
    }
}
