package com.wnw.lovebaby.model.modelImpl;

import android.content.Context;
import android.support.v4.util.LogWriter;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wnw.lovebaby.config.NetConfig;
import com.wnw.lovebaby.domain.Order;
import com.wnw.lovebaby.model.modelInterface.IFindOrderByInviteeModel;
import com.wnw.lovebaby.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2017/4/23.
 */

public class FindOrderByInviteeImpl implements IFindOrderByInviteeModel {

    private Context context;
    private OrderFindByInviteeListener orderFindByInviteeListener;
    private List<Order> returnData;
    private List<String> nameList;

    @Override
    public void findOrderByInvitee(Context context, int invitee, int number, OrderFindByInviteeListener orderFindByInviteeListener) {
        this.context = context;
        this.orderFindByInviteeListener = orderFindByInviteeListener;
        sendRequestWithVolley(invitee, number);
    }

    /**
     * use volley to get the data
     * */

    private void sendRequestWithVolley(int invitee, int number){
        String url = NetConfig.SERVICE+NetConfig.FIND_ORDER_BY_INVITEE
                +"invitee="+invitee
                +"&number=" +number;
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
            JSONArray jsonArray = jsonObject.getJSONArray("findOrderByInvitee");
            if(jsonArray != null){
                returnData = new ArrayList<>();
                int length = jsonArray.length();
                for(int i = 0 ; i < length; i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Order order = new Order();
                    order.setId(object.getInt("id"));
                    order.setShopId(object.getInt("shopId"));
                    order.setAddressId(object.getInt("addressId"));
                    order.setUserId(object.getInt("userId"));
                    order.setCreateTime(object.getString("createTime"));
                    order.setPayTime(object.getString("payTime"));
                    order.setFinishTime(object.getString("finishTime"));
                    order.setOrderNumber(object.getString("orderNumber"));
                    order.setOrderType(object.getInt("orderType"));
                    returnData.add(order);
                }
                JSONArray nameArray = jsonObject.getJSONArray("nameList");
                if (nameArray != null){
                    nameList = new ArrayList<>();
                    int length1 = nameArray.length();
                    for (int i= 0; i < length1; i++){
                        nameList.add(nameArray.getString(i));
                    }
                }

            }else {
                //Toast.makeText(context, "找不到收货地址", Toast.LENGTH_SHORT).show();
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
        if(orderFindByInviteeListener != null){
            orderFindByInviteeListener.complete(returnData, nameList);
        }
    }
}
