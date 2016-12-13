package com.wnw.lovebaby.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.bean.ReceAddress;

import java.util.List;

/**
 * Created by wnw on 2016/12/11.
 */

public class ReceAddressAdapter extends BaseAdapter {

    public final static int SELECT_RECE_ADDRESS = 0;
    public final static int EDIT_RECE_ADDRESS = 1;
    public final static int DELETE_RECE_ADDRESS = 2;

    private Context context;
    private Handler handler;
    private List<ReceAddress> receAddressList;

    public ReceAddressAdapter(Context context,Handler handler,  List<ReceAddress> receAddresses){
        this.context = context;
        this.handler = handler;
        this.receAddressList = receAddresses;
    }

    private void setReceAddressList(List<ReceAddress> receAddresses){
        this.receAddressList = receAddresses;
    }

    @Override
    public int getCount() {
        return receAddressList.size();
    }

    @Override
    public Object getItem(int i) {
        return receAddressList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ReceAddressHolder receAddressHolder = null;
        if(view == null){
            receAddressHolder = new ReceAddressHolder();
            view = LayoutInflater.from(context).inflate(R.layout.lv_rece_address_item, null);
            receAddressHolder.receAddress = (LinearLayout)view.findViewById(R.id.rece_address);
            receAddressHolder.editReceAddress = (LinearLayout)view.findViewById(R.id.rece_address_edit);
            receAddressHolder.deleteReceAddress = (LinearLayout)view.findViewById(R.id.rece_address_delete);
            receAddressHolder.receiver = (TextView)view.findViewById(R.id.lv_item_receiver);
            receAddressHolder.phone = (TextView)view.findViewById(R.id.lv_item_phone);
            receAddressHolder.address = (TextView)view.findViewById(R.id.lv_item_address);
            view.setTag(receAddressHolder);
        }else {
            receAddressHolder = (ReceAddressHolder) view.getTag();
        }
        ReceAddress receAddress = receAddressList.get(i);
        receAddressHolder.receiver.setText(receAddress.getReceiver());
        receAddressHolder.phone.setText(receAddress.getPhone());
        receAddressHolder.address.setText(receAddress.getAddress());

        final int index = i;
        receAddressHolder.receAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = SELECT_RECE_ADDRESS;
                        message.arg1 = index;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });

        receAddressHolder.editReceAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = EDIT_RECE_ADDRESS;
                        message.arg1 = index;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });

        receAddressHolder.deleteReceAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = DELETE_RECE_ADDRESS;
                        message.arg1 = index;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
        return view;
    }

    private class ReceAddressHolder{
        LinearLayout receAddress;
        LinearLayout editReceAddress;
        LinearLayout deleteReceAddress;

        TextView receiver;
        TextView phone;
        TextView address;
    }
}
