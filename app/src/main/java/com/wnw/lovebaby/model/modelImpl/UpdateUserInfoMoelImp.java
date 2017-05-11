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
import com.wnw.lovebaby.domain.ReceAddress;
import com.wnw.lovebaby.domain.UserInfo;
import com.wnw.lovebaby.model.modelInterface.IUpdateUserInfoModel;
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by wnw on 2017/3/10.
 */

public class UpdateUserInfoMoelImp implements IUpdateUserInfoModel{

    private Context context;
    private UserInfoUpdateListener userInfoUpdateListener;
    private boolean reData;

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
        String url = NetConfig.SERVICE + NetConfig.UPDATE_USER_INFO;
        String sex = "";
        String nickName = "";
        String email = "";
        String hobby = "";
        String personalizedSignature ="";
        String userImage = "";
        try{
            sex = URLEncoder.encode(userInfo.getSex(), "UTF-8");
            nickName = URLEncoder.encode(userInfo.getNickName(), "UTF-8");
            email = URLEncoder.encode(userInfo.getEmail(), "UTF-8");
            hobby = URLEncoder.encode(userInfo.getHobby(), "UTF-8");
            userImage = URLEncoder.encode(userInfo.getUserImg(), "UTF-8");
            personalizedSignature = URLEncoder.encode(userInfo.getPersonalizedSignature(), "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        url = url + "id="+userInfo.getId()
                + "&userId="+ userInfo.getUserId()
                +"&sex="+ sex
                +"&userImg="+ userImage
                +"&nickName=" + nickName
                +"&birthday="+ userInfo.getBirthday()
                +"&email="+ email
                +"&hobby=" + hobby
                +"&personalizedSignature=" + personalizedSignature;

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
