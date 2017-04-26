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
import com.wnw.lovebaby.domain.Search;
import com.wnw.lovebaby.model.modelInterface.IFindSearchByUserIdModel;
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/26.
 */

public class FindSearchByUserIdModel implements IFindSearchByUserIdModel{

    private Context context;
    private FindSearchByUserIdListener findSearchByUserIdListener;
    private List<Search> searchList;

    @Override
    public void findSearchByUserId(Context context, int userId, FindSearchByUserIdListener findSearchByUserIdListener) {
        this.context = context;
        this.findSearchByUserIdListener = findSearchByUserIdListener;
        sendRequestWithVolley(userId);
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(int userId){
        String url = NetConfig.SERVICE+NetConfig.FIND_SEARCH_BY_USER_ID
                +"userId="+userId;
        LogUtil.d("url", url);
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
            JSONArray jsonArray = jsonObject.getJSONArray("findSearchByUserId");
            if(jsonArray != null){
                searchList = new ArrayList<>();
                int length = jsonArray.length();
                for(int i = 0 ; i < length; i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Search search = new Search();

                    search.setId(object.getInt("id"));
                    search.setUserId(object.getInt("userId"));
                    search.setKey(object.getString("key"));
                    searchList.add(search);
                }
            }else {
                Toast.makeText(context, "找不到", Toast.LENGTH_SHORT).show();
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
        if(findSearchByUserIdListener != null){
            findSearchByUserIdListener.complete(searchList);
        }
    }
}
