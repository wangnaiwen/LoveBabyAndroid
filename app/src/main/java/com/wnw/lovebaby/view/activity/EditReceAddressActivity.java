package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.domain.ReceAddress;
import com.wnw.lovebaby.bean.address.City;
import com.wnw.lovebaby.bean.address.County;
import com.wnw.lovebaby.bean.address.Province;
import com.wnw.lovebaby.presenter.UpdateReceAddressPresenter;
import com.wnw.lovebaby.util.LogUtil;
import com.wnw.lovebaby.util.Util;
import com.wnw.lovebaby.view.dialog.CityPickerDialog;
import com.wnw.lovebaby.view.viewInterface.IUpdateReceAddressView;
import com.wnw.lovebaby.view.viewInterface.MvpBaseActivity;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by wnw on 2016/12/13.
 */

public class EditReceAddressActivity extends MvpBaseActivity<IUpdateReceAddressView, UpdateReceAddressPresenter>
        implements View.OnClickListener, IUpdateReceAddressView{
    private ImageView backEditReceAddress;
    private EditText editReceiver;
    private EditText editRecePhone;
    private LinearLayout rePickReceAddressCity;
    private EditText editReceAddressHouseNum;
    private EditText editPostcode;
    private TextView displayAddress;
    private Button finishEditReceAddress;


    private ReceAddress receAddress = new ReceAddress();

    private ArrayList<Province> provinces = new ArrayList<Province>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        intiView();
        setDefaultData();
    }

    private void intiView(){
        backEditReceAddress = (ImageView)findViewById(R.id.back_edit_address);
        editReceiver= (EditText)findViewById(R.id.et_edit_address_receiver);
        editRecePhone = (EditText)findViewById(R.id.et_edit_address_phone);
        editReceAddressHouseNum = (EditText)findViewById(R.id.et_edit_address_house_num);
        editPostcode = (EditText)findViewById(R.id.et_edit_address_postcode);
        rePickReceAddressCity = (LinearLayout)findViewById(R.id.repick_address_dialog);
        finishEditReceAddress = (Button)findViewById(R.id.finish_edit_address);
        displayAddress = (TextView)findViewById(R.id.display_address);

        backEditReceAddress.setOnClickListener(this);
        rePickReceAddressCity.setOnClickListener(this);
        finishEditReceAddress.setOnClickListener(this);
    }

    private void setDefaultData(){
        Intent intent = getIntent();
        receAddress = (ReceAddress)intent.getSerializableExtra("receAddress_data");
        editReceiver.setText(receAddress.getReceiver());
        editRecePhone.setText(receAddress.getPhone());
        displayAddress.setText(receAddress.getProvince() + " " + receAddress.getCity() + " " + receAddress.getCounty());
        editReceAddressHouseNum.setText(receAddress.getDetailAddress());
        editPostcode.setText(receAddress.getPostcode()+ "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_edit_address:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.repick_address_dialog:
                if (provinces.size() > 0) {
                    showAddressDialog();
                } else {
                    new InitAreaTask(this).execute(0);
                }
                break;
            case R.id.finish_edit_address:
                finishAddress();
                break;
            default:

                break;

        }
    }

    @Override
    public void showDialog() {
        Toast.makeText(this, "正在保存修改", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateReceAddress(boolean isSuccess) {
        if(isSuccess){
            Toast.makeText(this, "成功保存", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("address", receAddress);
            setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        }else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected UpdateReceAddressPresenter createPresenter() {
        return new UpdateReceAddressPresenter();
    }

    private void showAddressDialog() {
        new CityPickerDialog(this, provinces, null, null, null, new CityPickerDialog.onCityPickedListener() {

            @Override
            public void onPicked(Province selectProvince, City selectCity, County selectCounty) {
                receAddress.setProvince(selectProvince != null ? selectProvince.getAreaName() : "");
                receAddress.setCity(selectCity != null ? selectCity.getAreaName() : "");
                if(selectCounty != null){
                    LogUtil.i("wnw", "!=null");
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
        if(editReceiver.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "请输入收件人",Toast.LENGTH_SHORT).show();
        }else if(editRecePhone.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "请输入收件人联系方式",Toast.LENGTH_SHORT).show();
        }else if (editReceAddressHouseNum.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "请选择地址",Toast.LENGTH_SHORT).show();
        }else if(displayAddress.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "请输入详细地址",Toast.LENGTH_SHORT).show();
        }else {
            receAddress.setReceiver(editReceiver.getText().toString().trim());
            receAddress.setPhone(editRecePhone.getText().toString().trim());
            receAddress.setDetailAddress(editReceAddressHouseNum.getText().toString().trim());
            if(editPostcode.getText().toString().trim().isEmpty()){

            }else {
                receAddress.setPostcode(Integer.parseInt(editPostcode.getText().toString().trim()));
            }
            updateReceAddress();
        }
    }

    private void updateReceAddress(){
        mPresenter.updateReceAddresss(this, receAddress);
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
