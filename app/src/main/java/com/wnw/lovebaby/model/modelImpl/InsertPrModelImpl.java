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
import com.wnw.lovebaby.model.modelInterface.IInsertPrModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by wnw on 2017/4/4.
 */

public class InsertPrModelImpl implements IInsertPrModel {
    private Context context;
    private PrInsertListener prInsertListener;
    private boolean returnData;

    @Override
    public void insertPr(Context context, Pr pr, PrInsertListener prInsertListener) {
        this.context = context;
        this.prInsertListener = prInsertListener;
        sendRequestWithVolley(pr);
    }
    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(Pr pr){
        String url = NetConfig.SERVICE + NetConfig.INSERT_PR;
        String userNickName = "";
        String evaluation = "";
        try{
            userNickName = URLEncoder.encode(pr.getUserNickName(), "UTF-8");
            evaluation = URLEncoder.encode(pr.getEvaluation(), "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }

        url = url
                + "userId="+pr.getUserId()
                +"&dealId="+ pr.getDealId()
                +"&productId="+ pr.getProductId()
                +"&userNickName="+ userNickName
                +"&productScore=" + pr.getProductScore()
                +"&serviceScore="+ pr.getServiceScore()
                +"&logisticsScore="+ pr.getLogisticsScore()
                +"&evaluation="+ evaluation
                +"&time="+ pr.getTime();
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
            returnData = jsonObject.getBoolean("insertPr");
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(prInsertListener != null){
            prInsertListener.complete(returnData);
        }
    }
}
