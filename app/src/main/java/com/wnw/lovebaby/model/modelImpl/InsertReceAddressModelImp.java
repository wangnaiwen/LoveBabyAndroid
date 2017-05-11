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
import com.wnw.lovebaby.model.modelInterface.IInsertReceAddressModel;
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by wnw on 2017/3/10.
 */

public class InsertReceAddressModelImp implements IInsertReceAddressModel{

    private Context context;
    private ReceAddress address;
    private ReceAddressInsertListener receAddressInsertListener;

    @Override
    public void insertReceAddress(Context context, ReceAddress address, ReceAddressInsertListener receAddressInsertListener) {
        this.context = context;
        this.receAddressInsertListener = receAddressInsertListener;
        sendRequestWithVolley(address);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(ReceAddress address){
        String url = NetConfig.SERVICE + NetConfig.INSERT_RECE_ADDRESS;
        String receiver = "";
        String province = "";
        String city = "";
        String county = "";
        String detailAddress = "";
        try{
            receiver = URLEncoder.encode(address.getReceiver(), "UTF-8");
            province = URLEncoder.encode(address.getProvince(), "UTF-8");
            city = URLEncoder.encode(address.getCity(), "UTF-8");
            county = URLEncoder.encode(address.getCounty(), "UTF-8");
            detailAddress = URLEncoder.encode(address.getDetailAddress(), "UTF-8");

        }catch (Exception e){
            e.printStackTrace();
        }

        url = url + "userId="+ address.getUserId()
                +"&receiver="+ receiver
                +"&province="+ province
                +"&city=" + city
                +"&county="+ county
                +"&detailAddress="+ detailAddress
                +"&phone=" + address.getPhone()
                +"&postcode="+ address.getPostcode();
        LogUtil.d("wnwInsert",url);
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
            JSONObject object= jsonObject.getJSONObject("insertReceAdd");
            if(object != null){
                address = new ReceAddress();
                address.setId(object.getInt("id"));
                address.setId(object.getInt("id"));
                address.setUserId(object.getInt("userId"));
                address.setReceiver(object.getString("receiver"));
                address.setProvince(object.getString("province"));
                address.setCity(object.getString("city"));
                address.setCounty(object.getString("county"));
                address.setDetailAddress(object.getString("detailAddress"));
                address.setPhone(object.getString("phone"));
                address.setPostcode(object.getInt("postcode"));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();

    }

    private void retData(){
        if(receAddressInsertListener != null){
            receAddressInsertListener.complete(address);
        }
    }
}
