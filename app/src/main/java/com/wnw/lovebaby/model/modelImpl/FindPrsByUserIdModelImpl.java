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
import com.wnw.lovebaby.domain.Pr;
import com.wnw.lovebaby.model.modelInterface.IFindPrsByUserIdModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/4.
 */

public class FindPrsByUserIdModelImpl implements IFindPrsByUserIdModel{
    private Context context;
    private PrFindByUserIdListener prFindByUserIdListener;
    private List<Pr> prList;

    @Override
    public void FindPrByUserId(Context context, int userId,int number, PrFindByUserIdListener prFindByUserIdListener) {
        this.context = context;
        this.prFindByUserIdListener = prFindByUserIdListener;
        sendRequestWithVolley(userId,number);
    }


    private void sendRequestWithVolley(int userId,int number){
        String url = NetConfig.SERVICE + NetConfig.FIND_PR_BY_USER_ID;
        url = url + "userId=" + userId + "&number="+number;
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
            JSONArray jsonArray = jsonObject.getJSONArray("findPrByUserId");
            if(jsonArray != null){
                prList = new ArrayList<>();
                int length = jsonArray.length();
                for(int i = 0 ; i < length; i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Pr pr = new Pr();
                    pr.setId(object.getInt("id"));
                    pr.setUserId(object.getInt("userId"));
                    pr.setDealId(object.getInt("dealId"));
                    pr.setProductId(object.getInt("productId"));
                    pr.setUserNickName(object.getString("userNickName"));
                    pr.setProductScore(object.getInt("productScore"));
                    pr.setLogisticsScore(object.getInt("logisticsScore"));
                    pr.setServiceScore(object.getInt("serviceScore"));
                    pr.setEvaluation(object.getString("evalution"));
                    pr.setTime(object.getString("time"));

                    prList.add(pr);
                }
            }else {
                Toast.makeText(context, "找不到", Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(prFindByUserIdListener != null){
            prFindByUserIdListener.complete(prList);
        }
    }
}
