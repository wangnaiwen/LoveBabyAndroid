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
import com.wnw.lovebaby.model.modelInterface.IUpdateArticleReadTimes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/5/8.
 */

public class UpdateArticleReadTimes implements IUpdateArticleReadTimes {

    private Context context;
    private ArticleUpdateReadTimesListener articleUpdateReadTimesListener;
    private boolean isSuccess;

    @Override
    public void updateArticle(Context context, int id, ArticleUpdateReadTimesListener articleUpdateReadTimesListener) {
        this.context = context;
        this.articleUpdateReadTimesListener = articleUpdateReadTimesListener;
        sendRequestWithVolley(id);
    }

    /**
     * use volley to get the data
     *
     * */

    private void sendRequestWithVolley(int id){

        String url = NetConfig.SERVICE + NetConfig.UPDATE_ARTICLE_READ_TIMES;
        url = url +"id="+id;
        Log.d("url",url );
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
            isSuccess = jsonObject.getBoolean("updateArticleReadTimes");
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
        if(articleUpdateReadTimesListener != null){
            articleUpdateReadTimesListener.complete(isSuccess);
        }
    }

}
