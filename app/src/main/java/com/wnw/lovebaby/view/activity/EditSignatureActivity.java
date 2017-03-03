package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;

/**
 * Created by wnw on 2017/2/20.
 */

public class EditSignatureActivity  extends Activity implements View.OnClickListener {

    private ImageView backSignature;
    private TextView saveSignature;
    private EditText editSignatureView;

    private String signature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_edit);
        Intent intent = getIntent();
        signature = intent.getStringExtra("signature");
        initView();
    }

    private void initView(){
        backSignature = (ImageView)findViewById(R.id.signature_back);
        saveSignature = (TextView)findViewById(R.id.signature_save);
        editSignatureView = (EditText)findViewById(R.id.signature_edit);

        backSignature.setOnClickListener(this);
        saveSignature.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signature_back:
                onBackPressed();
                break;
            case R.id.signature_save:
                if(editSignatureView.getText().toString().trim().isEmpty()){
                    Toast.makeText(this, "个性签名为空", Toast.LENGTH_SHORT).show();
                }else if (editSignatureView.getText().toString().trim().equals(signature)){
                    Toast.makeText(this, "个性签名没有改变", Toast.LENGTH_SHORT).show();
                }else {
                    saveSignature();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void saveSignature(){
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}

