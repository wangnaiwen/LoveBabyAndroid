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
import com.wnw.lovebaby.model.modelInterface.IValidateArticleLike;
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/6/1.
 */

public class ValidateArticleLikeImp implements IValidateArticleLike {
    private Context context;
    private ValidateArticleLikeListener validateArticleLikeListener;
    private boolean returnData;

    @Override
    public void validateArticleLike(Context context, int userId, int articleId, ValidateArticleLikeListener validateArticleLikeListener) {
        this.context = context;
        this.validateArticleLikeListener = validateArticleLikeListener;
        sendRequestWithVolley(userId, articleId);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(int userId, int articleId){
        String url = NetConfig.SERVICE
                + NetConfig.VALIDATE_ARTICLE_LIKE
                +"userId=" + userId
                +"&articleId=" + articleId;
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
            returnData = jsonObject.getBoolean("validateArticleLike");
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(validateArticleLikeListener != null){
            validateArticleLikeListener.complete(returnData);
        }
    }
}
