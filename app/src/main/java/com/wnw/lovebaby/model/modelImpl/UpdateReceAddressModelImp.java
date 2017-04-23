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
import com.wnw.lovebaby.model.modelInterface.IUpdateReceAddressModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/3/10.
 */

public class UpdateReceAddressModelImp implements IUpdateReceAddressModel {
    private Context context;
    private boolean returnData = false;
    private ReceAddressUpdateListener receAddressUpdateListener;

    @Override
    public void updateReceAddress(Context context, ReceAddress receAddress, ReceAddressUpdateListener receAddressUpdateListener) {
        this.context = context;
        this.receAddressUpdateListener = receAddressUpdateListener;
        sendRequestWithVolley(receAddress);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(ReceAddress address){
        String url = NetConfig.SERVICE + NetConfig.UPDATE_RECE_ADDRESS;
        url = url +"id="+address.getId()
                + "&userId="+ address.getUserId()
                +"&receiver="+ address.getReceiver()
                +"&province="+ address.getProvince()
                +"&city=" + address.getCity()
                +"&county="+ address.getCounty()
                +"&detailAddress="
                + address.getDetailAddress()
                +"&phone=" + address.getPhone()
                +"&postcode="+ address.getPostcode();
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
            boolean isSuccess = jsonObject.getBoolean("updateReceAdd");
            if(isSuccess){
                Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show();
                returnData = true;
            }else {
                Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();

    }

    private void retData(){
        if(receAddressUpdateListener != null){
            receAddressUpdateListener.complete(returnData);
        }
    }
}
