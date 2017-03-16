package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.util.EncodingUtils;
import com.google.gson.Gson;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.domain.ReceAddress;
import com.wnw.lovebaby.bean.address.City;
import com.wnw.lovebaby.bean.address.County;
import com.wnw.lovebaby.bean.address.Province;
import com.wnw.lovebaby.presenter.InsertReceAddressPresenter;
import com.wnw.lovebaby.util.LogUtil;
import com.wnw.lovebaby.util.Util;
import com.wnw.lovebaby.view.dialog.CityPickerDialog;
import com.wnw.lovebaby.view.viewInterface.IInsertReceAddressView;
import com.wnw.lovebaby.view.viewInterface.MvpBaseActivity;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by wnw on 2016/12/13.
 */

public class AddReceAddressActivity extends MvpBaseActivity<IInsertReceAddressView, InsertReceAddressPresenter>
        implements IInsertReceAddressView, View.OnClickListener {

    private ImageView backAddReceAddress;
    private EditText addReceiver;
    private EditText addRecePhone;
    private LinearLayout pickReceAddressCity;
    private EditText addReceAddressHouseNum;
    private EditText addPostcode;
    private Button finishAddReceAddress;
    private TextView displayAddress;

    private ReceAddress receAddress = new ReceAddress();

    private ArrayList<Province> provinces = new ArrayList<Province>();

    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        intiView();
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        LogUtil.d("wnwI", userId+"");
    }

    private void intiView(){
        backAddReceAddress = (ImageView)findViewById(R.id.back_add_address);
        addReceiver= (EditText)findViewById(R.id.et_add_address_receiver);
        addRecePhone = (EditText)findViewById(R.id.et_add_address_phone);
        addReceAddressHouseNum = (EditText)findViewById(R.id.et_add_address_house_num);
        pickReceAddressCity = (LinearLayout)findViewById(R.id.pick_address_dialog);
        addPostcode = (EditText) findViewById(R.id.et_add_address_postcode);
        finishAddReceAddress = (Button)findViewById(R.id.finish_add_address);
        displayAddress = (TextView)findViewById(R.id.display_pick_address);

        backAddReceAddress.setOnClickListener(this);
        pickReceAddressCity.setOnClickListener(this);
        finishAddReceAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_add_address:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.pick_address_dialog:
                if (provinces.size() > 0) {
                    showAddressDialog();
                } else {
                    new InitAreaTask(this).execute(0);
                }
                break;
            case R.id.finish_add_address:
                finishAddress();
                break;
            default:

                break;

        }
    }

    private void showAddressDialog() {
        new CityPickerDialog(this, provinces, null, null, null, new CityPickerDialog.onCityPickedListener() {

            @Override
            public void onPicked(Province selectProvince, City selectCity, County selectCounty) {
                receAddress.setProvince(selectProvince != null ? selectProvince.getAreaName() : "");
                receAddress.setCity(selectCity != null ? selectCity.getAreaName() : "");
                if(selectCounty != null){
                    receAddress.setCounty(selectCounty.getAreaName());
                    if(selectCounty.getAreaName() == null){
                        receAddress.setCounty("");
                    }
                }else {
                    receAddress.setCounty("");
                }
                StringBuilder address = new StringBuilder();
                address.append(receAddress.getProvince())
                        .append(" ")
                        .append(receAddress.getCity())
                        .append(" ")
                        .append(receAddress.getCounty());
                displayAddress.setText(address.toString());
            }
        }).show();
    }

    private class InitAreaTask extends AsyncTask<Integer, Integer, Boolean> {

        Context mContext;

        Dialog progressDialog;

        public InitAreaTask(Context context) {
            mContext = context;
            progressDialog = Util.createLoadingDialog(mContext, "请稍等...", true,
                    0);
        }

        @Override
        protected void onPreExecute() {

            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressDialog.dismiss();
            if (provinces.size()>0) {
                showAddressDialog();
            } else {
                Toast.makeText(mContext, "数据初始化失败", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            String address = null;
            InputStream in = null;
            try {
                in = mContext.getResources().getAssets().open("address.txt");
                byte[] arrayOfByte = new byte[in.available()];
                in.read(arrayOfByte);
                address = EncodingUtils.getString(arrayOfByte, "UTF-8");
                JSONArray jsonList = new JSONArray(address);
                Gson gson = new Gson();
                for (int i = 0; i < jsonList.length(); i++) {
                    try {
                        provinces.add(gson.fromJson(jsonList.getString(i),
                                Province.class));
                    } catch (Exception e) {
                    }
                }
                return true;
            } catch (Exception e) {
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {

                    }
                }
            }
            return false;
        }
    }

    private void finishAddress(){
        if(addReceiver.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "请输入收件人",Toast.LENGTH_SHORT).show();
        }else if(addRecePhone.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "请输入收件人联系方式",Toast.LENGTH_SHORT).show();
        }else if(displayAddress.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "请选择地址",Toast.LENGTH_SHORT).show();
        }else if (addReceAddressHouseNum.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "请输入详细地址",Toast.LENGTH_SHORT).show();
        }else {
            receAddress.setUserId(userId);
            receAddress.setReceiver(addReceiver.getText().toString().trim());
            receAddress.setPhone(addRecePhone.getText().toString().trim());
            receAddress.setDetailAddress(addReceAddressHouseNum.getText().toString().trim());
            if(addPostcode.getText().toString().trim().isEmpty()){

            }else {
                receAddress.setPostcode(Integer.parseInt(addPostcode.getText().toString().trim()));
            }
            addReceAddressToDB();
        }
    }

    private void addReceAddressToDB(){
        mPresenter.insertReceAddresss(this, receAddress);
    }

    @Override
    public void showDialog() {
        Toast.makeText(this, "正在保存", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void insertReceAddress(ReceAddress address) {
        if (address == null){
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }else {
            LogUtil.d("wnwI", address.getId()+address.getProvince()+address.getCity());
            Intent intent = new Intent();
            intent.putExtra("address", address);
            setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        }
    }

    @Override
    protected InsertReceAddressPresenter createPresenter() {
        return new InsertReceAddressPresenter();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
