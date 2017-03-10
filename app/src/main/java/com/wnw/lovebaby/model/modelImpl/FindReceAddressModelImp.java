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
import com.wnw.lovebaby.model.modelInterface.IFindReceAddressModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/3/10.
 */

public class FindReceAddressModelImp implements IFindReceAddressModel {

    private Context context;
    private List<ReceAddress> returnData = null;
    private ReceAddressFindListener receAddressFindListener;
    private String url = "http://119.29.182.235:8080/babyTest/findReceAddressByUserId?";

    @Override
    public void findReceAddress(Context context, int userId, ReceAddressFindListener receAddressFindListener) {
        this.context = context;
        this.receAddressFindListener = receAddressFindListener;
        sendRequestWithVolley(userId);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(int userId){
        url = url + "userId="+ userId;
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
            JSONArray jsonArray = jsonObject.getJSONArray("findReceAdd");
            if(jsonArray != null){
                returnData = new ArrayList<>();
                int length = jsonArray.length();
                for(int i = 0 ; i < length; i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    ReceAddress address = new ReceAddress();
                    address.setId(object.getInt("id"));
                    address.setUserId(object.getInt("userId"));
                    address.setReceiver(object.getString("receiver"));
                    address.setProvince(object.getString("province"));
                    address.setProvince(object.getString("city"));
                    address.setCounty(object.getString("county"));
                    address.setDetailAddress(object.getString("detailAddress"));
                    address.setPhone(object.getString("phone"));
                    address.setPostcode(object.getInt("postcode"));
                    returnData.add(address);
                }
            }else {
                Toast.makeText(context, "找不到收货地址", Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();
    }

    private void retData(){
        if(receAddressFindListener != null){
            receAddressFindListener.complete(returnData);
        }
    }
}
