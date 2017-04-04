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
import com.wnw.lovebaby.domain.Pr;
import com.wnw.lovebaby.domain.Product;
import com.wnw.lovebaby.model.modelInterface.IFindPrsByProductIdModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/4.
 */

public class FindPrsByProductIdModelImpl implements IFindPrsByProductIdModel {

    private Context context;
    private PrFindByProductIdListener prFindByProductIdListener;
    private List<Pr> prList;

    @Override
    public void FindPrByProductId(Context context, int productId, int number, PrFindByProductIdListener prFindByProductIdListener) {
        this.context = context;
        this.prFindByProductIdListener = prFindByProductIdListener;
        sendRequestWithVolley(productId, number);
    }

    private void sendRequestWithVolley(int productId, int number){
        String url = "http://119.29.182.235:8080/babyTest/findPrsByProductId";
        url = url + "productId=" + productId + "&number="+number;
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
            JSONArray jsonArray = jsonObject.getJSONArray("findPrByProductId");
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
                    pr.setEvaluation(object.getString("evaluation"));
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
        if(prFindByProductIdListener != null){
            prFindByProductIdListener.complete(prList);
        }
    }
}
