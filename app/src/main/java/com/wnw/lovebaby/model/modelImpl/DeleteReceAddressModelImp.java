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
import com.wnw.lovebaby.model.modelInterface.IDeleteReceAddressModel;
import com.wnw.lovebaby.model.modelInterface.IInsertReceAddressModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wnw on 2017/3/10.
 */

public class DeleteReceAddressModelImp implements IDeleteReceAddressModel{

    private Context context;
    private boolean returnData = false;
    private ReceAddressDeleteListener receAddressDeleteListener;

    @Override
    public void deleteReceAddress(Context context, int id, ReceAddressDeleteListener receAddressDeleteListener) {
        this.context = context;
        this.receAddressDeleteListener = receAddressDeleteListener;
        sendRequestWithVolley(id);
    }

    /**
     * use volley to get the data
     * */
    private void sendRequestWithVolley(int id){
        String url = NetConfig.SERVICE + NetConfig.DELETE_RECE_ADDRESS + "id="+id;
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
            boolean isSuccess = jsonObject.getBoolean("deleteReceAdd");
            if(isSuccess){
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                returnData = true;
            }else {
                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        retData();

    }

    private void retData(){
        if(receAddressDeleteListener != null){
            receAddressDeleteListener.complete(returnData);
        }
    }
}
